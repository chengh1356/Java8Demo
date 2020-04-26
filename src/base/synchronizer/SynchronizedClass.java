package base.synchronizer;

import java.util.concurrent.SynchronousQueue;

/**
 * @Description 关键字synchronized可以保证在同一时刻，
 *              只有一个线程可以执行某个方法或某个代码块，
 *              同时synchronized可以保证一个线程的变化可见
 * @Author cgh
 * @Date 2020-04-16 上午 10:10
 */
public class SynchronizedClass implements Runnable{
    /**
     * 基础：保证方法级别的原子性，可见性
     *      1.普通同步方法（实例方法），锁是当前实例对象 ，进入同步代码前要获得当前实例的锁
     *      2.静态同步方法，锁是当前类的class对象 ，进入同步代码前要获得当前类对象的锁
     *      3.同步方法块，锁是括号里面的对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。
     * 优点：
     *      1.使用在变量、方法、和类级别的
     *      2.保证变量的修改可见性和原子性
     * 缺点：
     *      1.线程阻塞
     */
    public static void main(String[] args) {
//        normalMethod1();
//        normalMethod2();
        staticMethod();


    }

//---------------------------------1.普通同步方法（实例方法），锁是当前实例对象 ，进入同步代码前要获得当前实例的锁-----------------------------------------------------------

    private static void normalMethod1() {
        final SynchronizedClass test = new SynchronizedClass();
        new Thread(test::method1).start();
        new Thread(test::method2).start();

        //Method 1 start
        //Method 1 execute
        //Method 1 end
        //Method 2 start
        //Method 2 execute
        //Method 2 end
    }

    private static void normalMethod2() {
        final SynchronizedClass test1 = new SynchronizedClass();
        final SynchronizedClass test2 = new SynchronizedClass();
        new Thread(test1::method1).start();
        new Thread(test2::method2).start();

        //Method 1 start
        //Method 1 execute
        //Method 2 start
        //Method 2 execute
        //Method 2 end
        //Method 1 end
    }

    public synchronized void method1() {
        System.out.println("Method 1 start");
        try {
            System.out.println("Method 1 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public synchronized void method2() {
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }
//------------------------------2.静态同步方法，锁是当前类的class对象 ，进入同步代码前要获得当前类对象的锁--------------------------------//
//------------------------------3.同步方法块，锁是括号里面的对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。--------------------------------//


    //共享资源
    static int i =0;
    /**
     * synchronized 修饰实例方法
     */
    public static synchronized void increase(){
        i++;
    }
    @Override
    public void run(){
        //省略其他耗时操作....
        //使用同步代码块对变量i进行同步操作,锁对象为instance
        for (int j =0 ; j<10000;j++){
            increase();
        }
    }

     private static void staticMethod(){
         try {
             Thread t1 = new Thread(new SynchronizedClass());
             Thread t2 = new Thread(new SynchronizedClass());
             t1.start();
             t2.start();
             t1.join();
             t2.join();
             System.out.println(i);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         // 20000
     }


    }
