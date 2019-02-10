package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DistributedLock {//建立一个描述分布式锁的程序处理类
    public static final String CONNECTION_RUL = "localhost:2181";
    public static final int SESSION_TIMEOUT = 2000;//设置连接超时时间
    public static final String AUTH_INFO = "zkuser:mldnjava";//进行连接的授权信息
    public static final String GROUPNODE = "/mldn-lock";//根节点
    public static final String SUNBODE = GROUPNODE + "/lockthread-";//子节点

    private CountDownLatch latch = null;

    //本操作的主要目的是为了在取得zookeeper连接之后才能进行后续的处理
    private CountDownLatch connectLatch = new CountDownLatch(1);

    private ZooKeeper zkClient = null; //建立Zookeeper程序控制类

    private String selfPath; //保存每次创建的临时节点信息

    private String waitPath; //保存下一个要进行处理的节点

    private int threadId = 0;

    /**
     * 进行一些初始化操作使用
     *
     * @param threadId 随意给定一个编号信息 * @param latch 进行线程同步处理
     * @throws Exception
     */
    public DistributedLock(int threadId, CountDownLatch latch) throws Exception {
        this.threadId = threadId;//保存每一个线程对象自己的ID信息
        this.latch = latch;
        this.connectionZookeeper();//进行节点的连接
    }

    public void handle() throws Exception {//具体业务处理
        this.createSubNode();//创建临时节点操作
    }

    public void handleSuccess() throws Exception {//表示取得锁之后进行的处理
        if (this.zkClient.exists(this.selfPath, false) == null) {
            return;//如果当前节点不存在
        }
        this.handleCallback();//执行具体的业务操作
        //如果某一个节点操作完毕了，那么应该立即删除掉该节点，否则获得的最小节点永远都是该节点
        this.zkClient.delete(selfPath, -1);
        this.releaseZookeeper();//释放连接
        this.latch.countDown();//进行减减的操作
    }

    public void handleCallback() throws Exception {//取得分布式锁之后的目的是要进行具体的操作
        Thread.sleep(200);//实现一个延迟处理
        System.out
                .println("****** Thread-" + this.threadId + "获得操作权，进行具体的业务操作");
    }

    public boolean checkMinPath() throws Exception {//进行最小节点的判断
        List<String> childen = this.zkClient.getChildren(GROUPNODE, false);//取得所有的节点信息
        Collections.sort(childen); //进行所有节点的排序，这样最小的节点就拍到最上面
        int index = childen.indexOf(this.selfPath.substring(GROUPNODE.length() + 1));
        switch (index) {
            case 0: {
                return true; //已经确定好当前的节点为最小节点
            }
            case -1: {
                return false; //该节点可能已经消失了
            }
            default: {//表示该节点不属于最小节点，那么应该向后继续排查
                this.waitPath = GROUPNODE + "/" + childen.get(index - 1);//获得下一个节点
                try {
                    this.zkClient.getData(waitPath, true, new Stat());//取得下一个节点的数据
                    return false; //本节点不是当前的操作的最小节点
                } catch (Exception e) {//如果出现了异常，则表示该节点不存在
                    if (this.zkClient.exists(waitPath, false) == null) {
                        return this.checkMinPath();//继续向后检测
                    } else {
                        throw e;
                    }
                }
            }
        }
    }

    public void createSubNode() throws Exception {//每一个线程对象的启动都要求创建一个节点信息
        this.selfPath = this.zkClient.create(SUNBODE, ("Thread-" + this.threadId).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("【Thread-" + this.threadId + "、创建新的临时节点】"
                + this.selfPath);
        //当节点创建完成之后就需要进行最小节点的检测
        if (this.checkMinPath()) {//如果当前的节点为整个项目的最小节点
            this.handleSuccess();//进行锁后的具体操作
        }
    }

    public void connectionZookeeper() throws Exception {//连接zookeeper服务
        this.zkClient = new ZooKeeper(CONNECTION_RUL, SESSION_TIMEOUT,
                new Watcher() {
                    public void process(WatchedEvent event) {
                        if (event.getType() == Event.EventType.None) {//第一次连接zookeeper的时候会出现none
                            DistributedLock.this.connectLatch.countDown();//表示已经连接成功
                        } else { //要处理删除节点操作，并且要确定下一个节点是已经准备出来的节点信息
                            if (event.getType() == Event.EventType.NodeDeleted
                                    && event.getPath().equals(
                                    DistributedLock.this.waitPath)) {
                                try {
                                    if (DistributedLock.this.checkMinPath()) {//如果当前的节点为整个项目的最小节点
                                        DistributedLock.this.handleSuccess();//进行锁后的具体操作
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
        this.zkClient.addAuthInfo("digest", AUTH_INFO.getBytes());//进行授权认证
        if (this.zkClient.exists(GROUPNODE, false) == null) {
            this.zkClient.create(GROUPNODE, "LOCKDEMO".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        this.connectLatch.await();//等待连接后才执行后续的功能
    }

    public void releaseZookeeper() {//进行zookeeper的连接释放
        if (this.zkClient != null) {
            try {
                this.zkClient.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}