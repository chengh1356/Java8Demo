package concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Executors2 {
    public static void main(String[] args) throws InterruptedException {
        //Schedual
//        getRemainDelay();
//        getTaskByPeriod();
//        getFixedDelay();
        getinvokeAll();
    }
    private static void getinvokeAny() throws ExecutionException, InterruptedException {
        //1.创建workExecutor
        ExecutorService executor = Executors.newWorkStealingPool();
        //2.创建callable的list
        List<Callable<String>> callables = Arrays.asList(
                callable("task1",3),
                callable("task2",1),
                callable("task3",5)
        );
        //3.executor执行
        String result = executor.invokeAny(callables);
        System.out.println(result);
        //4.关闭executor
        executor.shutdownNow();
    }
    private static Callable<String> callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }
    private static void getinvokeAll() throws InterruptedException {
        //1.创建workExecutor
        ExecutorService executor = Executors.newWorkStealingPool();
        //2.创建callable的list
        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3"
                );
        //3.executor执行invoke
        executor.invokeAll(callables)
                .stream()
                .map(future ->{
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
        executor.shutdownNow();
    }
    //设置多重延迟
    private static void getFixedDelay(){
        //1.创建schedualExecutor
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        //2.创建task
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("Schedual : "+ System.nanoTime());
            } catch (InterruptedException e) {
                System.err.printf("task interrupted");
            }
        };
        //3.executor执行
        executor.scheduleWithFixedDelay(task,0,5,TimeUnit.SECONDS);
    }
    //间隔固定时间period执行task
    private static void getTaskByPeriod(){
        //1.创建schedualExecutor
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        //2.创建task
        Runnable task = () -> System.out.println("Schedual: "+ System.nanoTime());
        //3.设置initialDealy(初始化延迟时间)；设置period(间隔时间)
        int initialDealy = 0;
        int period = 2;
        //4.executor执行
        executor.scheduleAtFixedRate(task,initialDealy,period,TimeUnit.SECONDS);
    }
    //获取剩余延迟时间
    private static void getRemainDelay() throws InterruptedException {
        //1.创建schedualexcutor
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        //2.创建task
        Runnable task = () -> System.out.println("Schudaling : "+ System.nanoTime());
        //3.创建future
        int delay = 3;
        ScheduledFuture<?> future =  executor.schedule(task, delay, TimeUnit.SECONDS);
        //4.设置sleep时间
        TimeUnit.MILLISECONDS.sleep(1337);
        //5.执行future获取剩余time
        long remainDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remain Dealy : %sms\n",remainDelay);
    }
}
