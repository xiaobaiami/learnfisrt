package thread.my;

/**
 * 模拟一套我自己的threadlocal来理解具体的关系
 */
public class MyThread {
    /**
     * 线程内部有一个ThreaLockMap的变量
     */
    private MyThreadLocal.MyThreadLocalMap threadLocalMap = null;

    public MyThreadLocal.MyThreadLocalMap getThreadLocalMap() {
        return threadLocalMap;
    }

    public void setThreadLocalMap(MyThreadLocal.MyThreadLocalMap threadLocalMap) {
        this.threadLocalMap = threadLocalMap;
    }
}
