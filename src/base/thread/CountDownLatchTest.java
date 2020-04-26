package base.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Description 假设今天考试，20个学生，1个监考老师。规定学生可以提前交卷，即把卷子留下，直接走人就行了。
 *              但老师必须等到所有的学生都走后，才可以收卷子，然后装订打包。
 * @Author cgh
 * @Date 2020-09-11 下午 2:09
 */
public class CountDownLatchTest {
    static final int COUNT = 20;
    //每完成一个线程，计数器减1，当减到0时，被阻塞的线程自动执行
    static CountDownLatch cdl = new CountDownLatch(COUNT);

    public static void main(String[] args)throws Exception{
        new Thread(new Teacher(cdl)).start();
        Thread.sleep(1);
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Student(i,cdl)).start();
        }
        synchronized (ThreadCo1.class){
            ThreadCo1.class.wait();
        }
    }
    //老师
    static class Teacher implements Runnable{
        CountDownLatch cdl;

        Teacher(CountDownLatch cdl){
            this.cdl = cdl;
        }
        @Override
        public void run() {
            System.out.println("老师发卷子。。。");
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("老师收卷子。。。");
        }
    }
    //学生
    static class Student implements Runnable{

        CountDownLatch cdl;
        int num;

        public Student( int num,CountDownLatch cdl) {
            this.cdl = cdl;
            this.num = num;
        }

        @Override
        public void run() {
            System.out.printf("学生(%d)写卷子。。。\n",num);
            double random = Math.round(Math.random()*5000);

            try {
                Thread.sleep((long)random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("学生(%d)交卷子\n",num);
            cdl.countDown();

        }
    }
    static class ThreadCo1 extends Thread{

    }
}
