package app;

import org.apache.log4j.BasicConfigurator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Parameters parameters = Parameters.readArgs(args);

        System.out.println(parameters);
        Graph graph = new SingleGraph("Tutorial 1");

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        graph.display();
    }
}
