package concurrent.Lock;

import concurrent.ConcurrentUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * 邮戳，利用生成lock时的stamp来区分每个lock
 * 实现写写独立，读读独立，读写独立
 */
public class StampedLockTest1 {
    public static void main(String[] args) {
        readWriteTest();
    }
    private static void readWriteTest(){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String,String> map = new HashMap<>();
        StampedLock lock = new StampedLock();
        Runnable writetask1 = getWriteTask(lock,map,"foo1","bar1");
        Runnable writetask2 = getWriteTask(lock,map,"foo2","bar2");
        executor.submit(writetask1);
        executor.submit(writetask2);
        Runnable task1 = getReadTask(lock,map,"foo1");
        Runnable task2 = getReadTask(lock,map,"foo2");
        executor.submit(task1);
        executor.submit(task2);

        ConcurrentUtils.stop(executor);
    }
    public static Runnable getWriteTask(StampedLock lock, Map<String, String> map, String key,String value){
        Runnable writetask = () -> {
            //邮戳
            long stamp = lock.writeLock();
            try {
                ConcurrentUtils.sleep(1);
                map.put(key,value);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlockWrite(stamp);
            }
        };
        return writetask;
    }

    public static Runnable getReadTask(StampedLock lock, Map<String, String> map, String key){
        Runnable readtask = () ->{
            long stamp = lock.readLock();
            try {
                ConcurrentUtils.sleep(1);
                System.out.println(map.get(key));
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlockRead(stamp);
            }
        };
        return readtask;
    }
}
