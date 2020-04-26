package base;

/**
 * @Description
 * @Author cgh
 * @Date 2020-04-16 上午 8:50
 */
public class VolitileClass {

    /**
     * 优点：
     *      1.不会造成线程的阻塞
     * 缺点：
     *      1.只能实现变量的修改可见性，并不能保证原子性
     *      2.只能使用在变量级别
     */
    private volatile int a;
    public void update() {
        a = 1;
    }
    public static void main(String[] args) {
        VolitileClass test = new VolitileClass();
        test.update();
    }

    /**
     *  1. volatile 可见性实现
     *      内存屏障，又称内存栅栏，是一个 CPU 指令。
     *      在程序运行时，为了提高执行性能，编译器和处理器会对指令进行重排序，
     *      JMM 为了保证在不同的编译器和 CPU 上有相同的结果，
     *      通过插入特定类型的内存屏障来禁止特定类型的编译器重排序和处理器重排序，
     *      插入一条内存屏障会告诉编译器和 CPU：
     *      不管什么指令都不能和这条 Memory Barrier 指令重排序。
     */
            /**
             * 1.1 缓存一致性
             *      为了保证各个处理器的缓存是一致的，实现了缓存一致性协议（MESI），
             *      日常处理的大多数计算机设备都属于 " 嗅探（snooping）" 协议。
             *      每个处理器通过嗅探在总线上传播的数据来检查自己缓存的值是不是过期了，
             *      当处理器发现自己缓存行对应的内存地址被修改，就会将当前处理器的缓存行设置成无效状态，
             *      当处理器对这个数据进行修改操作的时候，会重新从系统内存中把数据读到处理器缓存里。
             */
    /**
     * 2. 有序性实现
     *      对一个 volatile 域的写，happens-before 于任意后续对这个 volatile 域的读。
     */

    /**
     * 3. 禁止重排序，内存屏障
     *      StoreStore 屏障	禁止上面的普通写和下面的 volatile 写重排序。
     *                      volite写
     *      StoreLoad 屏障	防止上面的 volatile 写与下面可能有的 volatile 读/写重排序。
     *                      volite读
     *      LoadLoad 屏障	禁止下面所有的普通读操作和上面的 volatile 读重排序。
     *      LoadStore 屏障	禁止下面所有的普通写操作和上面的 volatile 读重排序。
     */


    /**
     * 应用场景：
     *      1.状态标志：用于指示发生了一个重要的一次性事件
     *      2.一次性安全发布：在缺乏同步的情况下，可能会遇到某个对象引用的更新值（由另一个线程写入）和该对象状态的旧值同时存在。
     *      3.独立观察（independent observation）：定期 发布 观察结果供程序内部使用
     *      4. volatile bean 模式：JavaBean 的所有数据成员都是 volatile 类型的
     *      5.开销较低的读－写锁策略：如果读操作远远超过写操作，可以结合使用内部锁和 volatile 变量来减少公共代码路径的开销。
     *      6.双重检查（double-checked）: 单例模式
     */
}
