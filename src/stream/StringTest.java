package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StringTest {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        stringList.add("ddd2");
        stringList.add("aaa2");
        stringList.add("bbb1");
        stringList.add("aaa1");
        stringList.add("bbb3");
        stringList.add("ccc");
        stringList.add("bbb2");
        stringList.add("ddd1");

        //filter
        stringList.stream()
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::println);
        // "aaa2","aaa1"

        //sort
        stringList.stream()
                .sorted((a1,a2) ->{
                    System.out.printf("sort:  %s,%s\n",a1,a2);
                    return a1.compareTo(a2);
                })
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::println);
        //"aaa2","aaa1"

        //map
        stringList.stream()
                .map(s -> s.toUpperCase())
                .sorted((a,b) -> b.compareTo(a))
                .forEach(System.out::println);

        //match
        boolean anyMatchFlag = stringList.stream()
                                    .anyMatch(s -> s.startsWith("a"));
        System.out.println(anyMatchFlag);

        //count
        long count = stringList.stream()
                            .filter(s -> s.startsWith("a"))
                            .count();
        System.out.println(count);

        //reduce
        Optional<String> reduced = stringList.stream()
                                            .sorted()
                                            .reduce((s1,s2) -> s1 + "#" +s2);
        reduced.ifPresent(System.out::println);

        //Supplier
        Supplier<Stream<String>> streamSupplier = ()->stringList.stream().filter(s -> s.startsWith("a"));
        boolean anyMatchFlag2 = streamSupplier.get().anyMatch(s -> true);
        boolean noneMatchFlag2 = streamSupplier.get().noneMatch(s -> true);
        System.out.println(anyMatchFlag2);
        System.out.println(noneMatchFlag2);

        //findFirst
        stringList.stream()
                .findFirst()
                .ifPresent(System.out::println);

        //max
        stringList.stream()
                .map(s -> s.substring(2))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);


    }
}
