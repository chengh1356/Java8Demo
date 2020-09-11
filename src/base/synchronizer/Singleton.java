package base.synchronizer;

/**
 * @Description 单例模式
 * @Author cgh
 * @Date 2020-04-16 上午 10:12
 */
public class Singleton {
    //volatile 读在写之后执行
    private  volatile static Singleton instance;
    //安全的实现方式
    public synchronized static Singleton getInstanceSafe() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }


    //双重检查（DCL）,可能导致部分加载
    //原因：1.构造函数并不是final方法，在生成单例对象后其实已经算完成，后续的对变量的赋值不对其他内存可见
    //解决方法：使用volatile对对象进行修饰，保证内存可见性
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
    private Singleton() {
    }

    static class SingletonHolder{
        static Singleton instance = new Singleton();
    }
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }
}
