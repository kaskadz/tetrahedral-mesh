package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import initialization.EntrySymbolInitializer;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.assertj.core.util.Lists;
import org.javatuples.Triplet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Production13TestGraphs.createCorrectGraph;

public class Production13Tests extends AbstractProductionTest {
    private final static Production prod = new Production13();
    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
    private final static boolean visualMode = true;

    private static GraphNode getUniqNodeByCords(Collection<GraphNode> nodes, Point2d cords) {
        List<GraphNode> filteredNodes = nodes
                .stream()
                .filter(node -> node.getCoordinates().equals(cords))
                .collect(Collectors.toList());
        assertEquals(1, filteredNodes.size());
        return filteredNodes.get(0);
    }

    private static void checkIfOneSiedOfInteriorNodesIsCOrrectlyConected(List<InteriorNode> side1InteriorNodes, GraphNode bottomExterior1, GraphNode bottomExterior2) {

        assertEquals(1, side1InteriorNodes.size());

        assertTrue(bottomExterior1.isDirectlyConnectedWith(bottomExterior2.getId()));
        assertTrue(bottomExterior2.isDirectlyConnectedWith(bottomExterior1.getId()));
    }

    private static void checkIfCorrectGraph(TetrahedralGraph graph, int topGraphLevel, Point2d cords1, Point2d cords2) {
        List<GraphNode> topExternalNodes = new ArrayList<>(graph.getGraphNodesByLevel(topGraphLevel));
        List<InteriorNode> topInteriorNodes = new ArrayList<>(graph.getInteriorNodesByLevel(topGraphLevel));

        assertEquals(1, topExternalNodes.size());
        assertEquals(2, topInteriorNodes.size());

        String topNodeId = topExternalNodes.get(0).getId();
        //check if top nodes are connected properly
        assertTrue(topInteriorNodes.stream().allMatch(node -> node.isDirectlyConnectedWith(topNodeId)));
        InteriorNode topInterior1 = topInteriorNodes.get(0);
        InteriorNode topInterior2 = topInteriorNodes.get(1);
        assertFalse(topInterior1.isDirectlyConnectedWith(topInterior2.getId()));


        int bottomLevel = topGraphLevel + 1;

        Collection<GraphNode> bottomExternalNodes = graph.getGraphNodesByLevel(bottomLevel);
        Collection<InteriorNode> bottomInteriorNodes = graph.getInteriorNodesByLevel(bottomLevel);

        assertEquals(2, bottomExternalNodes.size());
        assertEquals(2, bottomInteriorNodes.size());

        //checks coordinates of bottom exterior nodes
        GraphNode bottomExterior1 = getUniqNodeByCords(bottomExternalNodes, cords1);
        GraphNode bottomExterior2 = getUniqNodeByCords(bottomExternalNodes, cords2);

        // checks if bottom exterior nodes are connected properly
        assertTrue(bottomExterior1.isDirectlyConnectedWith(bottomExterior2.getId()));

        assertTrue(bottomInteriorNodes.stream().allMatch(node ->
                node.isDirectlyConnectedWith(bottomExterior1.getId()) && node.isDirectlyConnectedWith(bottomExterior2.getId())
        ));


        List<InteriorNode> side1InteriorNodes = topInterior1.getChildren().collect(Collectors.toList());
        assertEquals(1, side1InteriorNodes.size());

        List<InteriorNode> side2InteriorNodes = topInterior2.getChildren().collect(Collectors.toList());
        assertEquals(1, side2InteriorNodes.size());

        checkIfOneSiedOfInteriorNodesIsCOrrectlyConected(side1InteriorNodes, bottomExterior1, bottomExterior2);
        checkIfOneSiedOfInteriorNodesIsCOrrectlyConected(side2InteriorNodes, bottomExterior1, bottomExterior2);

        assertFalse(side1InteriorNodes.get(0).isDirectlyConnectedWith(side2InteriorNodes.get(0).getId()));

        List<InteriorNode> bottomInteriorsList = Stream
                .concat(side1InteriorNodes.stream(), side2InteriorNodes.stream())
                .collect(Collectors.toList());

        //check labels
        assertTrue(Stream
                .concat(bottomInteriorsList.stream(), topInteriorNodes.stream())
                .allMatch(node -> node.getSymbol().equals("I")));
        assertTrue(Stream
                .concat(topExternalNodes.stream(), bottomExternalNodes.stream())
                .allMatch(node -> node.getSymbol().equals("E")));
    }

    @Test
    public void shouldHaveProperNumber() {
        // Arrange

        // Act & Assert
        assertEquals(13, prod.getProductionId());
    }

    @Test
    public void shouldNotApplyOnInitialGraph() {
        // Arrange
        Production prod = new Production13();
        TetrahedralGraph graph = new EntrySymbolInitializer().initializeGraph();
        InteriorNode initialNode = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        // Act
        Executable subject = () -> prod.apply(graph, initialNode, Collections.emptyList());
        // Assert
        assertThrows(ProductionApplicationException.class, subject);
    }

    @Test
    public void shouldApplyOnPlainCorrectLeftSideWithFirstGraphNodeList() {
        // Arrange
        Point2d cords1 = new Point2d(0.0, 0.0);
        Point2d cords2 = new Point2d(1.0, 1.0);

        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(cords1, cords2);
        TetrahedralGraph graph = triplet.getValue0();
        Production prod = new Production13();

        // Act
        Executable subject = () -> prod.apply(graph, null, triplet.getValue1());

        visualizer.addStep(graph);


        // Assert
        assertDoesNotThrow(subject);
        checkIfCorrectGraph(graph, 0, cords1, cords2);

        visualizer.addStep(graph);


        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

    @Test
    public void shouldApplyOnPlainCorrectLeftSideWithSecondGraphNodeList() {
        // Arrange
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Point2d(0.0, 0.0), new Point2d(1.0, 1.0));
        TetrahedralGraph graph = triplet.getValue0();
        Production prod = new Production13();

        // Act
        Executable subject = () -> prod.apply(graph, null, triplet.getValue2());


        // Assert
        assertDoesNotThrow(subject);

    }

    //        should it work?
    @Test
    public void shouldNotApplyOnPlainCorrectLeftSideWithMixedGraphNodeList() {
        // Arrange
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Point2d(0.0, 0.0), new Point2d(1.0, 1.0));
        TetrahedralGraph graph = triplet.getValue0();
        Production prod = new Production13();


        List<GraphNode> newNodesList = Lists.newArrayList(triplet.getValue1().get(0), triplet.getValue2().get(1));
        // Act
        Executable subject = () -> prod.apply(graph, null, newNodesList);


        // Assert
        assertThrows(ProductionApplicationException.class, subject);

    }

    @Test
    public void shouldNotApplyOnPlaintLeftSideWithMissingNode() {
        // Arrange
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Point2d(0.0, 0.0), new Point2d(1.0, 1.0));
        TetrahedralGraph graph = triplet.getValue0();
        Production prod = new Production13();

        graph.removeGraphNode(triplet.getValue1().get(0));

        Executable subject = () -> prod.apply(graph, null, triplet.getValue2());

        visualizer.addStep(graph);


        // Assert
        assertThrows(ProductionApplicationException.class, subject);

        visualizer.addStep(graph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");

    }

    @Test
    public void shouldNotApplyOnPlaintLeftSideWithMissingEdge() {
        // Arrange
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Point2d(0.0, 0.0), new Point2d(1.0, 1.0));
        TetrahedralGraph graph = triplet.getValue0();
        Production prod = new Production13();

        GraphNode removedNode = triplet.getValue1().get(0);
        graph.removeGraphNode(removedNode);
        GraphNode bottomExterior1 = graph.insertGraphNode(1, "E", removedNode.getCoordinates());
        graph.connectNodes(triplet.getValue1().get(1), bottomExterior1);
        Executable subject = () -> prod.apply(graph, null, triplet.getValue2());

        visualizer.addStep(graph);

        // Assert
        assertThrows(ProductionApplicationException.class, subject);

        visualizer.addStep(graph);

        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");

    }

}
