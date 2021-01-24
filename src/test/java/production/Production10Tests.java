package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import initialization.EntrySymbolInitializer;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.javatuples.Quartet;
import org.javatuples.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import visualization.MultiLevelVisualizer;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class Production10Tests extends AbstractProductionTest {
    private final Production sut = new Production10();
    private final boolean visualMode = false;

    @Test
    public void shouldHaveProperNumber() {
        // Act & Assert
        assertEquals(10, sut.getProductionId());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("validGraphs")
    public void shouldApplyProductionIfApplicable(
            String testCaseName, TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodes) {
        // Arrange
        MultiStepMultiLevelVisualizer visualizer = new MultiStepMultiLevelVisualizer();
        visualizer.addStep(graph);

        // Act
        Executable production = () -> sut.apply(graph, interiorNode, graphNodes);

        // Assert
        assertDoesNotThrow(production);
        visualizer.addStep(graph);

        assertChildGraphIsCreatedProperly(interiorNode);
        if (visualMode) visualizer.displayAll(testCaseName);
    }

    private void assertChildGraphIsCreatedProperly(InteriorNode interiorNode) {
        assertThat(interiorNode.getChildrenIds()).hasSize(1);
        assertThat(interiorNode.getSymbol()).isEqualTo("i");

        Set<GraphNode> graphNodeChildren = interiorNode
                .getChildren()
                .flatMap(InteriorNode::getSiblings)
                .collect(Collectors.toSet());
        assertThat(graphNodeChildren).hasSize(4);

    }



    private static Optional<GraphNode> getNthNode(Collection<GraphNode> graphNodes, int n) {
        return graphNodes.stream()
                .sorted(Comparator
                        .comparingDouble((GraphNode o) -> o.getCoordinates().getY())
                        .thenComparingDouble(o -> o.getCoordinates().getX()))
                .skip(n)
                .findFirst();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidGraphs")
    public void shouldThrowExceptionIfProductionIsNotApplicable(
            String testCaseName, TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodes) {
        // Arrange
        MultiLevelVisualizer visualizer = new MultiLevelVisualizer();
        if (visualMode) visualizer.displayGraph(graph, testCaseName);

        // Act
        Executable production = () -> sut.apply(graph, interiorNode, graphNodes);

        // Assert
        assertThrows(ProductionApplicationException.class, production);
    }

    public static Stream<Arguments> invalidGraphs() {
        List<Quartet<String, TetrahedralGraph, InteriorNode, List<GraphNode>>> invalidGraphs = new ArrayList<>();

        EntrySymbolInitializer entrySymbolInitializer = new EntrySymbolInitializer();

        TetrahedralGraph graph;
        InteriorNode interiorNode;
        List<GraphNode> graphNodes = Collections.emptyList();

        // Graph 1
        graph = entrySymbolInitializer.initializeGraph();
        invalidGraphs.add(new Quartet<>("Interior node is null", graph, null, graphNodes));

        // Graph 2
        graph = entrySymbolInitializer.initializeGraph();
        InteriorNode interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        invalidGraphs.add(new Quartet<>("GraphNodeList is not empty", graph, interiorNode,
                graph.getGraphNodes().stream().limit(3).collect(Collectors.toList())));

        // Graph 3
        graph = new TetrahedralGraph();
        graph.insertInteriorNode(0, "i");
        interiorNode = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        invalidGraphs.add(new Quartet<>("Application on small 'i'", graph, interiorNode, graphNodes));

        // Graph 4
        graph = entrySymbolInitializer.initializeGraph();
        interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        graph.removeGraphNode(interiorNode.getSiblingsIds().findFirst().get());
        invalidGraphs.add(new Quartet<>("Missing interior's sibling", graph, interiorNode, graphNodes));

        // Graph 5
        graph = entrySymbolInitializer.initializeGraph();
        interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        GraphNode graphNode = interiorNode.getSiblings().findFirst().get();
        String sibling1Id = graphNode.getId();
        String sibling2Id = graphNode.getSiblingsIds().findFirst().get();
        graph.getGraph().removeEdge(sibling1Id, sibling2Id);
        invalidGraphs.add(new Quartet<>("Missing edge between interior's siblings", graph, interiorNode, graphNodes));

        // Graph 6
        graph = entrySymbolInitializer.initializeGraph();
        interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        graphNode = getNthNode(interiorNode.getSiblings().collect(Collectors.toList()), 0).get();
        Set<GraphNode> siblings = graphNode.getSiblings().collect(Collectors.toSet());
        Point2d coordinates = graphNode.getCoordinates();
        coordinates = new Point2d(coordinates.getX() * 2.0, coordinates.getY() * 2);
        int level = graphNode.getLevel();
        String symbol = graphNode.getSymbol();
        graph.removeGraphNode(graphNode);
        graphNode = graph.insertGraphNode(level, symbol, coordinates);
        for (GraphNode gn : siblings) {
            graph.connectNodes(graphNode, gn);
        }
        graph.connectNodes(interiorNode, graphNode);
        invalidGraphs.add(new Quartet<>("Misplaced interior's sibling", graph, interiorNode, graphNodes));

        return invalidGraphs.stream().map(Tuple::toArray).map(Arguments::of);
    }

    public static Stream<Arguments> validGraphs() {
        List<Quartet<String, TetrahedralGraph, InteriorNode, List<GraphNode>>> validGraphs = new ArrayList<>();

        TetrahedralGraph graph;
        InteriorNode interiorNode;
        List<GraphNode> graphNodes = Collections.emptyList();

        EntrySymbolInitializer entrySymbolInitializer = new EntrySymbolInitializer();

        // Graph 1
        graph = entrySymbolInitializer.initializeGraph();
        InteriorNode interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        validGraphs.add(new Quartet<>("P1 -> (P10)", graph, interiorNode, graphNodes));

        // Graph 2
        graph = entrySymbolInitializer.initializeGraph();
        interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode1 = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        new Production2().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().findFirst().get();
        validGraphs.add(new Quartet<>("P1 -> P2 -> (P10)", graph, interiorNode, graphNodes));

        return validGraphs.stream().map(Tuple::toArray).map(Arguments::of);
    }
}
