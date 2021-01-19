package utils;

import common.CustomCollectors;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import production.Production;
import production.Production1;
import production.Production2;
import production.Production7;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Production8TestGraphs {
    private static final Production production1 = new Production1();
    private static final Production production2 = new Production2();
    private static final Production production7 = new Production7();


    public static Comparator<GraphNode> byX = Comparator.comparing(
            x -> x.getCoordinates().getX()
    );

    public static Comparator<GraphNode> byY = Comparator.comparing(
            x -> x.getCoordinates().getY()
    );

    public static TetrahedralGraph getProduction8ThreeLevelsGraph(MultiStepMultiLevelVisualizer visualizer) {
        TetrahedralGraph graph = new TetrahedralGraph();
        InteriorNode initialNode = graph.insertInteriorNode(0, "E");

        InteriorNode entryNode = graph
                .getInteriorNodes()
                .stream()
                .filter(x -> x.getSymbol().equals("E"))
                .collect(CustomCollectors.toSingle());

        production1.apply(graph, entryNode, Collections.emptyList());

        InteriorNode level1Interior = entryNode.getChildren().collect(CustomCollectors.toSingle());

        production2.apply(graph, level1Interior, Collections.emptyList());

        level1Interior.getChildren().forEach(level2Interior ->
                production2.apply(graph, level2Interior, Collections.emptyList()));

        List<GraphNode> sortedNodes = graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream()
                .filter(x -> (x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                        && (Math.abs(x.getCoordinates().getX()) == 0.5 || Math.abs(x.getCoordinates().getY()) == 0.5))
                .sorted(byX.thenComparing(byY))
                .collect(Collectors.toList());

        for (int i = 0; i < sortedNodes.size() - 2; i += 2) {
            List<GraphNode> nodes = sortedNodes
                    .get(i)
                    .getSiblings()
                    .filter(x -> x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                    .collect(Collectors.toList());

            nodes.add(sortedNodes.get(i));
            nodes.sort(byX.thenComparing(byY));

            production7.apply(graph, null, nodes);
        }

        return graph;
    }

    public static TetrahedralGraph getBasicFormExample() {
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

        GraphNode e5 = graph.insertGraphNode(2, "E", new Point2d(4, 4));
        graph.connectNodes(i4, e5);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e5, e4);

        return graph;
    }

    public static TetrahedralGraph getBasicExampleWithBadCoordinates() {
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

        GraphNode e2 = graph.insertGraphNode(2, "E", new Point2d(8, 4));
        graph.connectNodes(i2, e2);

        GraphNode e5 = graph.insertGraphNode(2, "E", new Point2d(4, 4));
        graph.connectNodes(i4, e5);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e5, e4);

        return graph;
    }

    public static TetrahedralGraph getBasicExampleWithBadLabel() {
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
        GraphNode i4 = graph.insertGraphNode(2, "E", new Point2d(0, 0));
        graph.connectNodes(rightI, i3);
        graph.connectNodes(rightI, i4);

        // Level 2 - E
        GraphNode e1 = graph.insertGraphNode(2, "E", new Point2d(0, 0));
        graph.connectNodes(i1, e1);
        graph.connectNodes(i3, e1);

        GraphNode e2 = graph.insertGraphNode(2, "E", new Point2d(8, 4));
        graph.connectNodes(i2, e2);

        GraphNode e5 = graph.insertGraphNode(2, "E", new Point2d(4, 4));
        graph.connectNodes(i4, e5);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e5, e4);

        return graph;
    }

}
