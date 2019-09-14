package Arguments;

import Exceptions.InvalidArgumentsException;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ArgResolver {

    public static Arguments resolveArgs(String[] commandLineArgs) throws ParseException, InvalidArgumentsException {
        Options options = new Options();
        options.addOption("a", "Ascending sort order");
        options.addOption("d", "Descending sort order");
        options.addOption("i", "Integer data type");
        options.addOption("s", "String data type");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, commandLineArgs);
        SortingOrder sortOrder = SortingOrder.ASCENDING;
        DataType dataType;
        int outPos, inPos, requiredLength;

        if (cmd.hasOption("a") || cmd.hasOption("d")) {
            if (cmd.hasOption("d")) {
                sortOrder = SortingOrder.DESCENDING;
            }

            outPos = 2;
        } else {
            outPos = 1;
        }
        inPos = outPos + 1;
        requiredLength = inPos + 1;
        if (commandLineArgs.length < requiredLength) {
            throw new InvalidArgumentsException(options, "");
        }

        if (cmd.hasOption("i")) {
            dataType = DataType.INTEGER;
        } else if (cmd.hasOption("s")) {
            dataType = DataType.STRING;
        } else {
            throw new InvalidArgumentsException(options, "");
        }

        String out = commandLineArgs[outPos];
        ConcurrentLinkedQueue<String> fileQueue = new ConcurrentLinkedQueue<>(Arrays.asList(
                Arrays.copyOfRange(commandLineArgs, inPos, commandLineArgs.length)));

        return new Arguments(sortOrder, dataType, out, fileQueue);
    }

}