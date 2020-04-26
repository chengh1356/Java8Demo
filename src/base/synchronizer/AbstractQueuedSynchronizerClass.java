package base.synchronizer;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Description 所有同步器的抽象类
 * @Author cgh
 * @Date 2020-04-16 下午 2:25
 */
public class AbstractQueuedSynchronizerClass {

    public static void main(String[] args) {

    }

    //尝试以独占模式获取锁
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }
    //尝试释放独占模式的锁
    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }
    //尝试以共享模式获取锁
    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }
    //尝试释放共享模式的锁
    protected boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
    }
    //返回是否以独占模式获有锁
    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }


}
