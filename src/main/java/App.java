import org.apache.log4j.BasicConfigurator;

import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Parameters parameters = readArgs(args);

        System.out.printf("Recursion level = %d", parameters.recursionLevel);
    }

    private static Parameters readArgs(String[] args) {
        if(args.length <=0){
            String errorMessage = "Recursion level should be provided";
            log.severe(errorMessage);
            throw new IllegalArgumentException();
        }

        int recursionLevel = Integer.parseInt(args[0]);
        if (recursionLevel < 0) {
            String errorMessage = "Recursion level should not be negative";
            log.severe(errorMessage);
            throw new IllegalArgumentException();
        }

        return new Parameters(recursionLevel);
    }
}
