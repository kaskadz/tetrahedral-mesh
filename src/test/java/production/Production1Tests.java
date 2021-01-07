package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import visualization.MultiLevelVisualizer;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Production1Tests extends AbstractProductionTest {
    private final Production sut = new Production1();
    private final boolean visualMode = false;

    @Test
    public void shouldHaveProperNumber() {
        // Arrange
        Production sut = new Production1();

        // Act & Assert
        assertEquals(1, sut.getProductionId());
    }

    @Test
    public void shouldModifyGraphProperly() {
        // Arrange
        MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
        TetrahedralGraph graph = new TetrahedralGraph();
        InteriorNode initialNode = graph.insertInteriorNode(0, "E");
        visualizer.addStep(graph);

        // Act
        sut.apply(graph, initialNode, Collections.emptyList());
        visualizer.addStep(graph);

        // Assert
        assertThat(initialNode.getSymbol()).isEqualTo("e");
        assertThat(initialNode.getSiblingsIds()).isEmpty();
        assertThat(initialNode.getChildrenIds()).hasSize(1);

        InteriorNode interiorNode = initialNode.getChildren().collect(CustomCollectors.toSingle());
        assertThat(interiorNode.getSiblingsIds()).hasSize(4);

        assertThat(graph.getGraph().edges()).hasSize(9);

        Set<GraphNode> graphNodes = interiorNode.getSiblings().collect(Collectors.toSet());
        for (GraphNode gn : graphNodes) {
            assertThat(gn.getInteriorsIds()).hasSize(1);

            Set<String> siblings = gn.getSiblingsIds().collect(Collectors.toSet());
            assertThat(siblings).hasSize(2);
        }

        GraphNode lastVisitedNode = interiorNode.getSiblings().findFirst().get();
        GraphNode firstNode = lastVisitedNode.getSiblings().findFirst().get();
        GraphNode currentNode = firstNode;
        int iteration = 0;
        while ((iteration > 1 && currentNode != firstNode) || iteration < 4) {
            GraphNode lnn = lastVisitedNode;
            lastVisitedNode = currentNode;
            currentNode = currentNode.getSiblings()
                    .filter(x -> !x.getId().equals(lnn.getId()))
                    .collect(CustomCollectors.toSingle());
            iteration++;
        }
        assertThat(iteration).isEqualTo(4);

        Set<Point2d> childNodes = interiorNode.getSiblings()
                .map(GraphNode::getCoordinates)
                .collect(Collectors.toSet());
        assertThat(childNodes)
                .hasSize(4)
                .contains(new Point2d(1, 1))
                .contains(new Point2d(1, -1))
                .contains(new Point2d(-1, 1))
                .contains(new Point2d(-1, -1));

        // The order of the positioned nodes can be verified in the visual mode

        if (visualMode) visualizer.displayAll();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidGraphs")
    public void shouldNotModifyGraphIfNotApplicable(String testCaseName, TetrahedralGraph graph) {
        // Arrange
        MultiLevelVisualizer visualizer = new MultiLevelVisualizer();
        if (visualMode) visualizer.displayGraph(graph, testCaseName);

        // Act
        Executable production = () -> sut.apply(graph, null, Collections.emptyList());

        // Assert
        assertThrows(ProductionApplicationException.class, production);
    }

    public static Stream<Arguments> invalidGraphs() {
        List<Pair<String, TetrahedralGraph>> invalidGraphs = new ArrayList<>();

        invalidGraphs.add(new Pair<>("Small e as entry symbol", initializeGraph(g -> {
            g.insertInteriorNode(0, "e");
        })));

        invalidGraphs.add(new Pair<>("No interior node", initializeGraph(g -> {
            g.insertGraphNode(0, "E", new Point2d(1.0, 1.0));
        })));

        return invalidGraphs.stream().map(Tuple::toArray).map(Arguments::of);
    }
}
