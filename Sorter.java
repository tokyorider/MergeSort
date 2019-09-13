import Utility.Converter;
import Utility.StreamCopier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Comparator;

public class Sorter {

    public static <T> void mergeSort(BufferedReader first, BufferedReader second, BufferedWriter out,
                                    Comparator<T> comparator, Converter<T> converter) throws IOException
    {
        String firstStr = first.readLine(), secondStr = second.readLine();
        while (firstStr != null && secondStr != null) {
            if (comparator.compare(converter.convert(firstStr), converter.convert(secondStr)) <= 0) {
                out.write(firstStr);
                firstStr = first.readLine();
            } else {
                out.write(secondStr);
                secondStr = second.readLine();
            }
            out.newLine();
        }

        if (firstStr != null) {
            StreamCopier.copy(first, out, firstStr);
        } else if (secondStr != null) {
            StreamCopier.copy(second, out, secondStr);
        }
    }

}
