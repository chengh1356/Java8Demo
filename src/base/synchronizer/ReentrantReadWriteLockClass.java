package base.synchronizer;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description 重入读写锁
 *              特点：
 *                  1.支持公平/非公平策略
 *                  2.支持锁重入
 *                  3.支持锁降级：写锁可以降级成读锁，读锁不能升级成写锁。
 *                  4.Condition条件支持
 * @Author cgh
 * @Date 2020-04-17 上午 10:58
 */
public class ReentrantReadWriteLockClass {
    static ReentrantReadWriteLock readAndWrieteLock = new ReentrantReadWriteLock(true);
    private final static Lock readlock = readAndWrieteLock.readLock();
    private final static Lock writelock = readAndWrieteLock.writeLock();
    private Map<String,String> map = new HashMap<>();
    private final static List<String> data = new ArrayList<>();
    private volatile boolean isUpdate = false;//保证状态可见性


    public static void main(String[] args) {

//        separateMethod();//读写分离
//        lockUpMethod();//锁升级
        lockDownMethod();//锁降级
//        readReadMethod();//读读共享
//        writeWriteMethod();//写写互斥
//        readWriteMethod();//读写互斥
//        writeReadMethod();//写写互斥
    }


    //-----------------------------------读写分离--------------------------------//
    private static void separateMethod() {
        new Thread(() -> write()).start();
        new Thread(() -> read()).start();
    }

    public static void write(){
            try {
                writelock.lock();
                for (int i = 0; i < 4; i++) {
                    data.add("数据"+i);
                    System.out.println(Thread.currentThread().getName()+ ":写"+data.get(i));
                }
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                writelock.unlock();
            }


    }

    public static void read(){
        try {
            readlock.lock();
            for (String str:data){
                System.out.println(Thread.currentThread().getName()+":读"+str);
            }
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readlock.unlock();
        }
    }

    //---------------------------------锁升级-------------------------------------------//
    public static void lockUpMethod(){
        ReentrantReadWriteLockClass test = new ReentrantReadWriteLockClass();
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"启动");
                test.writeAndRead();
            }).start();
        }
    }


    public void writeAndRead(){
        //读数据
        readlock.lock();
        String readResult = map.get("a");
        if(readResult == null){
            System.out.println("空数据，需要先写");
            readlock.unlock();
            writelock.lock();
            map.put("a","java的架构师技术栈");
            writelock.unlock();
            System.out.println("写完了数据，写锁释放了");
            readlock.lock();
        }
        System.out.println("读取的数据是："+readResult);
        readlock.unlock();
    }


    //-------------------------------------------锁降级-----------------------------------------------------
    public static void lockDownMethod(){
        final ReentrantReadWriteLockClass test = new ReentrantReadWriteLockClass();
        Thread t1 = new Thread(() -> test.readWrite("lockDown","锁降级"));
        t1.start();
    }

    public void readWrite(String key,String value) {
        readlock.lock(); // 为了保证isUpdate能够拿到最新的值
        if (!isUpdate) {
            // 在获取写锁前必须释放读锁
            readlock.unlock();
            writelock.lock();
            try {
                //再次检查其他线程是否已经抢到
                if(!isUpdate) {
                    map.put(key, value);
                    isUpdate = true;
                }
                // 在释放写锁之前通过获取读锁来降级
                readlock.lock();
            } catch (Exception e) {
                e.printStackTrace();
            }
            writelock.unlock();
        }
        try {
            String obj = map.get(key);
            System.out.println(Thread.currentThread().getName()+":"+obj);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            readlock.unlock();
        }


    }
    //源码
    class CachedData {
        Object data;
        volatile boolean cacheValid;
        final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        void processCachedData() {
            rwl.readLock().lock();
            if (!cacheValid) {

                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {

                    // Recheck state because another thread might have
                    // acquired write lock and changed state before we did.
                    if (!cacheValid) {
                        //获取数据
                        data = null;
                        cacheValid = true;
                    }
                    // Downgrade by acquiring read lock before releasing write lock
                    rwl.readLock().lock();
                } finally {
                    rwl.writeLock().unlock(); // Unlock write, still hold read
                }
            }
            try {
//                use(data);
            } finally {
                rwl.readLock().unlock();
            }
        }
    }

    //--------------------------------------------读读共享---------------------------------------------------
    public static void readReadMethod(){
        final ReentrantReadWriteLockClass test = new ReentrantReadWriteLockClass();
        Thread t1 = new Thread(() -> test.read1());
        t1.setName("t1");
        Thread t2 = new Thread(() -> test.read1());
        t2.setName("t2");

        t1.start();
        t2.start();
    }


    public void read1() {
        try {
            readlock.lock();
            System.out.println(Thread.currentThread().getName() + " read start");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " read end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readlock.unlock();
        }
    }

    //--------------------------------写写互斥-------------------------------------------------------
    public static void writeWriteMethod(){
        final ReentrantReadWriteLockClass test = new ReentrantReadWriteLockClass();
        Thread t1 = new Thread(() -> test.write1());
        t1.setName("t1");
        Thread t2 = new Thread(() -> test.write1());
        t2.setName("t2");

        t1.start();
        t2.start();
    }


    public void write1() {
        try {
            writelock.lock();
            System.out.println(Thread.currentThread().getName() + " write start");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " write end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writelock.unlock();
        }
    }

    //--------------------------------读写、写读互斥-------------------------------------------------------
    public static void readWriteMethod(){
        final ReentrantReadWriteLockClass test = new ReentrantReadWriteLockClass();
        Thread t1 = new Thread(() -> test.read1());
        t1.setName("t1");
        Thread t2 = new Thread(() -> test.write1());
        t2.setName("t2");

        t1.start();
        t2.start();
    }
    public static void writeReadMethod(){
        final ReentrantReadWriteLockClass test = new ReentrantReadWriteLockClass();
        Thread t1 = new Thread(() -> test.write1());
        t1.setName("t1");
        Thread t2 = new Thread(() -> test.read1());
        t2.setName("t2");

        t1.start();
        t2.start();
    }

}
