package zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ConfigWatcher implements Watcher {
    ZooKeeper zk;

    public ConfigWatcher(ZooKeeper zk) {
        this.zk = zk;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            String read = ZkUtils.read(zk, "/config", this);

            System.out.println(Thread.currentThread().getName() + "find value has changed, new values is " + read);
        }
    }

    public static void main(String[] args) {
        ZooKeeper zk = ZkUtils.getZk();
        ConfigWatcher configWatcher = new ConfigWatcher(zk);
        String read = ZkUtils.read(zk, "/config", configWatcher);
        System.out.println(Thread.currentThread().getName() + "find value has changed, new values is " + read);
        AtomicInteger integer = new AtomicInteger(0);
        new Thread(new ChangeConfigValue(integer)).start();
        new Thread(new ChangeConfigValue(integer)).start();
        new Thread(new ChangeConfigValue(integer)).start();
        new Thread(new ChangeConfigValue(integer)).start();
        new Thread(new ChangeConfigValue(integer)).start();
        LockSupport.park();
    }
}

class ChangeConfigValue implements Runnable {
    AtomicInteger i;

    public ChangeConfigValue(AtomicInteger i) {
        this.i = i;
    }

    @Override
    public void run() {
        ZooKeeper zk = ZkUtils.getZk();
        while (true) {
            try {
                int v = i.getAndAdd(1);
                zk.setData("/config", String.valueOf(v).getBytes(), -1);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}