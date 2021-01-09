package processing;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.Collections;

public class Assignment2Processor extends AbstractProcessor {

    @Override
    public String getProcessorId() {
        return "zad2";
    }

    @Override
    protected TetrahedralGraph processGraphInternal(TetrahedralGraph graph) {
        InteriorNode entryNode = createLeftSideOfProduction(graph);

        // production 3
        applyProduction(3, graph, entryNode, Collections.emptyList());

        return graph;
    }


    // left side of production
    private InteriorNode createLeftSideOfProduction(TetrahedralGraph graph) {
        int graphLevel = 1;

        InteriorNode center = graph.insertInteriorNode(graphLevel, "I");

        GraphNode bottomLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, -1));
        GraphNode midLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, 0));
        GraphNode topLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, 1));
        GraphNode topRight = graph.insertGraphNode(graphLevel, "E", new Point2d(1, 1));
        GraphNode bottomRight = graph.insertGraphNode(graphLevel, "E", new Point2d(1, -1));;

        graph.connectNodes(center, topLeft);
        graph.connectNodes(center, topRight);
        graph.connectNodes(center, bottomLeft);
        graph.connectNodes(center, bottomRight);

        graph.connectNodes(topLeft, midLeft);
        graph.connectNodes(topLeft, topRight);
        graph.connectNodes(topRight, bottomRight);
        graph.connectNodes(bottomRight, bottomLeft);
        graph.connectNodes(bottomLeft, midLeft);

        return center;
    }
}
