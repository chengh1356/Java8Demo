package concurrent.Lock;

import concurrent.ConcurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * 邮戳锁，乐观读
 * 悲观锁：每次拿数据的时候就去锁上。
 * 乐观锁：每次去拿数据的时候，都没锁上，而是判断标记位是否有被修改，如果有修改就再去读一次
 */
public class StampedLockTest2 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();
        executor.submit(() -> {
            //尝试乐观读
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                ConcurrentUtils.sleep(1);
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                ConcurrentUtils.sleep(2);
                stamp = lock.tryOptimisticRead();
                System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
            } finally {
                lock.unlock(stamp);
                System.out.println("unLock Valid: " + lock.validate(stamp));
            }
        });

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("Write Lock acquired");
                ConcurrentUtils.sleep(2);
            } finally {
                lock.unlockWrite(stamp);
                System.out.println("Write done");
            }
        });
        System.out.println("executor stop");
        ConcurrentUtils.stop(executor);
    }

}
