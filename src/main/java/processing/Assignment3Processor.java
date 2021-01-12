package processing;

import common.CustomCollectors;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.Collections;

public class Assignment3Processor extends AbstractProcessor {
    private final static String PROCESSOR_ID = "zad3";

    @Override
    public String getProcessorId() {
        return PROCESSOR_ID;
    }

    @Override
    protected TetrahedralGraph processGraphInternal(TetrahedralGraph graph) {
        InteriorNode entryNode = createLeftSideOfProduction(graph);

        applyProduction(5, graph, entryNode, Collections.emptyList());

        return graph;
    }

    // left side of production
    private InteriorNode createLeftSideOfProduction(TetrahedralGraph graph) {
        int graphLevel = 1;

        InteriorNode center = graph.insertInteriorNode(graphLevel, "I");

        GraphNode bottomLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, -1));
        GraphNode midLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, 0));
        GraphNode topLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, 1));
        GraphNode midTop = graph.insertGraphNode(graphLevel, "E", new Point2d(0, 1.0));
        GraphNode topRight = graph.insertGraphNode(graphLevel, "E", new Point2d(1, 1));
        GraphNode midRight = graph.insertGraphNode(graphLevel, "E", new Point2d(1, 0));
        GraphNode bottomRight = graph.insertGraphNode(graphLevel, "E", new Point2d(1, -1));

        graph.connectNodes(center, topLeft);
        graph.connectNodes(center, topRight);
        graph.connectNodes(center, bottomLeft);
        graph.connectNodes(center, bottomRight);

        graph.connectNodes(topLeft, midLeft);
        graph.connectNodes(topLeft, midTop);

        graph.connectNodes(topRight, midRight);
        graph.connectNodes(topRight, midTop);

        graph.connectNodes(bottomRight, midRight);

        graph.connectNodes(bottomLeft, midLeft);

        graph.connectNodes(bottomLeft, bottomRight);
        return center;
    }
}