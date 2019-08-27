package concurrent.Lock;

import concurrent.ConcurrentUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Map<String,String> map = new HashMap<>();
        //读写锁互斥,只有读读可以
        ReadWriteLock lock = new ReentrantReadWriteLock();
        //写入锁
        executor.submit(() -> {
            lock.writeLock().lock();
            try {
                ConcurrentUtils.sleep(1);
                map.put("foo", "bar");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        });
        //读锁
        Runnable readtask  = () ->  {
            lock.readLock().lock();
            try {
                ConcurrentUtils.sleep(1);
                System.out.println(map.get("foo"));
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.readLock().unlock();
            }
        };
        executor.submit(readtask);
        executor.submit(readtask);

        ConcurrentUtils.stop(executor);

    }
}
