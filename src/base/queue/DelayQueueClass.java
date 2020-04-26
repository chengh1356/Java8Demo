package base.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description  带有延迟时间的queue
 *              应用场景：
 *                  1.对缓存超时的数据进行移除
 *                  2.任务超时处理
 *                  3.空闲连接的关闭
 * @Author cgh
 * @Date 2020-04-22 下午 4:14
 */
public class DelayQueueClass {
    public static void main(String[] args) {
        //新建一个网吧
        InternetBar internetBar=new InternetBar();
        //来了三个网民上网
        internetBar.startPlay("001","侯征",3);
        internetBar.startPlay("002","姚振",7);
        internetBar.startPlay("003","何毅",5);
        Thread t1=new Thread(internetBar);
        t1.start();
    }
    //网民
    static class Netizen implements Delayed {
        //身份证
        private String ID;
        //名字
        private String name;
        //上网截止时间
        private long playTime;

        //比较优先级,时间最短的优先
        @Override
        public int compareTo(Delayed o) {
            Netizen netizen = (Netizen) o;
            return this.getDelay(TimeUnit.SECONDS) - o.getDelay(TimeUnit.SECONDS) > 0 ? 1 : 0;
        }

        public Netizen(String iD, String name, long playTime) {
            ID = iD;
            this.name = name;
            this.playTime = playTime;
        }

        //获取上网时长,即延时时长
        @Override
        public long getDelay(TimeUnit unit) {
            //上网截止时间减去现在当前时间=时长
            return this.playTime - System.currentTimeMillis();
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getPlayTime() {
            return playTime;
        }

        public void setPlayTime(long playTime) {
            this.playTime = playTime;
        }
    }

    static class InternetBar implements Runnable {
        //网民队列,使用延时队列
        private DelayQueue<Netizen> dq=new DelayQueue<Netizen>();
        //上网
        public void startPlay(String id,String name,Integer money){
            //截止时间= 钱数*时间+当前时间(1块钱1秒)
            Netizen netizen=new Netizen(id,name,1000*money+System.currentTimeMillis());
            System.out.println(name+"开始上网计费......");
            dq.add(netizen);
        }
        //时间到下机
        public void endTime(Netizen netizen){
            System.out.println(netizen.getName()+"余额用完,下机");
        }
        @Override
        public void run() {
            //线程,监控每个网民上网时长
            while(true){
                try {
                    //除非时间到.否则会一直等待,直到取出这个元素为止
                    Netizen netizen=dq.take();
                    endTime(netizen);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
