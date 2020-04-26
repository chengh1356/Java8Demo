package base;

import base.classdemo.SimpleClass;

/**
 * @Description
 * @Author cgh
 * @Date 2020-04-07 下午 4:43
 */
public class LongClass {
    public static void main(String[] args) {
        SimpleClass simpleClass = new SimpleClass();

        System.out.println(simpleClass.getClass().toGenericString());

    }
}
