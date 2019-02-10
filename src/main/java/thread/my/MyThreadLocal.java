package thread.my;

import java.util.Calendar;

public class MyThreadLocal<T> {

    /**
     * ThreadLocal 有一个内部类
     */
    static class MyThreadLocalMap<T> {

        public void set(MyThreadLocal myThreadLocal, T value) {

        }
    }

    /**
     * 模拟set方法， 使用方法参数来模拟Thread.currnetThread()
     */
    public void set(T value, MyThread myThread) {
        MyThreadLocalMap map = myThread.getThreadLocalMap();
        if (map == null) {
            map.set(this, value);
        } else {
            MyThreadLocalMap myThreadLocalMap = new MyThreadLocalMap();
            myThreadLocalMap.set(this, value);
            myThread.setThreadLocalMap(myThreadLocalMap);
        }
    }

}
