package designpattern.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @Description 观察者，订阅者
 * @Author cgh
 * @Date 2020-04-01 下午 4:40
 */
public interface MyObserver extends Observer{
    @Override
    void update(Observable o, Object args);

    //添加取消关注的权利
    default void unregister(Observable o){
        o.deleteObserver(this);
    }
    //添加关注的权利
    default void register(Observable o){
        o.addObserver(this);
    }

}
