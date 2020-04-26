package base.synchronizer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description 倒数计时
 * @Author cgh
 * @Date 2020-04-17 下午 4:02
 */
public class CountDownLatchClass {
    public static void main(String[] args) {
//        normalMethod();
        concurrentMethod();
    }
    //-----------------------------普通示例------------------------------------------------
    private static void normalMethod() {
        CountDownLatch latch = new CountDownLatch(2);
        System.out.println("主线程开始执行。。。。。。");
        //第一个子线程执行
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("子线程："+Thread.currentThread().getName() + "执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        });
        executorService.shutdown();

        //第二个子线程执行
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        executorService1.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程："+Thread.currentThread().getName()+"执行");
            latch.countDown();
        });
        executorService1.shutdown();
        System.out.println("等待两个线程执行完毕。。。。。。");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("两个子线程都执行完毕，继续执行主线程");
    }

    //--------------------------------------并发示例---------------------------------------
    public static void concurrentMethod(){
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatch cd1 = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            CountRunnable runnable = new CountRunnable(cd1);
            pool.execute(runnable);
        }
    }

    static class CountRunnable implements Runnable{
        private CountDownLatch countDownLatch;

        public CountRunnable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                synchronized (countDownLatch){
                    //每次减少一个容量
                    countDownLatch.countDown();
                    System.out.println("thread counts = "+(countDownLatch.getCount()));
                }
                countDownLatch.await();
                System.out.println("concurrency counts = " + (100 - countDownLatch.getCount()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
