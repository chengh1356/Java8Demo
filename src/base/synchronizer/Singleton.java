package base.synchronizer;

/**
 * @Description 单例模式
 * @Author cgh
 * @Date 2020-04-16 上午 10:12
 */
public class Singleton {
    private  volatile static Singleton instance;
    //安全的实现方式
    public synchronized static Singleton getInstanceSafe() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }


    //双重检查,可能导致部分加载
    public static Singleton getInstanceDoubleCheck(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
    //懒加载优雅写法 Initialization on Demand Holder（IODH）
    static class SingletonHolder{
        static Singleton instance = new Singleton();
    }
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }
}
