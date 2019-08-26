package stream;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntTest {
    public static void main(String[] args) {
        //range
        IntStream.range(0,10)
                .filter(i -> i%2 ==1)
                .forEach(System.out::println);
        System.out.println("***********************************");

        //average
        IntStream.range(0, 10)
                 .average()
                .ifPresent(System.out::println);
        //转换
        Stream.of(new BigDecimal("1.2"), new BigDecimal("3.7"))
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .ifPresent(System.out::println);
        //创建
        int[] ints = {1,3,5,7};
        Arrays.stream(ints).average().ifPresent(System.out::println);

    }
}
