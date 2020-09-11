package base.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * @Description 最近景色宜人，公司组织去登山，大伙都来到了山脚下，登山过程自由进行。
 *
 *              但为了在特定的地点拍集体照，规定1个小时后在半山腰集合，谁最后到的，要给大家表演一个节目。
 *
 *              然后继续登山，在2个小时后，在山顶集合拍照，还是谁最后到的表演节目。
 *
 *              接着开始下山了，在2个小时后在山脚下集合，点名回家，最后到的照例表演节目。
 * @Author cgh
 * @Date 2020-09-11 下午 2:36
 */
public class CyclicBarrierTest02 {



    public static void main(String[] args) {
        final int COUNT = 5;
        //某个线程到达预设点时就在此等待，等所有的线程都到达时，大家再一起向下个预设点出发。如此循环反复下去。
        CyclicBarrier cb = new CyclicBarrier(COUNT,new Singer());
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Staff(i,cb)).start();
        }
        synchronized (CyclicBarrierTest02.class){
            try {
                CyclicBarrierTest02.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    static class Singer implements Runnable{

        @Override
        public void run() {
            System.out.println("为大家唱歌。。。");
        }
    }

    static class Staff implements Runnable{

        CyclicBarrier cb;
        int num;

        public Staff( int num,CyclicBarrier cb) {
            this.cb = cb;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 1; i < 4; i++) {
                println("员工(%d)出发",num);
                try {
                    doingLongTime();
                    System.out.printf("员工(%d)到达地点%d。。。\n",num,i);
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
            /*println("员工(%d)出发",num);
            try {
                println("员工(%d)到达地点一。。。",num);
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            println("员工(%d)再出发。。。", num);
            doingLongTime();
            println("员工(%d)到达地点二。。。", num);
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            println("员工(%d)再出发。。。", num);
            doingLongTime();
            println("员工(%d)到达地点三。。。", num);
            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            println("员工(%d)结束。。。", num);
        }

        private void doingLongTime() {
            try {
                double random = Math.round(Math.random()*5000);
                Thread.sleep((long)random);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private void println(String str,int num){
            System.out.printf(str+"\n",num);
        }
    }
    static class ThreadCo2 extends Thread{
        public void run(){
            System.out.println("员工集合。。。");
        }
    }
}
