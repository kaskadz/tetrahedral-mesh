package processing;

import common.CustomCollectors;
import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.*;
import java.util.stream.Collectors;

public class Assignment7Processor extends AbstractProcessor {
    private final static String PROCESSOR_ID = "zad7";

    @Override
    public String getProcessorId() {
        return PROCESSOR_ID;
    }

    @Override
    public TetrahedralGraph processGraph(TetrahedralGraph graph) {

        graph = new Assignment1Processor().processGraph(graph);

        List<InteriorNode> interiors = new ArrayList<>(graph
                .getInteriorNodesByLevel(graph.getMaxLevel() - 1));

        GraphNode entryNode = interiors
                .stream()
                .flatMap(InteriorNode::getSiblings)
                .distinct()
                .filter(i -> i.getInteriors().collect(Collectors.toList()).containsAll(interiors))
                .collect(CustomCollectors.toSingle());

        getProductionById(7).apply(graph, null, Collections.singletonList(entryNode));
        getProductionById(7).apply(graph, null, Collections.singletonList(entryNode));

        return graph;
    }
}