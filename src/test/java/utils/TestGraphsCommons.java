package utils;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGraphsCommons {
    static TetrahedralGraph prepareGraph(Map<Integer, Pair<Float, Float>> nodesCoords, List<Pair<Integer, Integer>> links) {
        int graphLevel = 0;

        TetrahedralGraph graph = new TetrahedralGraph();

        InteriorNode center = graph.insertInteriorNode(graphLevel, "I");
        Map<Integer, GraphNode> nodesMap = new HashMap<>();

        nodesCoords.forEach((key, value) -> {
            GraphNode newNode = graph.insertGraphNode(graphLevel, "E", new Point2d(value.getValue0(), value.getValue1()));
            nodesMap.put(key, newNode);
        });

        // connect with center
        links.stream().filter(link -> link.getValue0() == 0)
                .forEach(link -> graph.connectNodes(center, nodesMap.get(link.getValue1())));

        // connect with other nodes
        links.stream().filter(link -> link.getValue0() != 0)
                .forEach(link -> graph.connectNodes(nodesMap.get(link.getValue0()), nodesMap.get(link.getValue1())));

        return graph;
    }
}
