package base.classdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author cgh
 * @Date 2020-04-09 下午 5:11
 */
public class FiveClass {
    // There are five kinds of classes (or interfaces):
    // a) Top level classes
    // b) Nested classes (static member classes)
    // c) Inner classes (non-static member classes)
    // d) Local classes (named classes declared within a method)
    // e) Anonymous classes

    //静态内部类
    public static class NestedClass{

    }
    //非静态内部类
    public class InnerClass{

    }

    public static void main(String[] args) {
        //方法内的类
        class localClass{

        }
        //Anonymous classes 匿名类
        List list = new ArrayList<String>();;
        list.forEach((i) ->{
            System.out.println(i);
        });
    }

}
