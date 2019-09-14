package Exceptions;

import org.apache.commons.cli.Options;

public class InvalidArgumentsException extends Exception {

    private Options options;

    public InvalidArgumentsException(Options options) {
        this.options = options;
    }

    public Options getOptions() {
        return options;
    }

}