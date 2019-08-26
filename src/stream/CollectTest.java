package stream;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectTest {
    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        List<Person> persons =
                Arrays.asList(
                        new Person("Max", 18),
                        new Person("Peter", 23),
                        new Person("Pamela", 23),
                        new Person("David", 12));
//        GroupByTest(persons);
//        AverageTest(persons);
//        SummaryTest(persons);
        JoinTest2(persons);
//        ToMapTest(persons);
//        OfTest(persons);

    }
    public static void GroupByTest(List<Person> persons){
        //group by
        List<Person> filtered = persons
                .stream()
                .filter(p -> p.name.startsWith("P"))
                .collect(Collectors.toList());
        System.out.println(filtered);

        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));
        personsByAge.forEach((age,p) -> System.out.printf("age %s:%s\n",age,p));
    }
    public  static void AverageTest(List<Person> persons){
        Double avg = persons
                    .stream()
                    .collect(Collectors.averagingInt(p -> p.age));
        System.out.println(avg);
    }

    public static void SummaryTest(List<Person> persons){
        IntSummaryStatistics age = persons.stream()
                .collect(Collectors.summarizingInt(p -> p.age));
        System.out.println(age);
    }

    public static void JoinTest(List<Person> persons){
        String names = persons.stream()
                            .filter(p -> p.age >= 18)
                            .map(p -> p.name)
                            .collect(Collectors.joining(" and ","In Germany "," are of legal age."));
        System.out.println(names);
    }



    public static void ToMapTest(List<Person> persons){
        Collector<Person, ?, Map<Integer, String>> mapCollectors = Collectors.toMap(
                p -> p.age,
                p -> p.name,
                //当age相同时，name用";"分隔
                (name1, name2) -> name1 + ";" + name2
        );
        Map<Integer, String> map = persons.stream()
                .collect(mapCollectors);
        System.out.println(map);
    }



    public static void OfTest(List<Person> persons){
        Collector<Person, StringJoiner, String> personNameCollector = Collector.of(
                () -> new StringJoiner("|"),
                (j, p) -> j.add(p.name.toUpperCase()),
                (j1, j2) -> j1.merge(j2),
                StringJoiner::toString
        );
        String names = persons.stream()
                            .collect(personNameCollector);
        System.out.println(names);

    }
    public static void JoinTest2(List<Person> persons){
        String names = persons.stream()
                //.filter(p -> p.age >= 18)
                .map(p -> p.name.toUpperCase())
                .collect(Collectors.joining(" | "));
        System.out.println(names);
    }
}
