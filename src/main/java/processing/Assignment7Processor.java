package processing;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Temporary, basic visualisation of production 9
 */
public class Assignment7Processor extends AbstractProcessor {

    @Override
    public String getProcessorId() {
        return "zad7";
    }

    @Override
    public TetrahedralGraph processGraph(TetrahedralGraph graph) {
        graph = buildBasicExampleGraph();

        List<GraphNode> nodes = graph
                .getGraphNodes()
                .stream()
                .filter(x -> x.getSymbol().equals("e"))
                .collect(Collectors.toList());

        List<GraphNode> bottomNodes = graph
                .getGraphNodes()
                .stream()
                .filter(x -> x.getSymbol().equals("E"))
                .collect(Collectors.toList());

        nodes.addAll(bottomNodes);
        getProductionById(9).apply(graph, null, nodes);
        return graph;
    }

    private TetrahedralGraph buildBasicExampleGraph() {
        TetrahedralGraph graph = new TetrahedralGraph();

        // Level 0
        GraphNode initialNode = graph.insertGraphNode(0, "e", new Point2d(0, 0));
        InteriorNode leftI = graph.insertInteriorNode(1, "i");
        graph.connectNodes(leftI, initialNode);
        InteriorNode rightI = graph.insertInteriorNode(1, "i");
        graph.connectNodes(rightI, initialNode);

        // Level 2 - I
        InteriorNode i1 = graph.insertInteriorNode(2, "I");
        InteriorNode i2 = graph.insertInteriorNode(2, "I");
        graph.connectNodes(leftI, i1);
        graph.connectNodes(leftI, i2);

        InteriorNode i3 = graph.insertInteriorNode(2, "I");
        InteriorNode i4 = graph.insertInteriorNode(2, "I");
        graph.connectNodes(rightI, i3);
        graph.connectNodes(rightI, i4);

        // Level 2 - E
        GraphNode e1 = graph.insertGraphNode(2, "E", new Point2d(0, 0));
        graph.connectNodes(i1, e1);
        graph.connectNodes(i3, e1);

        GraphNode e2 = graph.insertGraphNode(2, "E", new Point2d(4, 4));
        graph.connectNodes(i2, e2);
        graph.connectNodes(i4, e2);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e2, e4);

        return graph;
    }
}
