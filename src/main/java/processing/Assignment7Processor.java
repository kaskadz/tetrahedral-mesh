package processing;

import model.GraphNode;
import model.TetrahedralGraph;

import java.util.*;
import java.util.stream.Collectors;

public class Assignment7Processor extends AbstractProcessor {
    private final static String PROCESSOR_ID = "zad5";

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
    public TetrahedralGraph processGraph(TetrahedralGraph graph) {

        graph = new Assignment1Processor().processGraph(graph);

        List<GraphNode> sortedNodes = graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream()
                .filter(x -> (x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                        && (Math.abs(x.getCoordinates().getX()) == 0.5 || Math.abs(x.getCoordinates().getY()) == 0.5))
                .sorted(byX.thenComparing(byY))
                .collect(Collectors.toList());


        for(int i=0; i<sortedNodes.size()-2; i+=2) {
            List<GraphNode> nodes = sortedNodes
                    .get(i)
                    .getSiblings()
                    .filter(x -> x.getCoordinates().getX() == 0.0 || x.getCoordinates().getY() == 0.0)
                    .collect(Collectors.toList());

            nodes.add(sortedNodes.get(i));
            nodes.sort(byX.thenComparing(byY));


            getProductionById(7).apply(graph, null, nodes);

//            break;

        }
        return graph;
    }
}