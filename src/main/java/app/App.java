package app;

import initialization.EntrySymbolInitializer;
import model.TetrahedralGraph;
import org.apache.log4j.BasicConfigurator;
import processing.Processor;
import processing.Processors;
import visualization.MultiLevelVisualizer;
import visualization.MultiStepMultiLevelVisualizer;
import visualization.MultiStepVisualizer;
import visualization.Visualizer;

import java.util.Arrays;
import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        BasicConfigurator.configure();
        Parameters parameters = Parameters.readArgs(args);

        System.out.println(parameters);

        EntrySymbolInitializer initializer = new EntrySymbolInitializer();
        Processor processor = Processors.processorsMap.get(parameters.processorId);

        if (processor != null) {
            Visualizer visualizer = new MultiLevelVisualizer();
            MultiStepVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();
            processor.setMultiStepVisualizer(multiStepVisualizer);

            TetrahedralGraph initialGraph = initializer.initializeGraph();
            TetrahedralGraph graph = processor.processGraph(initialGraph);

            multiStepVisualizer.displayAll();
            visualizer.displayGraph(graph);
        } else {
            System.out.println("Available processors:");
            Arrays.stream(Processors.processors).forEach(x -> System.out.printf(" - %s\n", x.getProcessorId()));
        }
    }
}
