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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Production7Utils.createCorrectGraph;

public class Production7Tests extends AbstractProductionTest {
    private final static Production prod = new Production7();
    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
    private final static boolean visualMode = true;

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
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));
        TetrahedralGraph graph = triplet.getValue0();
        Production prod = new Production7();

        // Act
        Executable subject = () -> prod.apply(graph, null, triplet.getValue1());

        visualizer.addStep(graph);


        // Assert
        assertDoesNotThrow(subject);

        visualizer.addStep(graph);


        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

    @Test
    public void shouldApplyOnPlainCorrectLeftSideWithSecondGraphNodeList() {
        // Arrange
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));
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
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));
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
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));
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
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));
        TetrahedralGraph graph = triplet.getValue0();
        Production prod = new Production7();

        GraphNode removedNode = triplet.getValue1().get(0);
        graph.removeGraphNode(removedNode);
        GraphNode bottomExterior1 = graph.insertGraphNode(1, "E",removedNode.getCoordinates());
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
        Triplet<TetrahedralGraph, List<GraphNode>, List<GraphNode>> triplet = createCorrectGraph(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));
        TetrahedralGraph graph = triplet.getValue0();
        Production prod = new Production7();

        // Act
        Executable subject = () -> prod.apply(graph, null, triplet.getValue1());

        visualizer.addStep(graph);


        // Assert
        assertDoesNotThrow(subject);

        visualizer.addStep(graph);


        // visualize
        if (visualMode) visualizer.displayAll("CorrectLeftSide");
    }

}
