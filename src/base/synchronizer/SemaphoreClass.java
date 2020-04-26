package base.synchronizer;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Description 计数信号量
 *              常用于限制可以访问某些资源的线程数量，例如通过 Semaphore 限流
 * @Author cgh
 * @Date 2020-04-17 下午 3:47
 */
public class SemaphoreClass {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量，只允许3个线程同时访问
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            final long num = i;
            executorService.submit(() -> {
                try {
                    //获取许可
                    semaphore.acquire();
                    //执行
                    System.out.println("Accessing:" + num);
                    Thread.sleep(new Random().nextInt(5000));//模拟随机执行时长
                    //释放
                    semaphore.release();
                    System.out.println("Release..." + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
