package production;

import common.ProductionApplicationException;
import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.Production8TestGraphs;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class Production8Tests extends AbstractProductionTest {
    private final Production production8 = new Production8();

    private final boolean displayMode = false;

    @Test
    public void shouldHaveProperNumber() {
        assertEquals(8, production8.getProductionId());
    }

    @Test
    public void shouldApplyCorrectlyOnThreeLevelGraph() {

        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();
        // given three level graph
        TetrahedralGraph inputGraph = Production8TestGraphs.getProduction8ThreeLevelsGraph(multiStepVisualizer);

        // choose right nodes
        List<GraphNode> nodes = inputGraph
                .getGraphNodesByLevel(inputGraph.getMinLevel() + 2)
                .stream()
                .filter(g -> (g.getCoordinates().getX() == 1 && g.getCoordinates().getY() == 0))
                .collect(Collectors.toList());

        nodes.addAll(inputGraph.getGraphNodesByLevel(inputGraph.getMaxLevel())
                .stream()
                .filter(g -> (
                        g.getCoordinates().getX() == 0 || g.getCoordinates().getX() == 0.5 ||
                                g.getCoordinates().getX() == 1) && g.getCoordinates().getY() == 0).collect(Collectors.toList()));

        multiStepVisualizer.addStep(inputGraph);

        Executable production = () -> production8.apply(inputGraph, null, nodes);
        assertDoesNotThrow(production);

        multiStepVisualizer.addStep(inputGraph);
        if (displayMode) multiStepVisualizer.displayAll();
    }

    @Test
    public void shouldApplyCorrectlyWithMinimalGraph() {

        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();

        // given three level graph
        TetrahedralGraph graph = Production8TestGraphs.getBasicFormExample();
        multiStepVisualizer.addStep(graph);

        List<GraphNode> nodes = getProperNodes(graph);

        Executable production = () -> production8.apply(graph, null, nodes);
        assertDoesNotThrow(production);
        multiStepVisualizer.addStep(graph);
        if (displayMode) multiStepVisualizer.displayAll();
    }

    @Test
    public void shouldNotApplyIfRandomGraphNodeIsMissing() {
        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();

        // given three level graph
        TetrahedralGraph graph = Production8TestGraphs.getBasicFormExample();

        GraphNode randomGraphNode = getRandomGraphNode(graph);
        graph.removeGraphNode(randomGraphNode);

        List<GraphNode> nodes = getProperNodes(graph);

        multiStepVisualizer.addStep(graph);

        Executable production = () -> production8.apply(graph, null, nodes);
        assertThrows(ProductionApplicationException.class, production);

        multiStepVisualizer.addStep(graph);
        if (displayMode) multiStepVisualizer.displayAll();
    }

    @Test
    public void shouldNotApplyIfRandomInteriorNodeIsMissing() {
        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();

        // given three level graph
        TetrahedralGraph graph = Production8TestGraphs.getBasicFormExample();

        InteriorNode randomInteriorNode = getRandomInteriorNode(graph);
        graph.removeInteriorNode(randomInteriorNode);

        List<GraphNode> nodes = getProperNodes(graph);

        multiStepVisualizer.addStep(graph);

        Executable production = () -> production8.apply(graph, null, nodes);
        assertThrows(ProductionApplicationException.class, production);

        multiStepVisualizer.addStep(graph);
        if (displayMode) multiStepVisualizer.displayAll();
    }

    @Test
    public void shouldNotApplyIfRandomEdgeIsMissing() {
        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();

        // given three level graph
        TetrahedralGraph graph = Production8TestGraphs.getBasicFormExample();

        deleteRandomEdge(graph);

        List<GraphNode> nodes = getProperNodes(graph);

        multiStepVisualizer.addStep(graph);

        Executable production = () -> production8.apply(graph, null, nodes);
        assertThrows(ProductionApplicationException.class, production);

        multiStepVisualizer.addStep(graph);
        if (displayMode) multiStepVisualizer.displayAll();
    }

    @Test
    public void shouldNotApplyIfCoordinatesAreBad() {
        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();

        // given three level graph
        TetrahedralGraph graph = Production8TestGraphs.getBasicExampleWithBadCoordinates();

        List<GraphNode> nodes = getProperNodes(graph);

        multiStepVisualizer.addStep(graph);

        Executable production = () -> production8.apply(graph, null, nodes);
        assertThrows(ProductionApplicationException.class, production);

        multiStepVisualizer.addStep(graph);
        if (displayMode) multiStepVisualizer.displayAll();
    }

    @Test
    public void shouldApplyIfRandomGraphNodeIsAdded() {
        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();

        // given three level graph
        TetrahedralGraph graph = Production8TestGraphs.getBasicFormExample();

        GraphNode newAddedGraphNode = addRandomGraphNode(graph);

        List<GraphNode> nodes = getProperNodes(graph);
        nodes.remove(newAddedGraphNode);

        multiStepVisualizer.addStep(graph);

        Executable production = () -> production8.apply(graph, null, nodes);
        assertDoesNotThrow(production);

        multiStepVisualizer.addStep(graph);
        if (displayMode) multiStepVisualizer.displayAll();
    }

    @Test
    public void shouldNotApplyIfLabelIsBad() {
        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();

        // given three level graph
        TetrahedralGraph graph = Production8TestGraphs.getBasicExampleWithBadLabel();

        List<GraphNode> nodes = getProperNodes(graph);

        multiStepVisualizer.addStep(graph);

        Executable production = () -> production8.apply(graph, null, nodes);
        assertThrows(ProductionApplicationException.class, production);

        multiStepVisualizer.addStep(graph);
        if (displayMode) multiStepVisualizer.displayAll();
    }

    private List<GraphNode> getProperNodes(TetrahedralGraph graph) {
        List<GraphNode> nodes = graph
                .getGraphNodes()
                .stream()
                .filter(x -> x.getSymbol().equals("e"))
                .collect(Collectors.toList());

        List<GraphNode> bottomNodes = graph
                .getGraphNodes()
                .stream()
                .filter(x -> x.getSymbol().equals("E"))
                .collect(Collectors.toList());

        nodes.addAll(bottomNodes);
        return nodes;
    }

    private GraphNode getRandomGraphNode(TetrahedralGraph graph) {
        Random r = new Random();
        List<GraphNode> graphNodes = new ArrayList<>(graph.getGraphNodes());
        assertTrue(graphNodes.size() > 0);
        return graphNodes.get(r.nextInt(graphNodes.size()));
    }

    private InteriorNode getRandomInteriorNode(TetrahedralGraph graph) {
        Random r = new Random();
        List<InteriorNode> interiorNodes = new ArrayList<>(graph.getInteriorNodes());
        assertTrue(interiorNodes.size() > 0);
        return interiorNodes.get(r.nextInt(interiorNodes.size()));
    }

    private void deleteRandomEdge(TetrahedralGraph graph) {
        GraphNode randomGraphNode = getRandomGraphNode(graph);
        List<NodeBase> siblings = randomGraphNode.getSiblings().collect(Collectors.toList());
        List<NodeBase> graphNodesInteriors = randomGraphNode.getInteriors().collect(Collectors.toList());
        siblings.addAll(graphNodesInteriors);
        assertFalse(siblings.isEmpty());

        Random r = new Random();
        NodeBase sibling = siblings.get(r.nextInt(siblings.size()));
        graph.removeEdge(randomGraphNode, sibling);
    }

    private GraphNode addRandomGraphNode(TetrahedralGraph graph) {
        GraphNode randomGraphNode = getRandomGraphNode(graph);
        GraphNode newGraphNode = graph.insertGraphNode(2, "E", new Point2d(2, 2));
        graph.connectNodes(newGraphNode, randomGraphNode);
        return newGraphNode;
    }
}
