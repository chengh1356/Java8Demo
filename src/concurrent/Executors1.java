package concurrent;

import java.util.concurrent.*;


public class Executors1 {
    public static void main(String[] args) {
        test1(3);
//        test1(7);
        //test2();
    }
    private static void test1(long seconds){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() ->{
            try {
                TimeUnit.SECONDS.sleep(seconds);
                //获取当前线程名称
                String name = Thread.currentThread().getName();
                System.out.println("task finished: "+name);
            } catch (InterruptedException e) {
                System.out.println("task interrupted");
            }
        });
        stop(executor);
    }
    static void stop(ExecutorService executor){
        try {
            //1.尝试关闭executor，因为executor尚在sleep中，未能关闭
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            //2.等待5秒执行时间
            executor.awaitTermination(5,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("termination interrupted");
        }finally {
            //3.判断task是否执行完毕
            if(!executor.isTerminated()){
                //3.1否则杀死未结束的tasks
                System.err.println("kill non-finished tasks");
            }
            //4.结束executor
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }
    //执行顺序
    private static void test2() throws ExecutionException, InterruptedException {
        //1.创建executor
        ExecutorService executor = Executors.newFixedThreadPool(1);
        //2.创建future
        Future<Integer> future = executor.submit(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });
        System.out.println("future done: "+future.isDone());
        //3.执行future
        Integer result = future.get();
        System.out.println("future done: "+future.isDone());
        System.out.println("result :"+ result);
        //关闭executor
        executor.shutdownNow();
    }
}
