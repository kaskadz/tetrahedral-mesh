package production;

import model.GraphNode;
import model.TetrahedralGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import utils.Production12TestGraphs;
import visualization.MultiStepMultiLevelVisualizer;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Production12Tests extends AbstractProductionTest {
    private final Production production12 = new Production12();

    private final boolean displayMode = false;

    @Test
    public void shouldApplyCorrectlyWithMinimalGraph() {

        MultiStepMultiLevelVisualizer multiStepVisualizer = new MultiStepMultiLevelVisualizer();

        // given three level graph
        TetrahedralGraph graph = Production12TestGraphs.getBasicFormExample();
        multiStepVisualizer.addStep(graph);

        List<GraphNode> nodes = getProperNodes(graph);

        Executable production = () -> production12.apply(graph, null, nodes);
        assertDoesNotThrow(production);
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
}
