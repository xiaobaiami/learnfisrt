package zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZkUtils {

    static final int SESSION_OUTTIME = 2000;//ms
    static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
    static final String CONNECT_ADDR = "localhost:2181";

    public static ZooKeeper getZk() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {
                public void process(WatchedEvent event) {
                    //获取事件的状态
                    Event.KeeperState keeperState = event.getState();
                    Event.EventType eventType = event.getType();
                    //如果是建立连接
                    if (Event.KeeperState.SyncConnected == keeperState) {
                        if (Event.EventType.None == eventType) {
                            connectedSemaphore.countDown();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zk;
    }

    public static void write(ZooKeeper zk, String path, String value) {
        try {
            Stat exists = zk.exists(path, false);
            if (exists == null) {
                zk.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            } else {
                zk.setData(path, value.getBytes(), -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read(ZooKeeper zk, String path, Watcher watcher) {
        try {
            byte[] data = zk.getData(path, watcher, null);
            return new String(data);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
