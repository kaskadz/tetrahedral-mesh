package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import initialization.EntrySymbolInitializer;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class Production9Tests extends AbstractProductionTest {

    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
    private final static boolean visualMode = false;

    public static Comparator<GraphNode> byX = Comparator.comparing(
            x -> x.getCoordinates().getX()
    );

    public static Comparator<GraphNode> byY = Comparator.comparing(
            x -> x.getCoordinates().getY()
    );

    public static List<GraphNode> getNodesForProd7(int startingNode, List<GraphNode> sortedNodes) {
        List<GraphNode> nodes = sortedNodes
                .get(startingNode)
                .getSiblings()
                .filter(x -> x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                .collect(Collectors.toList());

        nodes.add(sortedNodes.get(startingNode));
        nodes.sort(byX.thenComparing(byY));

        return nodes;
    }

    @Test
    public void shouldApplyNestedInComplexGraph() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();
        List<GraphNode> graphNodes = Collections.emptyList();
        Production prod1 = new Production1();
        Production prod2 = new Production2();
        Production prod7 = new Production7();
        Production prod = new Production9();

        // Level 0
        InteriorNode initialNode = graph.insertInteriorNode(0, "E");
        visualizer.addStep(graph);

        // Level 1
        prod1.apply(graph, initialNode, graphNodes);
        visualizer.addStep(graph);

        // Level 2
        InteriorNode interiorNode1 = initialNode.getChildren().collect(CustomCollectors.toSingle());
        prod2.apply(graph, interiorNode1, graphNodes);
        visualizer.addStep(graph);

        // Level 3
        interiorNode1.getChildren().forEach(nodeLevel2 -> {
                    prod2.apply(graph, nodeLevel2, graphNodes);
                }
        );
        visualizer.addStep(graph);

        //Merging on level 3
        List<GraphNode> sortedNodes = graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream()
                .filter(x -> (x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                        && (Math.abs(x.getCoordinates().getX()) == 0.5 || Math.abs(x.getCoordinates().getY()) == 0.5))
                .sorted(byX.thenComparing(byY))
                .collect(Collectors.toList());


        for (int i = 0; i < 3; i++) {
            List<GraphNode> nodes = getNodesForProd7(i * 2, sortedNodes);
            prod7.apply(graph, null, nodes);

        }
        visualizer.addStep(graph);

        // Missing prod 12
        // Now we should apply prod 12 to merge nodes on segment (0.0, 0.0), (1.0, 0.0)
        // Will do it "manually"
        List<GraphNode> toBeMerged = graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream()
                .filter(
                        x -> x.getCoordinates().getX() == 1.0 && x.getCoordinates().getY() == 0.0
                )
                .collect(Collectors.toList());

        graph.mergeNodes(toBeMerged.get(0), toBeMerged.get(1));
        visualizer.addStep(graph);

        List<GraphNode> forProd9 = graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream()
                .filter(
                        x -> x.getCoordinates().getX() >= 0.0 && x.getCoordinates().getY() == 0.0
                )
                .collect(Collectors.toList());

//        for (GraphNode node : forProd9) {
//            System.out.println("" + node.getCoordinates().getX() + " " + node.getCoordinates().getY());
//        }

        // Act
        Executable subject = () -> prod.apply(graph, null, forProd9);

        // Assert
        assertDoesNotThrow(subject);
        visualizer.addStep(graph);

        // Visualize
        if (visualMode) visualizer.displayAll("ComplexGraph");
    }

    @Test
    public void shouldHaveProperNumber() {
        // Arrange
        Production prod = new Production9();

        // Act & Assert
        assertEquals(9, prod.getProductionId());
    }

    @Test
    public void shouldNotApplyOnInitialGraph() {
        // Arrange
        Production prod = new Production9();
        TetrahedralGraph graph = new EntrySymbolInitializer().initializeGraph();
        InteriorNode initialNode = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // Act
        Executable subject = () -> prod.apply(graph, initialNode, Collections.emptyList());

        // Assert
        assertThrows(ProductionApplicationException.class, subject);
    }

    @Test
    public void shouldApplyOnGraphIsomorphicToLeftSide() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();

        // Level 0
        GraphNode initialNode = graph.insertGraphNode(1, "e", new Point2d(0, 0));
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

        GraphNode e2 = graph.insertGraphNode(2, "E", new Point2d(1, 1));
        graph.connectNodes(i2, e2);
        graph.connectNodes(i4, e2);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(0.5, 0.5));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(0.5, 0.5));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e2, e4);

        visualizer.addStep(graph);

        Production prod = new Production9();
        List<GraphNode> nodes = Lists.newArrayList(e1, e2, e3, e4);

        // Act
        Executable subject = () -> prod.apply(graph, null, nodes);

        // Assert
        assertDoesNotThrow(subject);
        visualizer.addStep(graph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

    @Test
    public void shouldNotApplyOnGraphIsomorphicToLeftSideWithWrongCoords() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();

        // Level 0
        GraphNode initialNode = graph.insertGraphNode(1, "e", new Point2d(0, 0));
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

        GraphNode e2 = graph.insertGraphNode(2, "E", new Point2d(1, 1));
        graph.connectNodes(i2, e2);
        graph.connectNodes(i4, e2);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(0.7, 0.7));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(0.7, 0.7));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e2, e4);


        Production prod = new Production9();
        List<GraphNode> nodes = Lists.newArrayList(e1, e2, e3, e4);

        // Act
        Executable subject = () -> prod.apply(graph, null, nodes);

        // Assert
        assertThrows(ProductionApplicationException.class, subject);
    }

    @Test
    public void shouldNotApplyOnGraphIsomorphicToLeftSideWithWrongCoords2() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();

        // Level 0
        GraphNode initialNode = graph.insertGraphNode(1, "e", new Point2d(0, 0));
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

        GraphNode e2 = graph.insertGraphNode(2, "E", new Point2d(1, 1));
        graph.connectNodes(i2, e2);
        graph.connectNodes(i4, e2);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(0.5, 0.5));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(0.45, 0.5));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e2, e4);


        Production prod = new Production9();
        List<GraphNode> nodes = Lists.newArrayList(e1, e2, e3, e4);

        // Act
        Executable subject = () -> prod.apply(graph, null, nodes);

        // Assert
        assertThrows(ProductionApplicationException.class, subject);
    }

    @Test
    public void shouldNotApplyOnGraphIsomorphicToLeftSideWithoutNode() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();

        // Level 0
        GraphNode initialNode = graph.insertGraphNode(1, "e", new Point2d(0, 0));
        InteriorNode leftI = graph.insertInteriorNode(1, "i");
        graph.connectNodes(leftI, initialNode);
        InteriorNode rightI = graph.insertInteriorNode(1, "i");
        graph.connectNodes(rightI, initialNode);

        // Level 2 - I
        InteriorNode i1 = graph.insertInteriorNode(2, "I");
        InteriorNode i2 = graph.insertInteriorNode(2, "I");
        graph.connectNodes(leftI, i1);
        graph.connectNodes(leftI, i2);

        InteriorNode i4 = graph.insertInteriorNode(2, "I");
        graph.connectNodes(rightI, i4);

        // Level 2 - E
        GraphNode e1 = graph.insertGraphNode(2, "E", new Point2d(0, 0));
        graph.connectNodes(i1, e1);

        GraphNode e2 = graph.insertGraphNode(2, "E", new Point2d(1, 1));
        graph.connectNodes(i2, e2);
        graph.connectNodes(i4, e2);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(0.5, 0.5));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(0.5, 0.5));
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e2, e4);


        Production prod = new Production9();
        List<GraphNode> nodes = Lists.newArrayList(e1, e2, e3, e4);

        // Act
        Executable subject = () -> prod.apply(graph, null, nodes);

        // Assert
        assertThrows(ProductionApplicationException.class, subject);
    }

    @Test
    public void shouldNotApplyOnGraphIsomorphicToLeftSideWithoutEdge() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();

        // Level 0
        GraphNode initialNode = graph.insertGraphNode(1, "e", new Point2d(0, 0));
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

        GraphNode e2 = graph.insertGraphNode(2, "E", new Point2d(1, 1));
        graph.connectNodes(i2, e2);
        graph.connectNodes(i4, e2);

        GraphNode e3 = graph.insertGraphNode(2, "E", new Point2d(0.5, 0.5));
        graph.connectNodes(i1, e3);
        //  Missing edge
        //  graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(2, "E", new Point2d(0.45, 0.5));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e2, e4);


        Production prod = new Production9();
        List<GraphNode> nodes = Lists.newArrayList(e1, e2, e3, e4);

        // Act
        Executable subject = () -> prod.apply(graph, null, nodes);

        // Assert
        assertThrows(ProductionApplicationException.class, subject);
    }

}
