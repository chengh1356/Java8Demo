package base;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;

/**
 * @Description
 * @Author cgh
 * @Date 2020-04-13 上午 8:52
 */
public class AtomicClass {
    public static void main(String[] args) {
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        AtomicReference reference = new AtomicReference();
        AtomicStampedReference reference1 = new AtomicStampedReference(100,0);
        AtomicIntegerArray integerArray = new AtomicIntegerArray(100);
        integerTest();

    }

    private static void integerTest() {
        AtomicInteger atomicInteger = new AtomicInteger();

        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.getAndIncrement());
        //预测值相符时，替换
        System.out.println( atomicInteger.compareAndSet(2,3));
        System.out.println(atomicInteger.decrementAndGet());
    }


    private static AtomicInteger atomicInt = new AtomicInteger(100);
    private static AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);

    /**
     * 通过getStamp标记，解决CAS中的ABA问题
     * @throws InterruptedException
     */
    private static void abaTest()throws InterruptedException {
        Thread intT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInt.compareAndSet(100, 101);
                atomicInt.compareAndSet(101, 100);
            }
        });

        Thread intT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                boolean c3 = atomicInt.compareAndSet(100, 101);
                System.out.println(c3); // true
            }
        });

        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();

        Thread refT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            }
        });

        Thread refT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedRef.getStamp();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                }
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(c3); // false
            }
        });

        refT1.start();
        refT2.start();
    }

}
