import Arguments.*;
import Exceptions.InvalidArgumentsException;
import Utility.Converter;
import Utility.StreamCopier;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

    public static void main(String[] args) {
        Arguments arguments;
        try {
            arguments = ArgResolver.resolveArgs(args);
        } catch (InvalidArgumentsException e) {
            e.printStackTrace();
            HelpFormatter formatter = new HelpFormatter();
            return;
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        String sortOrder = arguments.order, dataType = arguments.dataType;
        if (dataType.equals("i")) {
            if (sortOrder.equals("a")) {
                mergeAllFiles(arguments, Integer::compareTo, Integer::parseInt);
            } else {
                mergeAllFiles(arguments, ((num1, num2) -> -1 * num1.compareTo(num2)), Integer::parseInt);
            }
        } else {
            if (sortOrder.equals("a")) {
                mergeAllFiles(arguments, String::compareTo, (str -> str));
            } else {
                mergeAllFiles(arguments, ((str1, str2) -> -1 * str1.compareTo(str2)), (str -> str));
            }
        }
    }

    private static <T>void mergeAllFiles(Arguments arguments, Comparator<T> comparator, Converter<T> converter) {
        ConcurrentLinkedQueue<String> inQueue = arguments.inQueue;
        int i = 1;
        while (inQueue.size() != 1) {
            try (BufferedReader first = new BufferedReader(new InputStreamReader(
                    new FileInputStream(inQueue.poll()), StandardCharsets.UTF_8));
                 BufferedReader second = new BufferedReader(new InputStreamReader(
                         new FileInputStream(inQueue.poll()), StandardCharsets.UTF_8));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                         new FileOutputStream("tmp" + i + ".krl2004"), StandardCharsets.UTF_8))) {
                Sorter.mergeSort(first, second, out, comparator, converter);
                out.flush();

                inQueue.add("tmp" + i + ".krl2004");
                ++i;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inQueue.poll()), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arguments.out), StandardCharsets.UTF_8)))
        {
            StreamCopier.copyWithSkippingUnsortedData(in, out, comparator, converter);
        } catch(IOException e) {
            e.printStackTrace();
        }

        deleteTmpFiles(i);
    }

    private static void deleteTmpFiles(int count) {
        for (int j = 1; j < count; j++) {
            if (!new File("tmp" + j + ".krl2004").delete()) {
                System.err.println("File deletion error");
            }
        }
    }

}
