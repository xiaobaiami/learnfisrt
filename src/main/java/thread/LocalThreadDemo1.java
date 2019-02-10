package thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class LocalThreadDemo1 implements Runnable {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private static ThreadLocal<SimpleDateFormat> local_sdf = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };
    int i;

    public LocalThreadDemo1(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        try {
            Date d = local_sdf.get().parse("12:12:" + (i % 60));
            System.out.println(i+":"+d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new LocalThreadDemo1(i));
        }
        executorService.shutdown();
    }
}
