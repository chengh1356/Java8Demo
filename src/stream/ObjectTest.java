package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ObjectTest {
    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        IntStream.range(1,4)
                .mapToObj(num -> new Foo("Foo"+num))
                .peek(foo -> IntStream.range(1,5)
                      .mapToObj(num -> new Bar("Bar"+num+" <- "+foo.name))
                      .forEach(foo.bars::add))
                .flatMap(foo -> foo.bars.stream())
                .forEach(bar -> System.out.println(bar.name));


    }
}
