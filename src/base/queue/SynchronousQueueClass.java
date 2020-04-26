package base.queue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description 一种没有缓冲的队列，生产者生产的数据会直接被消费者获取并消费
 *             适用场景：
 *              非常适合于传递性设计，在这种设计中，在一个线程中运行的对象要将某些信息、
 *              事件或任务传递给在另一个线程中运行的对象，它就必须与该对象同步。
 * @Author cgh
 * @Date 2020-04-22 下午 4:01
 */
public class SynchronousQueueClass {
    /**
     * isEmpty()永远是true。
     * remainingCapacity() 永远是0。
     * remove()和removeAll() 永远是false。
     */
        public static void main(String[] args) {
            SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
            Thread t1= new Product(queue);
            Thread t2= new Customer(queue);
            t1.start();
            t2.start();
        }

    }
    /**
     * 模拟生产者
     * @author Administrator
     *
     */
    class Product extends Thread{
        SynchronousQueue<Integer> queue = null;

        public Product(){

        }

        public Product(SynchronousQueue<Integer> queue){
            this.queue = queue;
        }

        @Override
        public void run() {
            int rand = new Random().nextInt(1000);
            System.out.println(String.format("模拟生产者：%d",rand));
            try{
                TimeUnit.SECONDS.sleep(3);
                queue.put(rand);
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println(queue.isEmpty());
        }

    }
    /**
     * 模拟消费者
     * @author Administrator
     *
     */
    class Customer extends Thread{
        SynchronousQueue<Integer> queue = null;

        public Customer(){

        }

        public Customer(SynchronousQueue<Integer> queue){
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("消费者已经准备好接受元素了...");
            try{
                System.out.println(String.format("消费一个元素：%d", queue.take()));
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("------------------------------------------");
        }


}
