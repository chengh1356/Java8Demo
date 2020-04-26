package base.classdemo;

import sun.reflect.generics.factory.CoreReflectionFactory;
import sun.reflect.generics.factory.GenericsFactory;
import sun.reflect.generics.repository.ClassRepository;
import sun.reflect.generics.scope.ClassScope;

import java.lang.reflect.TypeVariable;

/**
 * @Description Class源码解析
 * @Author cgh
 * @Date 2020-04-08 上午 10:49
 */
public class SimpleClass<T> {
    private static final int ANNOTATION= 0x00002000;
    private volatile transient ClassRepository genericInfo;
    //获取通用签名
    private native String getGenericSignature0();
    //是否是原始的类
    public native boolean isPrimitive();
    //获取修饰符
    public native int getModifiers();
    //是否是注解类
    public boolean isAnnotation() {
        return (getModifiers() & ANNOTATION) != 0;
    }
    //获取类的泛型的类型
    public TypeVariable<Class<T>>[] getTypeParameters() {
        ClassRepository info = getGenericInfo();
        if (info != null)
            return (TypeVariable<Class<T>>[])info.getTypeParameters();
        else
            return (TypeVariable<Class<T>>[])new TypeVariable<?>[0];
    }
    //获取通用信息
    private ClassRepository getGenericInfo() {
        ClassRepository genericInfo = this.genericInfo;
        if (genericInfo == null) {
            //签名
            String signature = getGenericSignature0();
            if (signature == null) {
                genericInfo = ClassRepository.NONE;
            } else {
                genericInfo = ClassRepository.make(signature, getFactory());
            }
            this.genericInfo = genericInfo;
        }
        return (genericInfo != ClassRepository.NONE) ? genericInfo : null;
    }
    //获取工厂
    private GenericsFactory getFactory() {
        // 创建作用域和工厂
        return CoreReflectionFactory.make(this.getClass(), ClassScope.make(this.getClass()));
    }



}
