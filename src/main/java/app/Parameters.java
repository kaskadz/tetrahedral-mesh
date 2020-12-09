package app;

import java.util.logging.Logger;

public class Parameters {
    private static final Logger log = Logger.getLogger(Parameters.class.getName());

    public final String processorId;

    public Parameters(String processorId) {
        this.processorId = processorId;
    }

    public static Parameters readArgs(String[] args) {
        if (args.length < 1) {
            failParsing("Processor id should be provided");
        }

        String processorId = args[0];

        return new Parameters(processorId);
    }

    private static void failParsing(String errorMessage) {
        log.severe(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }

    @Override
    public String toString() {
        return String.format("Parameters{processorId='%s'}", processorId);
    }
}
