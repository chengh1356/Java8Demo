package base;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.*;
import java.util.concurrent.atomic.LongAdder;


/**
 * @Description LongAdder引入的初衷——解决高并发环境下AtomicLong的自旋瓶颈问题
 *              尽量减少热点冲突，不到最后万不得已，尽量将CAS操作延迟
 *              https://segmentfault.com/a/1190000015865714
 * @Author cgh
 * @Date 2020-04-15 上午 9:17
 */
public class LongAdderClass{

    /** Number of CPUS, to place bound on table size */
    //  cpu核数，用来决定槽数组的大小
    static final int NCPU = Runtime.getRuntime().availableProcessors();

    /**
     * Table of cells. When non-null, size is a power of 2.
     * 槽数组，大小为2的幂次
     */
//    transient volatile Cell[] cells;

    /**
     * Base value, used mainly when there is no contention, but also as
     * a fallback during table initialization races. Updated via CAS.
     * 基数，在两种情况下使用：
     * 1. 没有遇到并发竞争时，直接使用base累加数值
     * 2. 初始化cells数组时，必须要保证cells数组之被初始化一次
     */
    transient volatile long base;

    /**
     * Spinlock (locked via CAS) used when resizing and/or creating Cells.
     * 锁标识
     * cells初始化或扩容时，通过CAS操作将此标识设置为1-加锁状态，初始化或扩容完毕后，将此标识设置为0-无锁状态
     */
    transient volatile int cellsBusy;


    public static void main(String[] args) {
        testAtomicLongVSLongAdder(1, 10000000);
        testAtomicLongVSLongAdder(10, 10000000);
        testAtomicLongVSLongAdder(20, 10000000);
        testAtomicLongVSLongAdder(40, 10000000);
        testAtomicLongVSLongAdder(80, 10000000);
    }

    private static void addTest(){
        //CASE1：Cell[]数组已经初始化
            //1. 判断cell是否为null，没有就创建单元,赋值
            //2. 判断第一次CAS是否成功，不成功则重新尝试
            //3. cell不为null，尝试累加x
            //4. cell的数组大小超过cpu核数后，不再扩容
            //5. 尝试加锁扩容，扩容后大小 == 当前容量 * 2
        //CASE2：Cell[]数组未初始化
            //1.cells没有加锁且没有初始化，则尝试对它进行加锁，并初始化cells数组
        //CASE3：Cell[]数组正在初始化中
            //1.直接操作base基数，将值累加到base上
        //重新计算线程新的hash值
    }


    private static void sumTest(){
        /**
         * 返回累加的和，也就是“当前时刻”的计数值
         *
         * 此返回值可能不是绝对准确的，因为调用这个方法时嗨哟其他线程可能正在进行计数累加，
         * 方法的返回时刻和调用时刻不是同一个点，在有并发的情况下，这个值只是近似准确的计数值
         *
         * 高并发是，除非全局加锁，否则得不到程序运行中某个时刻绝对准确的值
         */
    }

    static void testAtomicLongVSLongAdder(final int threadCount, final int times){
        try {
            System.out.println("threadCount：" + threadCount + ", times：" + times);
            long start = System.currentTimeMillis();
            testLongAdder(threadCount, times);
            System.out.println("LongAdder elapse：" + (System.currentTimeMillis() - start) + "ms");

            long start2 = System.currentTimeMillis();
            testAtomicLong(threadCount, times);
            System.out.println("AtomicLong elapse：" + (System.currentTimeMillis() - start2) + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void testAtomicLong(final int threadCount, final int times) throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        List<Thread> list = new ArrayList<>();
        for (int i=0;i<threadCount;i++){
            list.add(new Thread(() -> {
                for (int j = 0; j<times; j++){
                    atomicLong.incrementAndGet();
                }
            }));
        }

        for (Thread thread : list){
            thread.start();
        }

        for (Thread thread : list){
            //当前线程结束后，执行下一个线程
            thread.join();
        }
    }

    static void testLongAdder(final int threadCount, final int times) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        List<Thread> list = new ArrayList<>();
        for (int i=0;i<threadCount;i++){
            list.add(new Thread(() -> {
                for (int j = 0; j<times; j++){
                    longAdder.add(1);
                }
            }));
        }

        for (Thread thread : list){
            thread.start();
        }

        for (Thread thread : list){
            //当前线程结束后，执行下一个线程
            thread.join();
        }
    }





}
