package base.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 线程池生命周期
 * @Author cgh
 * @Date 2020-04-26 上午 9:59
 */
public class ThreadLifeTest {
    public static void main(String[] args) {
        Object object = new Object();
        ReentrantLock lock = new ReentrantLock();
        Condition condition  = lock.newCondition();
        new Thread(() -> {
           synchronized (object){
               try {
                   System.out.println("thread1 waiting");
                   object.wait(5000);
                   System.out.println("thread1 after waiting");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        },"Thread1").start();

        new Thread(() -> {
           synchronized (object){
               try {
                   System.out.println("thread2 notify");
                   object.notify();
                   // notify之后当前线程并不会释放锁，只是被notify的线程从等待队列进入同步队列
                   Thread.sleep(10000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        },"Thread2").start();

        new Thread(()->{
            lock.lock();
            System.out.println("thread3 waiting");
            try {
                condition.await();
//                condition.await(5, TimeUnit.SECONDS);
                System.out.println("thread3 after waiting");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "Thread3").start();

        new Thread(()->{
            lock.lock();
            System.out.println("thread4");
            // 打开或关闭这段注释，观察Thread3的状态
            condition.signal();
            // signal之后当前线程并不会释放锁，只是被signal的线程从等待队列进入同步队列
            // sleep也不会释放锁
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "Thread4").start();


    }
}
