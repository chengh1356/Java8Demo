package concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicTest {
    private static final int NUM_INCRMENTS = 1000;

    private static AtomicInteger atomicInt = new AtomicInteger(0);

    public static void main(String[] args) {
        testIncrement();
        testAccumulate();
    }
    //更新
    public static void testUpdate(){
        atomicInt.set(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0,NUM_INCRMENTS)
                .forEach(i -> executor.submit(() -> atomicInt.updateAndGet(n -> n+2)));
    }
    //合并
    public static void testAccumulate(){
        atomicInt.set(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0,NUM_INCRMENTS)
                .forEach(i -> executor.submit(() -> atomicInt.accumulateAndGet(i,(n,m) -> n+m)));
        ConcurrentUtils.stop(executor);
        System.out.format("Accumulate:%d\n",atomicInt.get());
    }
    //自增
    public static void testIncrement(){
        atomicInt.set(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        IntStream.range(0,NUM_INCRMENTS)
                .forEach(i -> executor.submit(atomicInt::incrementAndGet));
        ConcurrentUtils.stop(executor);
        System.out.format("Increment: Expected=%d; Is=%d\n",NUM_INCRMENTS,atomicInt.get());
    }
}
