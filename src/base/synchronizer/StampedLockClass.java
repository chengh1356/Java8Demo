package base.synchronizer;

/**
 * @Description 邮戳锁：对ReentrantReadWriteLock的优化
 *              特点：
 *                  1.获取和释放锁都需要邮戳
 *                  2.不可重入
 *                  3.读写模式可以互换
 *                  4.不支持Condition
 * @Author cgh
 * @Date 2020-04-17 下午 4:33
 */
public class StampedLockClass {
    public static void main(String[] args) {

    }
}
