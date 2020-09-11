package base.queue;

import sun.rmi.runtime.Log;

<<<<<<< HEAD
import java.nio.Buffer;
import java.nio.ByteBuffer;
=======
>>>>>>> ae36b6a43d6976f7b185f7d7d876fe92f51c6cc4
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author cgh
 * @Date 2020-04-21 上午 10:48
 */
public class ConcurrentHashMapClass {
    static Map<Long, String> conMap = new ConcurrentHashMap<Long, String>();
    public static void main(String[] args) throws InterruptedException {
        for (long i = 0; i < 5; i++) {
            conMap.put(i, i + "");
        }

        Thread thread = new Thread(new Runnable() {
            public void run() {
                conMap.put(100l, "100");
                System.out.println("ADD:" + 100);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for (Iterator<Map.Entry<Long, String>> iterator = conMap.entrySet().iterator(); iterator.hasNext();) {
                    Map.Entry<Long, String> entry = iterator.next();//并发使用
                    System.out.println(entry.getKey() + " - " + entry.getValue());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        thread2.start();

        Thread.sleep(3000);
        System.out.println("--------");
        for (Map.Entry<Long, String> entry : conMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
<<<<<<< HEAD
=======

>>>>>>> ae36b6a43d6976f7b185f7d7d876fe92f51c6cc4
    }
}
