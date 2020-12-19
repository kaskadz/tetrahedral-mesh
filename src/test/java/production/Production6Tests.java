package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class Production6Tests extends AbstractProductionTest {
    @Test
    public void shouldHaveProperNumber() {
        Production sut = new Production6();

        assertEquals(6, sut.getProductionId());
    }

    @Test
    public void shouldApplyOnCorrectLeftSide() {
        Production sut = new Production6();

        TetrahedralGraph correctGraph = getGraph(false, false);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        Executable production = () -> sut.apply(correctGraph, interiorNode, Collections.emptyList());

        assertDoesNotThrow(production);
    }

    @Test
    public void shouldApplyOnCorrectLeftSideWithAdditionalNodes() {
        Production sut = new Production6();

        TetrahedralGraph correctGraph = getGraph(true, false);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        System.out.println(interiorNode);

        Executable production = () -> sut.apply(correctGraph, interiorNode, Collections.emptyList());

        assertDoesNotThrow(production);
    }


    @Test
    public void shouldNotApplyOnIncorrectLeftSide() {
        Production sut = new Production6();

        TetrahedralGraph correctGraph = getGraph(false, true);
        InteriorNode interiorNode = correctGraph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());

        System.out.println(interiorNode);

        Executable production = () -> sut.apply(correctGraph, interiorNode, Collections.emptyList());

        assertThrows(ProductionApplicationException.class, production);
    }

    private TetrahedralGraph getGraph(boolean additionalNodes, boolean breakGraph) {
        int graphLevel = 0;

        TetrahedralGraph graph = new TetrahedralGraph();

        InteriorNode center = graph.insertInteriorNode(graphLevel, "I");

        GraphNode bottomLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, -1));
        GraphNode midLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, 0));
        GraphNode topLeft = graph.insertGraphNode(graphLevel, "E", new Point2d(-1, 1));
        GraphNode midTop = graph.insertGraphNode(graphLevel, "E", new Point2d(0, 1));
        GraphNode topRight = graph.insertGraphNode(graphLevel, "E", new Point2d(1, 1));
        GraphNode midRight = graph.insertGraphNode(graphLevel, "E", new Point2d(1, 0));
        GraphNode bottomRight = graph.insertGraphNode(graphLevel, "E", new Point2d(1, -1));
        GraphNode midBottom = graph.insertGraphNode(graphLevel, "E", new Point2d(0, -1));

        graph.connectNodes(center, topLeft);
        graph.connectNodes(center, topRight);
        graph.connectNodes(center, bottomLeft);
        graph.connectNodes(center, bottomRight);

        graph.connectNodes(topLeft, midLeft);
        graph.connectNodes(topLeft, midTop);

        graph.connectNodes(topRight, midRight);
        if (breakGraph) {
            GraphNode between = graph.insertGraphNode(graphLevel, "E", new Point2d(0.5, 1));

            graph.connectNodes(midTop, between);
            graph.connectNodes(between, topRight);
        } else {
            graph.connectNodes(topRight, midTop);
        }

        graph.connectNodes(bottomRight, midRight);
        graph.connectNodes(bottomRight, midBottom);

        graph.connectNodes(bottomLeft, midLeft);
        graph.connectNodes(bottomLeft, midBottom);


        // Inserts 3 additional nodes, after this, production still should be applicable.
        if (additionalNodes) {
            GraphNode rightTop = graph.insertGraphNode(graphLevel, "E", new Point2d(2, 1));
            GraphNode rightCenter = graph.insertGraphNode(graphLevel, "E", new Point2d(2, 0));
            GraphNode rightBottom = graph.insertGraphNode(graphLevel, "E", new Point2d(2, -1));

            graph.connectNodes(topRight, rightTop);
            graph.connectNodes(midRight, rightCenter);
            graph.connectNodes(bottomRight, rightBottom);
        }

        return graph;
    }
}
