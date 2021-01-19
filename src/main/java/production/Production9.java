package production;

import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Production9 extends AbstractProduction {
    @Override
    public int getProductionId() {
        return 9;
    }

    /**
     * Applies production 9.
     *
     * @param interiorNode  expected to be null
     * @param graphNodeList list with upper exterior node and 4 lower exterior nodes (in that order)
     */
    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        verifyInteriorNodeIsNull(interiorNode);
        verifyGraphNodeListSize(graphNodeList, 4);
        verifyExteriorNodesAreValid(graphNodeList, this::meetsProductionRequirements);

        applyProduction(graph, graphNodeList);
    }

    private boolean meetsProductionRequirements(List<GraphNode> nodes) {

        List<GraphNode> lowerNodesSorted = getLowerNodesSorted(nodes);
        if (!areLowerNodesCoordinatesCorrect(lowerNodesSorted)
                || !areLowerNodesConnectedCorrectly(lowerNodesSorted)) {
            return false;
        }

        List<InteriorNode> interiorNodes = new ArrayList<>(4);

        getCommonInnerNode(lowerNodesSorted.get(0), lowerNodesSorted.get(1))
                .ifPresent(interiorNodes::add);
        getCommonInnerNode(lowerNodesSorted.get(3), lowerNodesSorted.get(1))
                .ifPresent(interiorNodes::add);
        getCommonInnerNode(lowerNodesSorted.get(0), lowerNodesSorted.get(2))
                .ifPresent(interiorNodes::add);
        getCommonInnerNode(lowerNodesSorted.get(3), lowerNodesSorted.get(2))
                .ifPresent(interiorNodes::add);

        if (interiorNodes.stream().distinct().count() != 4) {
            return false;
        }

        return getCommonParent(interiorNodes.get(0), interiorNodes.get(1))
                .map(parent1 -> getCommonParent(interiorNodes.get(2), interiorNodes.get(3))
                        .map(parent2 -> getCommonSiblingNode(parent1, parent2)
                                .map(topNode -> areTopLevelInteriorNodesCorrect(parent1, parent2, topNode)
                                ).orElse(false)
                        ).orElse(false)
                ).orElse(false);
    }

    private boolean areTopLevelInteriorNodesCorrect(InteriorNode node1, InteriorNode node2, GraphNode topNode) {
        return !node1.equals(node2)
                && node1.getSiblings().anyMatch(topNode::equals)
                && node2.getSiblings().anyMatch(topNode::equals);
    }

    private Optional<GraphNode> getCommonSiblingNode(InteriorNode node1, InteriorNode node2) {
        return node1.getSiblings()
                .filter(node -> node2.getSiblings().anyMatch(node::equals))
                .findFirst();
    }

    private Optional<InteriorNode> getCommonInnerNode(GraphNode node1, GraphNode node2) {
        return node1.getInteriors()
                .filter(node -> node2.getInteriors().anyMatch(node::equals))
                .findFirst();
    }

    private Optional<InteriorNode> getCommonParent(InteriorNode node1, InteriorNode node2) {
        return node1.getParent()
                .filter(node -> node.equals(node2.getParent().orElse(null)));
    }

    private List<GraphNode> getLowerNodesSorted(List<GraphNode> nodes) {
        return nodes.stream()
                .sorted(Comparator
                        .comparingDouble((GraphNode x) -> x.getCoordinates().getX())
                        .thenComparingDouble(x -> x.getCoordinates().getY()))
                .collect(Collectors.toList());
    }

    private boolean areLowerNodesConnectedCorrectly(List<GraphNode> nodesSorted) {
        List<GraphNode> nodesToMerge = nodesSorted.subList(1, 3);
        return nodesSorted.get(0).getSiblings().filter(nodesToMerge::contains).count() == 2
                && nodesSorted.get(3).getSiblings().filter(nodesToMerge::contains).count() == 2;
    }

    private boolean areLowerNodesCoordinatesCorrect(List<GraphNode> nodesSorted) {
        return nodesSorted.get(1).getCoordinates().equals(nodesSorted.get(2).getCoordinates())
                && isInTheMiddle(nodesSorted.get(0), nodesSorted.get(3), nodesSorted.get(1))
                && isInTheMiddle(nodesSorted.get(0), nodesSorted.get(3), nodesSorted.get(2))
                && !nodesSorted.get(0).getCoordinates().equals(nodesSorted.get(3).getCoordinates());
    }

    private boolean isInTheMiddle(GraphNode left, GraphNode right, GraphNode middle) {
        return (left.getCoordinates().getX() + right.getCoordinates().getX()) / 2 == middle.getCoordinates().getX()
                || (left.getCoordinates().getY() + right.getCoordinates().getY()) / 2 == middle.getCoordinates().getY();
    }

    private void applyProduction(TetrahedralGraph graph, List<GraphNode> nodes) {
        List<GraphNode> lowerNodesSorted = getLowerNodesSorted(nodes);
        graph.mergeNodes(lowerNodesSorted.get(1), lowerNodesSorted.get(2));
    }

}
