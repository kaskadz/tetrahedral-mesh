package app;

import java.util.logging.Logger;

public class Parameters {
    private static final Logger log = Logger.getLogger(Parameters.class.getName());

    public final int recursionLevel;
    public final String processorId;

    public Parameters(int recursionLevel, String processorId) {
        this.recursionLevel = recursionLevel;
        this.processorId = processorId;
    }

    public static Parameters readArgs(String[] args) {
        if (args.length <= 0) {
            failParsing("Processor id should be provided");
        }

        int recursionLevel = Integer.parseInt(args[0]);
        if (recursionLevel < 0) {
            failParsing("Recursion level should not be negative");
        }

        if (args.length < 2) {
            failParsing("Processor id should be provided");
        }
        String processorId = args[1];

        return new Parameters(recursionLevel, processorId);
    }

    private static void failParsing(String errorMessage) {
        log.severe(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }

    @Override
    public String toString() {
        return String.format("Parameters{recursionLevel=%d, processorId='%s'}", recursionLevel, processorId);
    }
}
