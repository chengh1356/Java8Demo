package base.queue;

import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * LinkedTransferQueue 底层数据结构由链表实现的无界双向阻塞队列
 * 特性：相比其他的阻塞队列，LinkedBlockingQeque多了addFirst，addLast，offerFirst，offerLast，peekFirst，peekLast
 * 适用场景:
 *          1.一般在生产者-消费者的场景下使用得较多
 * @Description
 * @Author cgh
 * @Date 2020-04-22 下午 5:10
 */
public class LinkedTransferQueueClass {

    public static void main(String[] args) {
        TransferQueue<String> queue = new LinkedTransferQueue<String>();
        Thread producer = new Thread(new Producer(queue));
        producer.setDaemon(true);
        producer.start();

        for (int i = 0; i < 10; i++) {
            Thread consumer = new Thread(new Consumer(queue));
            consumer.setDaemon(true);
            consumer.start();
            try {
                Thread.sleep(1000L);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    static class Producer implements Runnable {
        private final TransferQueue<String> queue;
        public Producer(TransferQueue<String> queue) {
            this.queue = queue;
        }

        private String produce() {
            return "Your lucky number " + (new Random().nextInt(100));
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                while (true) {
                    if (queue.hasWaitingConsumer()) {
                        queue.transfer(produce());
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e){

            }

        }

    }


    static class Consumer implements Runnable{
        private final TransferQueue<String> queue;
        public Consumer(TransferQueue<String> queue) {
            this.queue = queue;
        }
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                System.out.println("Consumer " + Thread.currentThread().getName() + queue.take());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


}
