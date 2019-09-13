package Utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Comparator;

public class StreamCopier {

    public static void copy(BufferedReader from, BufferedWriter to, String str) throws IOException {
        while (str != null) {
            to.write(str);
            to.newLine();
            str = from.readLine();
        }
    }

    public static <T>void copyWithSkippingUnsortedData(BufferedReader from, BufferedWriter to,
                                                   Comparator<T> comparator, Converter<T> converter) throws IOException
    {
        String str = from.readLine(), prevStr = str;
        while (str != null) {
            if (comparator.compare(converter.convert(prevStr), converter.convert(str)) <= 0) {
                to.write(str);
                to.newLine();
                prevStr = str;
                System.err.println("Warning: files are unsorted. Some data will lost");
            }
            str = from.readLine();
        }
    }

}