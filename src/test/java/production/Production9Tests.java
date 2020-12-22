package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import initialization.EntrySymbolInitializer;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Production9Tests extends AbstractProductionTest {
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
        GraphNode e1 = graph.insertGraphNode(3, "E", new Point2d(0, 0));
        graph.connectNodes(i1, e1);
        graph.connectNodes(i3, e1);

        GraphNode e2 = graph.insertGraphNode(3, "E", new Point2d(4, 4));
        graph.connectNodes(i2, e2);
        graph.connectNodes(i4, e2);

        GraphNode e3 = graph.insertGraphNode(3, "E", new Point2d(2, 2));
        graph.connectNodes(i1, e3);
        graph.connectNodes(i2, e3);
        graph.connectNodes(e1, e3);
        graph.connectNodes(e2, e3);

        GraphNode e4 = graph.insertGraphNode(3, "E", new Point2d(2, 2));
        graph.connectNodes(i3, e4);
        graph.connectNodes(i4, e4);
        graph.connectNodes(e1, e4);
        graph.connectNodes(e2, e4);

        Production prod = new Production9();

        // Act
        ArrayList<GraphNode> nodes = new ArrayList<>();
        nodes.add(initialNode);
        Executable subject = () -> prod.apply(graph, null, nodes);

        // Assert
        assertDoesNotThrow(subject);
    }
}
