package lambda;

public class InterfaceTest {
    public static void main(String[] args) {

        FunctionInterface fc = new FunctionInterface() {
            @Override
            public void test(int param) {
                System.out.println("复杂的Hello World "+ param);
            }
        };
        fc.test(1);
        fc = (param)->System.out.println("复杂的Hello World "+ param);
        fc.test(1);

        Function2Interface<Integer> fc2 = (Integer x2) -> System.out.println("泛型的要有参数类型:"+ x2);
        fc2.test(2);


        Function3Interface<Integer> fc3 = (Integer x3) -> {
            System.out.println("有参数有返回值，参数为泛型:"+x3);
            return true;
        };
        fc3.test(3);

        Function4Interface<Boolean,Integer> fc4 = (Integer x4) -> {
            System.out.println("有参数有返回值，参数和返回值均为泛型:"+x4);
            return true;
        };
        fc4.test(4);
    }
    /**
     * 函数接口：只有一个方法的接口。作为Lambda表达式的类型
     */
    //有参数
    public interface FunctionInterface {
        void test(int param);
    }
    //参数为泛型
    public interface Function2Interface<T> {
        void test(T param);
    }
    //有参数有返回值，参数为泛型
    public interface Function3Interface<T> {
        Boolean test(T param);
    }
    //有参数有返回值，参数和返回值均为泛型
    public interface Function4Interface<T,F>{
        T test(F param);
    }


}
