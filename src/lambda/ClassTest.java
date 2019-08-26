package lambda;

public class ClassTest {
    public static interface Converter<F, T> {
        T convert(F from);
    }
    public static interface StrConverter{
        String convert(String str);
    }
    static class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    public static void main(String[] args) {
        Something something = new Something();
        StrConverter strConverter = something::startsWith;
        strConverter.convert("Java");
    }
}
