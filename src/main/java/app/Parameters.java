package app;

import java.util.Arrays;
import java.util.logging.Logger;

public class Parameters {
    private static final Logger log = Logger.getLogger(Parameters.class.getName());

    public final int recursionLevel;
    public final int[] productionIds;

    public Parameters(int recursionLevel, int[] productionIds) {
        this.recursionLevel = recursionLevel;
        this.productionIds = productionIds;
    }

    public static Parameters readArgs(String[] args) {
        if (args.length <= 0) {
            failParsing("Recursion level should be provided");
        }

        int recursionLevel = Integer.parseInt(args[0]);
        if (recursionLevel < 0) {
            failParsing("Recursion level should not be negative");
        }

        var productionIds = Arrays.stream(args)
                .skip(1)
                .mapToInt(Integer::parseInt)
                .toArray();

        if (productionIds.length <=0) {
            failParsing("No productions were provided");
        }

        return new Parameters(recursionLevel, productionIds);
    }

    private static void failParsing(String errorMessage) {
        log.severe(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }

    @Override
    public String toString() {
        return "Parameters{" +
                "recursionLevel=" + recursionLevel +
                ", productionIds=" + Arrays.toString(productionIds) +
                '}';
    }
}
