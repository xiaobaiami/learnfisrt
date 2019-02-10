package zookeeper;

import java.util.concurrent.CountDownLatch;

class LockThread implements Runnable {
    private DistributedLock lock;


    public LockThread(int threadId, CountDownLatch latch) throws Exception {
        this.lock = new DistributedLock(threadId, latch);
    }
    @Override
    public void run() {
        //每一个线程对象启动后都应该创建一个临时的节点信息
        try {
            this.lock.handle();//进行具体的操作处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class TestDistributedLock {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new LockThread(i, latch)).start();
        }
        //Thread.sleep(Long.MAX_VALUE);//为了保证可以观察到所有的临时节点信息，保证此处先不进行关闭
        latch.await();
        System.out.println("************* 所有的线程对象操作完毕  *************");
    }
}