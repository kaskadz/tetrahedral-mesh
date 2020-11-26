package app;

import model.TetrahedralGraph;
import org.apache.log4j.BasicConfigurator;
import processing.Initializer;
import processing.Processor;
import production.Production;

import java.util.Arrays;
import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());
    private static final Production[] productions = {
    };

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Parameters parameters = Parameters.readArgs(args);

        System.out.println(parameters);

        var initializer = new Initializer();
        var processor = new Processor(Arrays.asList(productions));

        TetrahedralGraph graph = initializer.initializeGraph();

        graph = processor.applyProductions(graph, parameters.productionIds);

        graph.displayLevel(0);
    }
}
