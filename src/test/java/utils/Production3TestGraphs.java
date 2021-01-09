package utils;

import model.TetrahedralGraph;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.TestGraphsCommons.prepareGraph;

public class Production3TestGraphs {
    public static TetrahedralGraph getProduction3CorrectLeftSide() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(5, new Pair<>(1.0f, -1.0f)); // bottomRight

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 4));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(2, 3));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(4, 5));
        links.add(new Pair<>(5, 1));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction3CorrectLeftSideInGreaterGraph() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(5, new Pair<>(1.0f, -1.0f)); // bottomRight
        // additional nodes
        nodesMap.put(6, new Pair<>(-1.0f, 2.0f));
        nodesMap.put(7, new Pair<>(0.0f, 2.0f));
        nodesMap.put(8, new Pair<>(1.0f, 2.0f));
        nodesMap.put(9, new Pair<>(2.0f, 2.0f));
        nodesMap.put(10, new Pair<>(2.0f, 1.0f));
        nodesMap.put(11, new Pair<>(2.0f, 0.0f));
        nodesMap.put(12, new Pair<>(2.0f, -1.0f));

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 4));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(2, 3));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(4, 5));
        links.add(new Pair<>(5, 1));
        // additional links
        links.add(new Pair<>(6, 3));
        links.add(new Pair<>(6, 7));
        links.add(new Pair<>(7, 3));
        links.add(new Pair<>(7, 4));
        links.add(new Pair<>(7, 8));
        links.add(new Pair<>(8, 4));
        links.add(new Pair<>(9, 4));
        links.add(new Pair<>(9, 10));
        links.add(new Pair<>(10, 4));
        links.add(new Pair<>(10, 11));
        links.add(new Pair<>(11, 4));
        links.add(new Pair<>(11, 5));
        links.add(new Pair<>(11, 12));
        links.add(new Pair<>(12, 5));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction3LeftSideWithMissedMiddleNode() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(5, new Pair<>(1.0f, -1.0f)); // bottomRight

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 4));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(1, 3));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(4, 5));
        links.add(new Pair<>(5, 1));

        return prepareGraph(nodesMap, links);
    }


    public static TetrahedralGraph getProduction3LeftSideWithAdditionalMiddleNode() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(5, new Pair<>(1.0f, -1.0f)); // bottomRight
        nodesMap.put(6, new Pair<>(0.0f, -1.0f)); // midBottom

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 4));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(2, 3));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(4, 5));
        links.add(new Pair<>(5, 6));
        links.add(new Pair<>(6, 1));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction3LeftSideWithMiddleNodeWithWrongCoordiantes() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.1f, 0.1f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(5, new Pair<>(1.0f, -1.0f)); // bottomRight

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 4));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(2, 3));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(4, 5));
        links.add(new Pair<>(5, 1));

        return prepareGraph(nodesMap, links);
    }
}
