package designpattern.observer;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * @Description 被观察者，报社
 * @Author cgh
 * @Date 2020-04-01 下午 4:39
 */
public class MyObservable extends Observable {
    private boolean changed = false;
    //不可扩展
    private Vector<Observer> obs;

    public MyObservable() {
        obs = new Vector<>();
    }


    @Override
    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        obs.removeElement(o);
    }


    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }


    @Override
    public void notifyObservers(Object arg) {
        /*
         * 一个快照数组，用于发布给当前状态的观察者们
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {

            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }
        //默认写死为倒序通知，最好能改成接口，正序倒序都可以
        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
    }

    @Override
    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    /**
     * 使用时必须使setChanged方法和notifyObservers方法进行synchronized操作，使之成为“原子操作”
     */
    @Override
    protected synchronized void setChanged() {
        changed = true;
    }

    @Override
    protected synchronized void clearChanged() {
        changed = false;
    }

    @Override
    public synchronized boolean hasChanged() {
        return changed;
    }

    @Override
    public synchronized int countObservers() {
        return obs.size();
    }
}
