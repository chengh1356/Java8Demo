package concurrent.Lock;

import concurrent.ConcurrentUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockTest1 {
    private static final int NUM_INCREMENT = 10000;

    private static ReentrantLock lock = new ReentrantLock();

    private static int count = 0;

    private static void increment(){
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        task();
    }
    private static void task(){
        //创建
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //执行
        IntStream.range(0,NUM_INCREMENT)
                .forEach(i -> executor.submit(ReentrantLockTest1::increment));
        //关闭
        ConcurrentUtils.stop(executor);
        System.out.println(count);
    }
}

