package lambda;

public class ExtendTest {
    interface PersonFactory<P extends Person>{
        P create(String firstName,String lastName);
    }
    static class Person {
        public String firstName;
        public String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }
    public static void main(String[] args) {
        PersonFactory<Person> factory = Person::new;
        Person person = factory.create("chen", "gh");
        System.out.println(person);
    }
}
