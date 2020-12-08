package app;

import model.TetrahedralGraph;
import org.apache.log4j.BasicConfigurator;
import processing.Initializer;
import processing.Processor;
import production.Production;
import production.Production1;
import production.Production2;

import java.util.Arrays;
import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());
    private static final Production[] productions = {
            new Production1(),
            new Production2()
    };

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        BasicConfigurator.configure();
        Parameters parameters = Parameters.readArgs(args);

        System.out.println(parameters);

        Initializer initializer = new Initializer();
        Processor processor = new Processor(Arrays.asList(productions));

        TetrahedralGraph graph = initializer.initializeGraph();

        graph = processor.applyProductions(graph, parameters.productionIds);

        graph.displayLevel(parameters.recursionLevel);
    }
}
