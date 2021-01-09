package utils;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Production6TestGraphs {
    public static TetrahedralGraph getProduction6CorrectLeftSide() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(6, new Pair<>(1.0f, 0.0f)); // midRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
        nodesMap.put(8, new Pair<>(0.0f, -1.0f)); // midBottom

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(5, 6));
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(7, 6));
        links.add(new Pair<>(7, 8));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 8));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction6CorrectLeftSideWithAdditionalNodesOnRight() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(6, new Pair<>(1.0f, 0.0f)); // midRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
        nodesMap.put(8, new Pair<>(0.0f, -1.0f)); // midBottom
        // additional nodes
        nodesMap.put(9, new Pair<>(2.0f, 1.0f)); // rightTop
        nodesMap.put(10, new Pair<>(2.0f, 0.0f)); // rightCenter
        nodesMap.put(11, new Pair<>(2.0f, -1.0f)); // rightBottom

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(5, 6));
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(7, 6));
        links.add(new Pair<>(7, 8));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 8));
        // additional links
        links.add(new Pair<>(5, 9));
        links.add(new Pair<>(6, 10));
        links.add(new Pair<>(7, 11));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction6CorrectLeftSideInGreaterGraph() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        // nodesMap.put(0, new Pair<>(0.0f, 0.0f)); // center
        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(6, new Pair<>(1.0f, 0.0f)); // midRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
        nodesMap.put(8, new Pair<>(0.0f, -1.0f)); // midBottom
        // additional nodes
        nodesMap.put(9, new Pair<>(-1.0f, 2.0f));
        nodesMap.put(10, new Pair<>(0.0f, 2.0f));
        nodesMap.put(11, new Pair<>(1.0f, 2.0f));
        nodesMap.put(12, new Pair<>(2.0f, 2.0f));
        nodesMap.put(13, new Pair<>(2.0f, 1.0f));
        nodesMap.put(14, new Pair<>(2.0f, 0.0f));
        nodesMap.put(15, new Pair<>(2.0f, -1.0f));

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(5, 6));
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(7, 6));
        links.add(new Pair<>(7, 8));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 8));
        //additional links
        links.add(new Pair<>(3, 9));
        links.add(new Pair<>(9, 10));
        links.add(new Pair<>(10, 11));
        links.add(new Pair<>(10, 3));
        links.add(new Pair<>(10, 5));
        links.add(new Pair<>(11, 5));
        links.add(new Pair<>(11, 12));
        links.add(new Pair<>(12, 13));
        links.add(new Pair<>(12, 5));
        links.add(new Pair<>(13, 5));
        links.add(new Pair<>(13, 14));
        links.add(new Pair<>(14, 15));
        links.add(new Pair<>(14, 5));
        links.add(new Pair<>(14, 7));
        links.add(new Pair<>(15, 7));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction6LeftSideWithBrokenLink() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        // nodesMap.put(0, new Pair<>(0.0f, 0.0f)); // center
        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(6, new Pair<>(1.0f, 0.0f)); // midRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
        nodesMap.put(8, new Pair<>(0.0f, -1.0f)); // midBottom
        // break node
        nodesMap.put(9, new Pair<>(0.5f, 1.0f)); // between

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(5, 6));
        links.add(new Pair<>(7, 6));
        links.add(new Pair<>(7, 8));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 8));
        // break link
        links.add(new Pair<>(4, 9));
        links.add(new Pair<>(9, 5));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction6LeftSideWithMissingNode() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        // nodesMap.put(0, new Pair<>(0.0f, 0.0f)); // center
        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        // nodesMap.put(6, new Pair<>(1.0f, 0.0f)); // midRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
        nodesMap.put(8, new Pair<>(0.0f, -1.0f)); // midBottom

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(5, 7));
        links.add(new Pair<>(7, 8));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 8));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction6LeftSideWithIncorrectCoordinates() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        // nodesMap.put(0, new Pair<>(0.0f, 0.0f)); // center
        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(10.0f, 10.0f)); // topRight, wrong coords
        nodesMap.put(6, new Pair<>(1.0f, 0.0f)); // midRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
        nodesMap.put(8, new Pair<>(0.0f, -1.0f)); // midBottom

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(5, 6));
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(7, 6));
        links.add(new Pair<>(7, 8));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 8));

        return prepareGraph(nodesMap, links);
    }

    private static TetrahedralGraph prepareGraph(Map<Integer, Pair<Float, Float>> nodesCoords, List<Pair<Integer, Integer>> links) {
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
