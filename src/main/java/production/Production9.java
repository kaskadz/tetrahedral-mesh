package production;

import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Production9 extends AbstractProduction {
    @Override
    public int getProductionId() {
        return 7;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        verifyInteriorNodeIsNull(interiorNode);
        verifyGraphNodeListSize(graphNodeList, 1);
        verifyGraphIsValid(graphNodeList.get(0), this::meetsProductionRequirements);

        applyProduction(graph, graphNodeList.get(0));
    }

    private void verifyGraphIsValid(GraphNode graphNode, Predicate<GraphNode> interiorNodePredicate) {
        if (!interiorNodePredicate.test(graphNode)) {
            throwProductionApplicationException("Invalid interiorNode");
        }
    }

    private boolean meetsProductionRequirements(GraphNode node) {

        if (node.getInteriors().count() != 2) {
            return false;
        }

        Stream<GraphNode> graphPoints = node.getInteriors()
                .filter(n -> n.getChildren().count() == 2)
                .flatMap(interior -> {
                    verifyInteriorNodeSymbol(interior, "I");
                    List<GraphNode> graphNodes = getBottomNodesSorted(interior);
                    if (graphNodes.size() == 3 && isInMiddle(graphNodes.get(0), graphNodes.get(2), graphNodes.get(1))) {
                        return Stream.of(graphNodes.get(0), graphNodes.get(1), graphNodes.get(2));
                    } else {
                        return Stream.of();
                    }
                });

        if (graphPoints.count() == 6 && graphPoints.distinct().count() == 4) {
            List<GraphNode> graphNodes = graphPoints.sorted(Comparator.comparingDouble((GraphNode x) -> x.getCoordinates().getX())
                    .thenComparingDouble(x -> x.getCoordinates().getY()))
                    .collect(Collectors.toList());
            return graphNodes.get(1).getCoordinates().equals(graphNodes.get(2).getCoordinates());
        }
        return false;
    }

    private List<GraphNode> getBottomNodesSorted(InteriorNode interior) {
        return interior.getChildren()
                .flatMap(InteriorNode::getSiblings)
                .sorted(Comparator.comparingDouble((GraphNode x) -> x.getCoordinates().getX())
                        .thenComparingDouble(x -> x.getCoordinates().getY()))
                .collect(Collectors.toList());
    }

    private boolean isInMiddle(GraphNode left, GraphNode right, GraphNode middle) {
        return (left.getCoordinates().getX() + right.getCoordinates().getX()) / 2 == middle.getCoordinates().getX()
                || (left.getCoordinates().getY() + right.getCoordinates().getY()) / 2 == middle.getCoordinates().getY();
    }

    private void applyProduction(TetrahedralGraph graph, GraphNode rootNode) {
        List<InteriorNode> internal = rootNode.getInteriors().collect(Collectors.toList());
        graph.mergeNodes(getBottomNodesSorted(internal.get(0)).get(1), getBottomNodesSorted(internal.get(1)).get(1));

    }
}
