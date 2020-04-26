package base.thread;

/**
 * @Description
 * @Author cgh
 * @Date 2020-09-11 上午 10:34
 */
public class ThreadStopTest {
    public static void main(String[] args) {
//        stopByFlag();     //停止
//        pauseByFlag();      //暂停
//        jqByJoin();         //插队
        stopByInterrupt();  //叫醒
    }

    /**
     * 线程在预设的地点检测flag,来决定是否停止
     */
    static void stopByFlag(){
        ARunnable ar = new ARunnable();
        new Thread(ar).start();
        ar.tellToStop();
    }
    static class ARunnable implements Runnable{
        volatile boolean stop;

        void tellToStop(){
            stop = false;
        }

        @Override
        public void run() {
            try {
                System.out.println("进入不可停止区域1.。。");

                Thread.sleep(5000);
                System.out.println("退出不可停止区域1 。。。");
                System.out.printf("检测标志stop = %s\n",String.valueOf(stop));
                if(stop){
                    System.out.println("停止执行");
                    return;
                }
                System.out.println("进入不可停止区域2 。。。");
                Thread.sleep(5000);
                System.out.println("退出不可停止区域2 。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //预设点检测flag，然后wait/notify
    static void pauseByFlag(){
        try {
            BRunnable br = new BRunnable();
            Thread t = new Thread(br);
            t.start();
            br.tellToPause();
            Thread.sleep(8000);
            br.tellToResume();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class BRunnable implements Runnable{
        volatile boolean pause;

        //告诉对方暂停
        void tellToPause(){
            pause = true;
        }
        //告诉对方恢复
        void tellToResume(){
            synchronized (this){
                this.notify();
            }
        }

        @Override
        public void run() {
            try {
                System.out.println("进入不可暂停区域1。。。");
                Thread.sleep(5000);
                System.out.println("退出不可暂停区域1。。。");
                System.out.printf("检测标志pause = %s\n",String.valueOf(pause));
                if(pause){
                    System.out.println("暂停执行");
                    try {
                        synchronized (this){
                            this.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("恢复执行");
                }
                System.out.println("进入不可暂停区域2。。。");
                Thread.sleep(5000);
                System.out.println("退出不可暂停区域2。。。");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //join方法可以让线程查到自己前面，等到它执行完，自己才会继续执行
    static void jqByJoin(){
        try {
            CRunnable cr = new CRunnable();
            Thread t = new Thread(cr);
            t.start();
            Thread.sleep(1000);
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("终于轮到我了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class CRunnable implements Runnable{

        @Override
        public void run() {
            try {
                System.out.println("进入不可暂停区域1.。。");
                Thread.sleep(5000);
                System.out.println("退出不可暂停区域1.。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void stopByInterrupt(){
        try {
            DRunnable dr = new DRunnable();
            Thread t = new Thread(dr);
            t.start();
            Thread.sleep(2000);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class DRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("进入暂停。。。");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("收到中断异常");
                System.out.println("做一些相关处理。。。");
            }
            System.out.println("继续执行或选择退出。。。");
        }
    }
}
