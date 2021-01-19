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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Production9Tests extends AbstractProductionTest {

    private final MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
    private final static boolean visualMode = false;

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
        List<GraphNode> nodes = Lists.newArrayList(initialNode, e1, e2, e3, e4);

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
        List<GraphNode> nodes = Lists.newArrayList(initialNode, e1, e2, e3, e4);

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
        List<GraphNode> nodes = Lists.newArrayList(initialNode, e1, e2, e3, e4);

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
        List<GraphNode> nodes = Lists.newArrayList(initialNode, e1, e2, e3, e4);

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
        List<GraphNode> nodes = Lists.newArrayList(initialNode, e1, e2, e3, e4);

        // Act
        Executable subject = () -> prod.apply(graph, null, nodes);

        // Assert
        assertThrows(ProductionApplicationException.class, subject);
    }

}
