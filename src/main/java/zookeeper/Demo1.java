package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 实现配置管理
 */
public class Demo1 {

    public static void main(String[] args) throws Exception {

        ZooKeeper zk = ZkUtils.getZk();
        System.out.println(Thread.currentThread().getName());
        //创建父节点
//        zk.create("/config", "config".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println("create config path successfully");
        //创建子节点，使用EPHEMERAL，主程序执行完成后该节点被删除，只在本次会话内有效，可以用作分布式锁。
//        zk.create("/config/children", "name=version1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println("create children successful. name=version1");
        new Thread(new WriteThread(zk)).start();
        new Thread(new WatchThread(zk), "watch1").start();
        new Thread(new WatchThread(zk), "watch2").start();
        new Thread(new WatchThread(zk), "watch3").start();


        System.out.println(Thread.currentThread().getName() + " ready to park!!!!!!!!!!!!!!!!!");
        LockSupport.park();
        //获取节点信息
//		byte[] data = zk.getData("/testRoot", false, null);
//		System.out.println(new String(data));
//		System.out.println(zk.getChildren("/testRoot", false));

        //修改节点的值，-1表示跳过版本检查，其他正数表示如果传入的版本号与当前版本号不一致，则修改不成功，删除是同样的道理。
//		zk.setData("/testRoot", "modify data root".getBytes(), -1);
//		byte[] data = zk.getData("/testRoot", false, null);
//		System.out.println(new String(data));

        //判断节点是否存在
//		System.out.println(zk.exists("/testRoot/children", false));
        //删除节点
//		zk.delete("/testRoot/children", -1);
//		System.out.println(zk.exists("/testRoot/children", false));

//        zk.close();
    }

    static class WriteThread implements Runnable {
        ZooKeeper zk;

        public WriteThread(ZooKeeper zk) {
            this.zk = zk;
        }

        public void run() {
            Thread.currentThread().setName("写线程启动");
            int i = 0;
            while (true) {
                i++;
                System.out.println("写线程休眠");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("写线程change value");
                try {
                    zk.setData("/config/children", ("name=version" + i).getBytes(), -1);
                    System.out.println("write thread has changed value.");
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class WatchThread implements Runnable {
        ZooKeeper zk;

        public WatchThread(ZooKeeper zk) {
            this.zk = zk;
        }

        public void run() {
            try {
                zk.getData("/config/children", new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        Event.EventType eventType = event.getType();
                        if (Event.EventType.NodeDataChanged == eventType) {
                            System.out.println(Thread.currentThread().getName() + " find zk value has changed.");
                            try {
                                zk.getData("/config/children", new Watcher() {
                                    @Override
                                    public void process(WatchedEvent event) {
                                        Event.EventType eventType = event.getType();
                                        if (Event.EventType.NodeDataChanged == eventType) {
                                            System.out.println(Thread.currentThread().getName() + " find zk value has changed.");
                                        }
                                    }
                                }, new Stat());
                            } catch (KeeperException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Stat());
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " has registered a watch");
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



