package production;

import common.CustomCollectors;
import common.ProductionApplicationException;
import initialization.EntrySymbolInitializer;
import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class Production2Tests extends AbstractProductionTest {
    private final Production sut = new Production2();

    @Test
    public void shouldHaveProperNumber() {
        // Act & Assert
        assertEquals(2, sut.getProductionId());
    }

    @ParameterizedTest
    @MethodSource("validGraphs")
    public void shouldApplyProductionIfApplicable(
            TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodes) {
        // Act
        Executable production = () -> sut.apply(graph, interiorNode, graphNodes);

        // Assert
        assertDoesNotThrow(production);
        assertChildGraphIsCreatedProperly(interiorNode);
    }

    private void assertChildGraphIsCreatedProperly(InteriorNode interiorNode) {
        assertThat(interiorNode.getChildrenIds()).hasSize(4);

        Set<String> graphNodeChildren = interiorNode
                .getChildren()
                .flatMap(InteriorNode::getSiblingsIds)
                .collect(Collectors.toSet());
        assertThat(graphNodeChildren).hasSize(9);
    }

    @ParameterizedTest
    @MethodSource("invalidGraphs")
    public void shouldThrowExceptionIfProductionIsNotApplicable(
            TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodes) {
        // Act
        Executable production = () -> sut.apply(graph, interiorNode, graphNodes);

        // Assert
        assertThrows(ProductionApplicationException.class, production);
    }

    public static Stream<Arguments> invalidGraphs() {
        List<Triplet<TetrahedralGraph, InteriorNode, List<GraphNode>>> invalidGraphs = new ArrayList<>();

        EntrySymbolInitializer entrySymbolInitializer = new EntrySymbolInitializer();

        TetrahedralGraph graph;
        InteriorNode interiorNode;
        List<GraphNode> graphNodes = Collections.emptyList();

        // Graph 1
        graph = entrySymbolInitializer.initializeGraph();
        invalidGraphs.add(new Triplet<>(graph, null, graphNodes));

        // Graph 2
        graph = entrySymbolInitializer.initializeGraph();
        InteriorNode interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        invalidGraphs.add(new Triplet<>(graph, interiorNode, graph.getGraphNodes().stream().limit(3).collect(Collectors.toList())));

        // Graph 3
        graph = entrySymbolInitializer.initializeGraph();
        interiorNode = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        invalidGraphs.add(new Triplet<>(graph, interiorNode, graphNodes));

        // Graph 4
        graph = entrySymbolInitializer.initializeGraph();
        interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        interiorNode.getSiblingsIds().findFirst().ifPresent(graph::removeGraphNode);
        invalidGraphs.add(new Triplet<>(graph, interiorNode, graphNodes));


        return invalidGraphs.stream().map(Tuple::toArray).map(Arguments::of);
    }

    public static Stream<Arguments> validGraphs() {
        List<Triplet<TetrahedralGraph, InteriorNode, List<GraphNode>>> validGraphs = new ArrayList<>();

        TetrahedralGraph graph;
        InteriorNode interiorNode;
        List<GraphNode> graphNodes = Collections.emptyList();

        EntrySymbolInitializer entrySymbolInitializer = new EntrySymbolInitializer();

        // Graph 1
        graph = entrySymbolInitializer.initializeGraph();
        InteriorNode interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        validGraphs.add(new Triplet<>(graph, interiorNode, graphNodes));

        // Graph 2
        graph = entrySymbolInitializer.initializeGraph();
        interiorNode1 = graph.getInteriorNodes().stream().collect(CustomCollectors.toSingle());
        new Production1().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode1 = interiorNode1.getChildren().collect(CustomCollectors.toSingle());
        new Production2().apply(graph, interiorNode1, Collections.emptyList());
        interiorNode = interiorNode1.getChildren().findFirst().get();
        validGraphs.add(new Triplet<>(graph, interiorNode, graphNodes));

        return validGraphs.stream().map(Tuple::toArray).map(Arguments::of);
    }
}
