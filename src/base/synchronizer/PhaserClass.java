package base.synchronizer;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Description题目：
 *  5个学生参加考试，一共有三道题，要求所有学生到齐才能开始考试
 *  ，全部做完第一题，才能继续做第二题，后面类似。
 *  Phaser有phase和party两个重要状态，
 *  phase表示阶段，party表示每个阶段的线程个数，
 *  只有每个线程都执行了phaser.arriveAndAwaitAdvance();
 *  才会进入下一个阶段，否则阻塞等待。
 *  例如题目中5个学生(线程)都条用phaser.arriveAndAwaitAdvance();就进入下一题
 * @Author cgh
 * @Date 2020-04-20 上午 8:29
 */
public class PhaserClass {
    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser();
        StudentTask[] studentTask = new StudentTask[5];
        for (int i = 0; i < studentTask.length; i++) {
            studentTask[i] = new StudentTask(phaser);
            phaser.register();      //标识phaser维护的线程个数
        }

        Thread[] threads = new Thread[studentTask.length];
        for (int i = 0; i < studentTask.length; i++) {
            threads[i] = new Thread(studentTask[i],"Student " + i);
            threads[i].start();
        }
        //等待所有线程执行结束
        for (int i = 0; i < studentTask.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Phaser has finished:" + phaser.isTerminated());

    }

    static class MyPhaser extends Phaser{
        //分阶段
        @Override
        protected boolean onAdvance(int phase,int registeredParties){
            switch (phase){
                case 0:
                    return studentArrived();
                case 1:
                    return finishFirstExecise();
                case 2:
                    return finishSecondExecise();
                case 3:
                    return finishExam();
                default:
                    return true;
            }
        }

        private boolean studentArrived(){
            System.out.println("学生准备好了，学生人数："+getRegisteredParties());
            return false;
        }

        private boolean finishFirstExecise(){
            System.out.println("第一题所有学生做完");
            return false;
        }

        private boolean finishSecondExecise(){
            System.out.println("第二题所有学生做完");
            return false;
        }

        private boolean finishExam(){
            System.out.println("第三题所有学生做完，结束考试");
            return true;
        }
    }

    static class StudentTask implements Runnable{
        private Phaser phaser;

        public StudentTask(Phaser phaser) {
            this.phaser = phaser;
        }


        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "到达考试");
            phaser.arriveAndAwaitAdvance();

            System.out.println(Thread.currentThread().getName() + "做第1题时间....");
            doExecise1();
            System.out.println(Thread.currentThread().getName() + "做第1题时间完成...");
            phaser.arriveAndAwaitAdvance();

            System.out.println(Thread.currentThread().getName() + "做第2题时间....");
            doExecise2();
            System.out.println(Thread.currentThread().getName() + "做第2题时间完成...");
            phaser.arriveAndAwaitAdvance();

            System.out.println(Thread.currentThread().getName() + "做第3题时间....");
            doExecise3();
            System.out.println(Thread.currentThread().getName() + "做第3题时间完成...");
            phaser.arriveAndAwaitAdvance();

        }

        private void doExecise1(){
            long duration = (long) (Math.random() * 10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void doExecise2(){
            long duration = (long) (Math.random() * 10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void doExecise3(){
            long duration = (long) (Math.random() * 10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
