package utils;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.assertj.core.util.Lists;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;

public class Production7Utils {

    private static List<GraphNode> prepareSideNodes(TetrahedralGraph graph, GraphNode topNode, Pair<Double, Double> cords1, Pair<Double, Double> cords2, int graphLevel) {

        // Top level
        InteriorNode topInteriorNode = graph.insertInteriorNode(graphLevel, "I");
        graph.connectNodes(topInteriorNode, topNode);

        //Bottom level
        int bottomLevel = graphLevel + 1;
        // interior
        InteriorNode bottomInterior1 = graph.insertInteriorNode(bottomLevel, "I");
        InteriorNode bottomInterior2 = graph.insertInteriorNode(bottomLevel, "I");
        graph.connectNodes(topInteriorNode, bottomInterior1);
        graph.connectNodes(topInteriorNode, bottomInterior2);

        //exterior
        GraphNode bottomExterior1 = graph.insertGraphNode(1, "E", new Point2d(cords1.getValue0(), cords1.getValue1()));
        graph.connectNodes(bottomInterior1, bottomExterior1);


        GraphNode bottomExterior2 = graph.insertGraphNode(1, "E", new Point2d((cords1.getValue0() + cords2.getValue0()) / 2, (cords1.getValue1() + cords2.getValue1()) / 2));
        graph.connectNodes(bottomInterior1, bottomExterior2);
        graph.connectNodes(bottomExterior1, bottomExterior2);
        graph.connectNodes(bottomInterior2, bottomExterior2);
        GraphNode bottomExterior3 = graph.insertGraphNode(1, "E", new Point2d(cords2.getValue0(), cords2.getValue1()));
        graph.connectNodes(bottomExterior3, bottomExterior2);
        graph.connectNodes(bottomInterior2, bottomExterior3);

        return Lists.newArrayList(bottomExterior1, bottomExterior2, bottomExterior3);
    }

    public static Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> createCorrectGraph(Pair<Double, Double> cords1, Pair<Double, Double> cords2) {
        //        todo it might be passed in params
        int graphLevel = 0;

        TetrahedralGraph graph = new TetrahedralGraph();
        GraphNode initialNode = graph.insertGraphNode(graphLevel, "E", new Point2d(cords1.getValue0(), cords2.getValue1()));
        List<GraphNode> leftSide = prepareSideNodes(graph, initialNode, cords1, cords2, graphLevel);
        List<GraphNode> rightSide = prepareSideNodes(graph, initialNode, cords1, cords2, graphLevel);

        return new Triplet<>(graph, leftSide,rightSide);
    }

    public static Pair<List<GraphNode>, List<GraphNode>> createCorrectGraph(TetrahedralGraph graph, Pair<Double, Double> cords1, Pair<Double, Double> cords2) {
        //        todo it might be passed in params
        int graphLevel = 0;

        GraphNode initialNode = graph.insertGraphNode(graphLevel, "E", new Point2d(cords1.getValue0(), cords2.getValue1()));
        List<GraphNode> leftSide = prepareSideNodes(graph, initialNode, cords1, cords2, graphLevel);
        List<GraphNode> rightSide = prepareSideNodes(graph, initialNode, cords1, cords2, graphLevel);

        return new Pair<>(leftSide,rightSide);
    }
}
