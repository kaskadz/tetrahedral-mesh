package processing;

import common.CustomCollectors;
import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Assignment6Processor extends AbstractProcessor {
    private final static String PROCESSOR_ID = "zad6";

    public static Comparator<GraphNode> byX = Comparator.comparing(
            x -> x.getCoordinates().getX()
    );

    public static Comparator<GraphNode> byY = Comparator.comparing(
            x -> x.getCoordinates().getY()
    );

    @Override
    public String getProcessorId() {
        return PROCESSOR_ID;
    }

    @Override
    public TetrahedralGraph processGraphInternal(TetrahedralGraph graph) {
        InteriorNode entryNode = graph
                .getInteriorNodes()
                .stream()
                .filter(x -> x.getSymbol().equals("E"))
                .collect(CustomCollectors.toSingle());

        applyProduction(1, graph, entryNode, Collections.emptyList());

        InteriorNode level1Interior = entryNode.getChildren().collect(CustomCollectors.toSingle());

        applyProduction(2, graph, level1Interior, Collections.emptyList());

        level1Interior.getChildren().forEach(level2Interior ->
                applyProduction(2, graph, level2Interior, Collections.emptyList()));

        List<GraphNode> sortedNodes = graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream()
                .filter(x -> (x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                        && (Math.abs(x.getCoordinates().getX()) == 0.5 || Math.abs(x.getCoordinates().getY()) == 0.5))
                .sorted(byX.thenComparing(byY))
                .collect(Collectors.toList());

        for (int i = 0; i < sortedNodes.size() - 2; i += 2) {
            List<GraphNode> nodes = sortedNodes
                    .get(i)
                    .getSiblings()
                    .filter(x -> x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                    .collect(Collectors.toList());

            nodes.add(sortedNodes.get(i));
            nodes.sort(byX.thenComparing(byY));

            applyProduction(7, graph, null, nodes);
        }

        List<GraphNode> nodes_2 = graph.getGraphNodesByLevel(graph.getMinLevel() + 2).stream().filter(g -> (g.getCoordinates().getX() == 1 && g.getCoordinates().getY() == 0)).collect(Collectors.toList());
        nodes_2.addAll(graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream().filter(g -> (g.getCoordinates().getX() == 0 || g.getCoordinates().getX() == 0.5 || g.getCoordinates().getX() == 1) &&
                        g.getCoordinates().getY() == 0).collect(Collectors.toList()));
        applyProduction(8, graph, null, nodes_2);
        return graph;
    }
}