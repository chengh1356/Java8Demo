package base.synchronizer;

/**
 * Java Memory Model（Java内存模型）
 * @Description
 * @Author cgh
 * @Date 2020-04-15 下午 3:45
 */
public class JMMClass {
    /**
     * 三要素：
     *      1.原子性
     *      2.可见性
     *      3.有序性
     */
    /**
     * 指令的执行
     *      1.取指 IF
     *      2.译码和取寄存器操作数 ID
     *      3.执行或者有效地址计算 EX
     *      4.存储器访问 MEM
     *      5.写回 WB
     */
    /**
     * Happen-Before规则
     *      程序顺序原则：一个线程内保证语义的串行性
     *      volatile规则：volatile变量的写操作，先发生于读操作，这保证了volatile变量的可见性
     *      锁规则：解锁（unlock）必然发生在随后的加锁（lock）前
     *      传递性：A先于B，B先于C，那么A必然先于C
     *      线程的start()方法先于它的每一个动作
     *      线程的所有操作先于线程的终结（Thread.join()）
     *      线程的中断（interrupt）先于被中断线程的代码
     *      对象的构造函数执行、结束先于finalize()方法
     */

}
