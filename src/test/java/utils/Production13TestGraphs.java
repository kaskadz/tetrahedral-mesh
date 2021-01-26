package utils;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.assertj.core.util.Lists;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Production13TestGraphs {

    private static Map<String, String> prepareSideNodes(
            TetrahedralGraph graph, GraphNode topNode, Point2d cords1, Point2d cords2, int graphLevel) {
        Map<String, String> nodesMap = new HashMap<>();

        // Top level
        InteriorNode topInteriorNode = graph.insertInteriorNode(graphLevel, "I");
        graph.connectNodes(topInteriorNode, topNode);
        nodesMap.put("topInteriorNode", topInteriorNode.getId());

        //Bottom level
        int bottomLevel = graphLevel + 1;
        // interior
        InteriorNode bottomInterior1 = graph.insertInteriorNode(bottomLevel, "I");
        graph.connectNodes(topInteriorNode, bottomInterior1);
        nodesMap.put("bottomInterior1", bottomInterior1.getId());

        //exterior
        // ext1
        GraphNode bottomExterior1 = graph.insertGraphNode(1, "E", cords1);

        // ext2
        GraphNode bottomExterior2 = graph.insertGraphNode(1, "E", cords2);

        // make connection between exteriors and interior bottom level

        graph.connectNodes(bottomInterior1, bottomExterior1);
        graph.connectNodes(bottomInterior1, bottomExterior2);

        graph.connectNodes(bottomExterior1, bottomExterior2);

        nodesMap.put("bottomExterior1", bottomExterior1.getId());
        nodesMap.put("bottomExterior2", bottomExterior2.getId());

        return nodesMap;
    }

    public static List<GraphNode> getBottomExteriorNodes(TetrahedralGraph graph, Map<String, String> nodesMap) {

        return Lists.newArrayList('1', '2')
                .stream()
                .map(number -> graph.getGraphNode(nodesMap.get("bottomExterior" + number)))
                .collect(Collectors.toList());
    }

    public static Pair<Map<String, String>, Map<String, String>> createCorrectGraph(
            TetrahedralGraph graph, Point2d cords1, Point2d cords2) {
        int graphLevel = 0;

        GraphNode initialNode = graph
                .insertGraphNode(graphLevel, "E", new Point2d(cords1.getX(), cords2.getY()));
        Map<String, String> leftSide = prepareSideNodes(graph, initialNode, cords1, cords2, graphLevel);
        Map<String, String> rightSide = prepareSideNodes(graph, initialNode, cords1, cords2, graphLevel);

        return new Pair<>(leftSide, rightSide);
    }

    public static Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> createCorrectGraph(
            Point2d cords1, Point2d cords2) {
        TetrahedralGraph graph = new TetrahedralGraph();
        Pair<Map<String, String>, Map<String, String>> sides = createCorrectGraph(graph, cords1, cords2);
        List<GraphNode> leftSide = getBottomExteriorNodes(graph, sides.getValue0());
        List<GraphNode> rightSide = getBottomExteriorNodes(graph, sides.getValue1());

        return new Triplet<>(graph, leftSide, rightSide);
    }


}
