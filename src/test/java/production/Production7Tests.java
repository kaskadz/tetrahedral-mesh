package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import initialization.EntrySymbolInitializer;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.assertj.core.util.Lists;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Production7TestGraphs.createCorrectGraph;
import static utils.Production7TestGraphs.getBottomExteriorNodes;

public class Production7Tests extends AbstractProductionTest {
    private final static Production prod = new Production7();
    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
    private final static boolean visualMode = false;

    public static Comparator<GraphNode> byX = Comparator.comparing(
            x -> x.getCoordinates().getX()
    );

    public static Comparator<GraphNode> byY = Comparator.comparing(
            x -> x.getCoordinates().getY()
    );

    private static GraphNode getUniqNodeByCords(Collection<GraphNode> nodes, Point2d cords) {
        List<GraphNode> filteredNodes = nodes
                .stream()
                .filter(node -> node.getCoordinates().equals(cords))
                .collect(Collectors.toList());
        assertEquals(1, filteredNodes.size());
        return filteredNodes.get(0);
    }

    private static void checkIfOneSiedOfInteriorNodesIsCOrrectlyConected(List<InteriorNode> side1InteriorNodes, GraphNode bottomExterior1, GraphNode bottomExterior3) {

        List<InteriorNode> filteredNodes = side1InteriorNodes
                .stream()
                .filter(node -> node.isDirectlyConnectedWith(bottomExterior1.getId()) || node.isDirectlyConnectedWith(bottomExterior3.getId()))
                .collect(Collectors.toList());
        assertEquals(2, filteredNodes.size());
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

        assertEquals(3, bottomExternalNodes.size());
        assertEquals(4, bottomInteriorNodes.size());

        //checks coordinates of bottom exterior nodes
        GraphNode bottomExterior1 = getUniqNodeByCords(bottomExternalNodes, cords1);
        GraphNode bottomExterior2 = getUniqNodeByCords(bottomExternalNodes, Point2d.center(cords1, cords2).get());
        GraphNode bottomExterior3 = getUniqNodeByCords(bottomExternalNodes, cords2);

        // checks if bottom exterior nodes are connected properly
        assertTrue(bottomExterior2.isDirectlyConnectedWith(bottomExterior1.getId()));
        assertTrue(bottomExterior2.isDirectlyConnectedWith(bottomExterior3.getId()));
        assertFalse(bottomExterior1.isDirectlyConnectedWith(bottomExterior3.getId()));

        assertTrue(bottomInteriorNodes.stream().allMatch(node -> node.isDirectlyConnectedWith(bottomExterior2.getId())));


        List<InteriorNode> side1InteriorNodes = topInterior1.getChildren().collect(Collectors.toList());
        assertEquals(2, side1InteriorNodes.size());

        List<InteriorNode> side2InteriorNodes = topInterior2.getChildren().collect(Collectors.toList());
        assertEquals(2, side2InteriorNodes.size());

        checkIfOneSiedOfInteriorNodesIsCOrrectlyConected(side1InteriorNodes, bottomExterior1, bottomExterior3);
        checkIfOneSiedOfInteriorNodesIsCOrrectlyConected(side2InteriorNodes, bottomExterior1, bottomExterior3);

        List<InteriorNode> bottomInteriorsList = Stream
                .concat(side1InteriorNodes.stream(), side2InteriorNodes.stream())
                .collect(Collectors.toList());

        // checks if interior nodes aren't connected
        assertTrue(bottomInteriorsList
                .stream()
                .allMatch(node -> bottomInteriorsList
                        .stream()
                        .noneMatch(interiorNode -> node.isDirectlyConnectedWith(interiorNode.getId()))));

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
        assertEquals(7, prod.getProductionId());
    }

    @Test
    public void shouldNotApplyOnInitialGraph() {
        // Arrange
        Production prod = new Production7();
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
        Production prod = new Production7();

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
        Production prod = new Production7();

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
        Production prod = new Production7();


        List<GraphNode> newNodesList = Lists.newArrayList(triplet.getValue2().get(0), triplet.getValue1().get(1), triplet.getValue2().get(2));
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
        Production prod = new Production7();

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
        Production prod = new Production7();

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

    @Test
    public void shouldNotApplyOnPlainLeftSideWithWrongCoords() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();

        Pair<Map<String, String>, Map<String, String>> sides = createCorrectGraph(graph, new Point2d(0.0, 0.0), new Point2d(1.0, 1.0));

        Map<String, String> side = sides.getValue0();

        graph.removeGraphNode(side.get("bottomExterior3"));
        GraphNode bottomExterior3 = graph.insertGraphNode(1, "E", new Point2d(0.7, 0.8));

        GraphNode bottomExterior2 = graph.getGraphNode(side.get("bottomExterior2"));
        InteriorNode bottomInterior2 = graph.getInteriorNode(side.get("bottomInterior2"));
        graph.connectNodes(bottomExterior3, bottomExterior2);
        graph.connectNodes(bottomInterior2, bottomExterior3);
        Production prod = new Production7();

        // Act
        Executable subject = () -> prod.apply(graph, null, getBottomExteriorNodes(graph, sides.getValue1()));

        visualizer.addStep(graph);

        // Assert
        assertThrows(ProductionApplicationException.class, subject);

        visualizer.addStep(graph);


        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

    @Test
    public void shouldApplyOnComplexLeftSide() {
        // Arrange
        TetrahedralGraph graph = new TetrahedralGraph();

        InteriorNode entryNode = graph.insertInteriorNode(0, "E");
        //this should be done by hand as it assumes productions 1 and 2 are correct but this way is easier
        Production prod1 = new Production1();
        prod1.apply(graph, entryNode, Collections.emptyList());

        InteriorNode level1Interior = entryNode.getChildren().collect(CustomCollectors.toSingle());

        Production prod2 = new Production2();


        prod2.apply(graph, level1Interior, Collections.emptyList());

        level1Interior.getChildren().forEach(level2Interior ->
                prod2.apply(graph, level2Interior, Collections.emptyList()));
        List<GraphNode> sortedNodes = graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream()
                .filter(x -> (x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                        && (Math.abs(x.getCoordinates().getX()) == 0.5 || Math.abs(x.getCoordinates().getY()) == 0.5))
                .sorted(byX.thenComparing(byY))
                .collect(Collectors.toList());


        List<GraphNode> nodes = sortedNodes
                .get(0)
                .getSiblings()
                .filter(x -> x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                .collect(Collectors.toList());

        nodes.add(sortedNodes.get(0));
        nodes.sort(byX.thenComparing(byY));
        Production prod = new Production7();

        // Act
        Executable subject = () -> prod.apply(graph, null, nodes);

        visualizer.addStep(graph);

        // Assert
        assertDoesNotThrow(subject);

        visualizer.addStep(graph);


        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }


}
