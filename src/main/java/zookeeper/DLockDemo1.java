package zookeeper;

import org.apache.zookeeper.*;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 分布式lock实现
 */
public class DLockDemo1 implements Runnable, Watcher {

    private ZooKeeper zk;
    private String self_path;
    private String root = "/lock/";

    public DLockDemo1() {
        zk = ZkUtils.getZk();
        try {
            self_path = zk.create(root, "222".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("register " + self_path);
            TimeUnit.SECONDS.sleep(2);
            zk.getChildren("/lock", this);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean lockFlag = false;

    private void checkGetLock() {
        try {
            List<String> children = zk.getChildren("/lock", this);
            Collections.sort(children);
            System.out.println("now first path is " + children.get(0));
            if ((root + children.get(0)).equals(self_path)) {
                System.out.println(self_path + " get lock ");
                lockFlag = true;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(new DLockDemo1()).start();
        }
        LockSupport.park();
    }


    @Override
    public void run() {
        while (!lockFlag) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(self_path + " work over");
        try {
            zk.delete(self_path, -1);
            System.out.println(self_path + " release lock ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
            checkGetLock();
        }
    }
}
