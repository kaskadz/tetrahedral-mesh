package utils;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

public class Production12TestGraphs {

    public static TetrahedralGraph getBasicFormExample() {
        TetrahedralGraph graph = new TetrahedralGraph();

        // Level 0
        GraphNode initialNode = graph.insertGraphNode(0, "e", new Point2d(0, 0));
        InteriorNode leftI = graph.insertInteriorNode(0, "i");
        graph.connectNodes(leftI, initialNode);
        InteriorNode rightI = graph.insertInteriorNode(0, "i");
        graph.connectNodes(rightI, initialNode);

        // Level 2 - I
        InteriorNode i1 = graph.insertInteriorNode(1, "I");
        InteriorNode i2 = graph.insertInteriorNode(1, "I");
        graph.connectNodes(leftI, i1);
        graph.connectNodes(leftI, i2);

        InteriorNode i3 = graph.insertInteriorNode(1, "I");
        graph.connectNodes(rightI, i3);

        // Level 2 - E
        GraphNode e1 = graph.insertGraphNode(1, "E", new Point2d(0, 0));
        graph.connectNodes(i1, e1);
        graph.connectNodes(i3, e1);

        GraphNode e2 = graph.insertGraphNode(1, "E", new Point2d(4, 4));
        graph.connectNodes(i2, e2);

        GraphNode e5 = graph.insertGraphNode(1, "E", new Point2d(4, 4));
        graph.connectNodes(i3, e5);
        graph.connectNodes(e1, e5);

        GraphNode e3 = graph.insertGraphNode(1, "E", new Point2d(2, 2));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        return graph;
    }
}
