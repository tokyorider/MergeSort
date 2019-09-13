package Utility;

@FunctionalInterface
public interface Converter<T> {

    T convert(String str);

}