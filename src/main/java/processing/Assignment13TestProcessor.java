package processing;

import common.CustomCollectors;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Assignment13TestProcessor extends AbstractProcessor {
    private final static String PROCESSOR_ID = "test13";
    @Override
    public String getProcessorId() {
        return PROCESSOR_ID;
    }

    public static Comparator<GraphNode> byX = Comparator.comparing(
            x -> x.getCoordinates().getX()
    );

    public static Comparator<GraphNode> byY = Comparator.comparing(
            x -> x.getCoordinates().getY()
    );

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

        int newSubgraphLevel = graph.getMaxLevel() + 1;

        graph.getInteriorNodesByLevel(graph.getMaxLevel()).stream().forEach(i -> {
            InteriorNode ni = graph.insertInteriorNode(
                    newSubgraphLevel,
                    "I"
            );
            graph.connectNodes(i, ni);

            List<GraphNode> sibs = i.getSiblings().map(s -> {
                GraphNode g = graph.insertGraphNode(newSubgraphLevel, "E", s.getCoordinates());

                graph.connectNodes(ni, g);

                return g;
            }).collect(Collectors.toList());

            for(int k=0; k<sibs.size(); k++) {
                for(int j=k+1; j<sibs.size(); j++){
                    if (sibs.get(k).getCoordinates().getX() ==  sibs.get(j).getCoordinates().getX() ||
                            sibs.get(k).getCoordinates().getY() ==  sibs.get(j).getCoordinates().getY()) {
                        graph.connectNodes(sibs.get(k), sibs.get(j));
                    }
                }
            }

        });

        this.getMultiStepVisualizer().addStep(graph);


        List<GraphNode> sortedNodes = graph.getGraphNodesByLevel(graph.getMaxLevel())
                .stream()
                .filter(x -> (x.getCoordinates().getX() == 0.0 && x.getCoordinates().getY() == 0.0))
                .sorted(byX.thenComparing(byY))
                .collect(Collectors.toList());


        for(int i=0; i<sortedNodes.size(); i++) {
            for(GraphNode g : sortedNodes.get(i).getSiblings().collect(Collectors.toList())) {
                List<GraphNode> nodes = Arrays.asList(g, sortedNodes.get(i));
                nodes.forEach(z -> System.out.printf("x = %f y = %f id =%s\n", z.getCoordinates().getX(), z.getCoordinates().getY(), z.getId()));

                applyProduction(13, graph, null, nodes);
//                break;

            }
            break;
        }

        return graph;
    }
}