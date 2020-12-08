package app;

import initialization.EntrySymbolInitializer;
import model.TetrahedralGraph;
import org.apache.log4j.BasicConfigurator;
import processing.Assignment1Processor;
import processing.Processor;
import production.Production;
import production.Production1;
import production.Production2;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());
    public static final Production[] productions = {
            new Production1(),
            new Production2()
    };

    private static final Processor[] processors = {
            new Assignment1Processor()
    };

    private static final Map<String, Processor> processorMap = Arrays.stream(processors)
            .collect(Collectors.toMap(
                    Processor::getProcessorId,
                    processor -> processor));

    static {
        int uniqueProcessorCount = Arrays.stream(processors)
                .map(Processor::getProcessorId)
                .collect(Collectors.toSet())
                .size();

        int actualProcessorCount = processors.length;

        assert uniqueProcessorCount == actualProcessorCount;
    }

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        BasicConfigurator.configure();
        Parameters parameters = Parameters.readArgs(args);

        System.out.println(parameters);

        EntrySymbolInitializer initializer = new EntrySymbolInitializer();
        Processor processor = processorMap.get(parameters.processorId);

        if (processor != null) {
            TetrahedralGraph initialGraph = initializer.initializeGraph();

            TetrahedralGraph graph = processor.processGraph(initialGraph);

            graph.displayLevel(parameters.recursionLevel);
        } else {
            System.out.println("Available processors:");
            processorMap.keySet().forEach(x -> System.out.printf(" - %s\n", x));
        }
    }
}
