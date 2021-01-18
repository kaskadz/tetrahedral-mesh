package utils;

import model.TetrahedralGraph;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.TestGraphsCommons.prepareGraph;

public class Production4TestGraphs {
    public static TetrahedralGraph getProduction4CorrectLeftSide() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(7, 1));
        links.add(new Pair<>(5, 7));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction4CorrectLeftSideWithAdditionalNodesOnRight() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
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
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 7));
        links.add(new Pair<>(5, 7));
        // additional links
        links.add(new Pair<>(5, 9));
        links.add(new Pair<>(7, 11));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction4CorrectLeftSideInGreaterGraph() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        // nodesMap.put(0, new Pair<>(0.0f, 0.0f)); // center
        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
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
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 7));
        links.add(new Pair<>(5, 7));
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

    public static TetrahedralGraph getProduction4LeftSideWithBrokenLink() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        // nodesMap.put(0, new Pair<>(0.0f, 0.0f)); // center
        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight
        // break node
        nodesMap.put(9, new Pair<>(0.5f, 1.0f)); // between

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 7));
        links.add(new Pair<>(5, 7));
        // break link
        links.add(new Pair<>(4, 9));
        links.add(new Pair<>(9, 5));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction4LeftSideWithMissingNode() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        // nodesMap.put(0, new Pair<>(0.0f, 0.0f)); // center
        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(5, new Pair<>(1.0f, 1.0f)); // topRight
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(5, 7));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 7));
        links.add(new Pair<>(3, 5));

        return prepareGraph(nodesMap, links);
    }

    public static TetrahedralGraph getProduction4LeftSideWithIncorrectCoordinates() {
        Map<Integer, Pair<Float, Float>> nodesMap = new HashMap<>();

        // nodesMap.put(0, new Pair<>(0.0f, 0.0f)); // center
        nodesMap.put(1, new Pair<>(-1.0f, -1.0f)); // bottomLeft
        nodesMap.put(2, new Pair<>(-1.0f, 0.0f)); // midLeft
        nodesMap.put(3, new Pair<>(-1.0f, 1.0f)); // topLeft
        nodesMap.put(4, new Pair<>(0.0f, 1.0f)); // midTop
        nodesMap.put(5, new Pair<>(2.0f, 2.0f)); // topRight, wrong coords
        nodesMap.put(7, new Pair<>(1.0f, -1.0f)); // bottomRight

        List<Pair<Integer, Integer>> links = new ArrayList<>();

        links.add(new Pair<>(0, 3));
        links.add(new Pair<>(0, 5));
        links.add(new Pair<>(0, 1));
        links.add(new Pair<>(0, 7));
        links.add(new Pair<>(3, 2));
        links.add(new Pair<>(3, 4));
        links.add(new Pair<>(5, 4));
        links.add(new Pair<>(1, 2));
        links.add(new Pair<>(1, 7));
        links.add(new Pair<>(5, 7));

        return prepareGraph(nodesMap, links);
    }

}
