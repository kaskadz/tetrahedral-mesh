package app;

import model.GraphNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.apache.log4j.BasicConfigurator;

import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Parameters parameters = Parameters.readArgs(args);

        System.out.println(parameters);
        TetrahedralGraph graph = new TetrahedralGraph("xd");

//        new GraphNode(graph, "H", 0, new Point2d(1.0, 1.0));

        graph.forEach(x -> System.out.println(x.getId()));

        graph.display();
    }
}
