package zookeeper;


import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 获取集群环境下的唯一id
 */
public class Demo2 {
    private ZkClient client = null;
    private final String server;//记录服务器的地址
    private final String root;//记录父节点的路径
    private final String nodeName;//节点的名称
    private volatile boolean running = false;
    private ExecutorService cleanExector = null;

    public Demo2(String server, String root, String nodeName) {
        this.server = server;
        this.root = root;
        this.nodeName = nodeName;
    }

    public enum RemoveMethod {
        NONE, IMMEDIATELY, DELAY

    }

    public void start() throws Exception {
        if (running)
            throw new Exception("server has stated...");
        running = true;
        init();
    }

    private void init() {
        client = new ZkClient(server, 5000, 5000, new BytesPushThroughSerializer());
        cleanExector = Executors.newFixedThreadPool(10);
        try {
            client.createPersistent(root, true);
        } catch (ZkNodeExistsException e) {
            //ignore;
        }
    }

    private void freeResource() {
        cleanExector.shutdown();
        try {
            cleanExector.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleanExector = null;
        }
        if (client != null) {
            client.close();
            client = null;

        }
    }

    private String ExtractId(String str) {
        int index = str.lastIndexOf(nodeName);
        if (index >= 0) {
            index += nodeName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

    private void checkRunning() throws Exception {
        if (!running)
            throw new Exception("请先调用start");

    }

    public String generateId(RemoveMethod removeMethod) throws Exception {
        checkRunning();
        final String fullNodePath = root.concat("/").concat(nodeName);
        //返回创建的节点的名称
        final String ourPath = client.createPersistentSequential(fullNodePath, null);
        if (removeMethod.equals(RemoveMethod.IMMEDIATELY)) {
            client.delete(ourPath);
        } else if (removeMethod.equals(RemoveMethod.DELAY)) {
            cleanExector.execute(new Runnable() {
                public void run() {
                    // TODO Auto-generated method stub
                    System.out.println(Thread.currentThread().getName()+" will delete" + ourPath);
                    client.delete(ourPath);
                }
            });

        }
        //node-0000000000, node-0000000001，ExtractId提取ID
        return ExtractId(ourPath);
    }

    public void stop() throws Exception {
        if (!running)
            throw new Exception("server has stopped...");
        running = false;
        freeResource();
    }

    public static void main(String[] args) throws Exception{
        Demo2 idMaker = new Demo2("localhost:2181",
                "/NameService/IdGen", "ID");
        idMaker.start();
//        boolean b = idMaker.client.deleteRecursive("/NameService/IdGen");
//        System.out.println(b);
        try {
            for (int i = 0; i < 10; i++) {
                String id = idMaker.generateId(RemoveMethod.IMMEDIATELY);
                System.out.println(id);
            }
        } finally {
            idMaker.stop();
        }
    }
}
