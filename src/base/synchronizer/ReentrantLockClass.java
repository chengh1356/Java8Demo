package base.synchronizer;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 重入锁
 * @Author cgh
 * @Date 2020-04-17 上午 8:57
 */
public class ReentrantLockClass {
    private static final Lock fairlock = new ReentrantLock(true);
    private static final Lock unfairlock = new ReentrantLock(false);

    public static void main(String[] args) {

//        fairMethod();
//        unfairMethod();
//        ResponseInterruptMethod();
        tryLockMethod();
//        conditionMethod();
//        blockQueueMethod();
    }
    //--------------------------------------------公平锁测试-------------------------------------------
    private static void fairMethod() {
        new Thread(() -> fairlockTest(),"线程A").start();
        new Thread(() -> fairlockTest(),"线程B").start();
        new Thread(() -> fairlockTest(),"线程C").start();
        new Thread(() -> fairlockTest(),"线程D").start();
        new Thread(() -> fairlockTest(),"线程E").start();
    }

    public static void fairlockTest(){
        for (int i = 0; i < 2; i++) {
            try {
                fairlock.lock();
                System.out.println(Thread.currentThread().getName()+"获取了锁");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"释放了锁");
                fairlock.unlock();
            }
        }
    }



    //------------------------------------------------非公平锁测试-------------------------------------
    private static void unfairMethod() {
        new Thread(() -> unfairlockTest(),"线程A").start();
        new Thread(() -> unfairlockTest(),"线程B").start();
        new Thread(() -> unfairlockTest(),"线程C").start();
        new Thread(() -> unfairlockTest(),"线程D").start();
        new Thread(() -> unfairlockTest(),"线程E").start();
    }

    public static void unfairlockTest(){
        for (int i = 0; i < 2; i++) {
            try {
                unfairlock.lock();
                System.out.println(Thread.currentThread().getName()+"获取了锁");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"释放了锁");
                unfairlock.unlock();
            }
        }
    }


    //----------------------------------------------响应中断---------------------------------------
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();
    public static void ResponseInterruptMethod(){
        //造成死锁
        Thread thread = new Thread(new ThreadDemo(lock1,lock2));
        Thread thread1 = new Thread(new ThreadDemo(lock2,lock1));
        thread.start();
        thread1.start();
        //线程0中断
        thread.interrupt();
    }


    static class ThreadDemo implements Runnable{
        Lock firstLock;
        Lock secondLock;

        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            try {
                //上锁
                firstLock.lockInterruptibly();
                TimeUnit.SECONDS.sleep(4);
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName()
                                +"获取了资源，正常结束！");
            }
        }
    }

    //----------------------------------------尝试获取锁-----------------------------------------------//
    public static void tryLockMethod(){
        Thread thread = new Thread(new ThreadDemo(lock1,lock2));
        thread.start();
    }
    static class ThreadDemo1 implements Runnable{
        Lock firstLock;
        Lock secondLock;

        public ThreadDemo1(Lock firstLock,Lock secondLock){
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }
        @Override
        public void run() {
            try {
                if(!lock1.tryLock()){TimeUnit.MILLISECONDS.sleep(10);}
                if(!lock2.tryLock()){TimeUnit.MILLISECONDS.sleep(10);}
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName() + "正常结束");
            }
        }
    }



    //-------------------------------------实现等待通知机制---------------------------------------//
    static ReentrantLock conditionLock = new ReentrantLock();
    static Condition condition = conditionLock.newCondition();
    public static void conditionMethod(){
        conditionLock.lock();
        new Thread(new SignalThread()).start();
        System.out.println("主线程等待通知");
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            conditionLock.unlock();
        }
        System.out.println("主线程恢复运行");
    }

    static class SignalThread implements Runnable{
        @Override
        public void run() {
            conditionLock.lock();
            try {
                condition.signal();
                System.out.println("子线程通知");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                conditionLock.unlock();
            }
        }
    }


    //------------------------------------阻塞队列实现------------------------------------------

    public static void blockQueueMethod(){
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<Integer>(2);
        for (int i = 0; i < 10; i++) {
            int data = i;
            new Thread(() ->{
                    try {
                        queue.enqueue(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            ).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Integer data = queue.dequeue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    static class MyBlockingQueue<E>{

        int size;//阻塞队列最大容量

        ReentrantLock lock = new ReentrantLock();

        LinkedList<E> list = new LinkedList<>();//队列底层实现

        Condition notFull = lock.newCondition();//队列满时的等待条件
        Condition notEmpty = lock.newCondition();//队列空时的等待条件

        public MyBlockingQueue(int size) {
            this.size = size;
        }
        //加入队列
        public void enqueue(E e)throws InterruptedException{
            lock.lock();
            try {
                while (list.size() == size)  //队列已满，在notFull条件上等待
                    notFull.await();
                list.add(e);//入队：加入链表末尾
                System.out.println("入队：" + e);
                notEmpty.signal();//通知在notEmpty条件上等待的线程
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        //删除队列
        public E dequeue() throws InterruptedException{
            E e;
            lock.lock();
            try {
                while (list.size() == 0)    //队列为空，在notEmpty条件上等待
                    notEmpty.await();
                e = list.removeFirst();     //出队；移出链表首元素
                System.out.println("出队：" + e);
                notFull.signal();//通知notFull条件上等待的线程
                return e;
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }finally {
                lock.unlock();
            }
            return null;
        }
    }
}
