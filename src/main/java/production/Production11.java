package production;

import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Production11 extends AbstractProduction {
    private GraphNode topLeftExterior;
    private GraphNode topRightExterior;
    private GraphNode middleLeftExterior;
    private GraphNode bottomLeftExterior;
    private GraphNode bottomRightExterior;

    @Override
    public int getProductionId() {
        return 11;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        verifyInteriorNodeIsNull(interiorNode);
        verifyGraphNodeListIsNotEmpty(graphNodeList);
        verifyGraphNodeListSize(graphNodeList, 6);

        boolean requirementsSatisfied = meetsProductionRequirements(graphNodeList);

        if (requirementsSatisfied) applyProduction(graph);
        else throwProductionApplicationException("Graf nie spełnia warunków");
    }

    private void applyProduction(TetrahedralGraph graph) {
        graph.removeEdge(topRightExterior, bottomRightExterior);
        graph.mergeNodes(topLeftExterior, topRightExterior);
        graph.mergeNodes(bottomLeftExterior, bottomRightExterior);
    }

    private boolean meetsProductionRequirements(List<GraphNode> nodes) {
        List<GraphNode> topLevelNodes = nodes.subList(1, 6);

        List<GraphNode> middleNode = topLevelNodes.stream().filter(n -> {
                    List<GraphNode> siblings = n.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());

                    return siblings.size() == 2
                            &&
                            (((siblings.get(0).getCoordinates().getX() / 2) +
                                    (siblings.get(1).getCoordinates().getX())) / 2) == n.getCoordinates().getX()
                            &&
                            (((siblings.get(0).getCoordinates().getY() / 2) +
                                    (siblings.get(1).getCoordinates().getY())) / 2) == n.getCoordinates().getY();
                }
        ).collect(Collectors.toList());

        if (middleNode.size() != 1) return false;

        middleLeftExterior = middleNode.get(0);

        List<GraphNode> topAndBottomLeft =
                middleLeftExterior.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());

        if (topAndBottomLeft.size() != 2) return false;

        topLeftExterior = topAndBottomLeft.get(0);
        bottomLeftExterior = topAndBottomLeft.get(1);

        List<GraphNode> rightTopExteriorList = topLevelNodes.stream().filter(n -> n.getCoordinates().equals(topLeftExterior.getCoordinates()) && n != topLeftExterior).collect(Collectors.toList());

        if (rightTopExteriorList.size() == 0) return false;

        topRightExterior = rightTopExteriorList.get(0);

        List<GraphNode> rightBottomExteriorList = topLevelNodes.stream()
                .filter(n -> n.getCoordinates().equals(bottomLeftExterior.getCoordinates()) && !n.equals(bottomLeftExterior))
                .collect(Collectors.toList());

        if (rightBottomExteriorList.size() == 0) return false;

        bottomRightExterior = rightBottomExteriorList.get(0);

        if (!(
                topRightExterior.getCoordinates().equals(topLeftExterior.getCoordinates()) &&
                        bottomRightExterior.getCoordinates().equals(bottomLeftExterior.getCoordinates()) &&
                        (((topLeftExterior.getCoordinates().getX() / 2) +
                                (bottomLeftExterior.getCoordinates().getX())) / 2) == middleLeftExterior.getCoordinates().getX() &&
                        (((topLeftExterior.getCoordinates().getY() / 2) +
                                (bottomLeftExterior.getCoordinates().getY())) / 2) == middleLeftExterior.getCoordinates().getY()
        )) {
            return false;
        }

        // siblings check
        List<GraphNode> topLeftSiblings = topLeftExterior.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());
        if (topLeftSiblings.size() != 1 || !topLeftSiblings.contains(middleLeftExterior)) return false;

        List<GraphNode> middleLeftSiblings = middleLeftExterior.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());
        if (middleLeftSiblings.size() != 2 || !middleLeftSiblings.contains(topLeftExterior) || !middleLeftSiblings.contains(bottomLeftExterior)) return false;

        List<GraphNode> bottomLeftSiblings = bottomLeftExterior.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());
        if (bottomLeftSiblings.size() != 1 || !bottomLeftSiblings.contains(middleLeftExterior)) return false;

        List<GraphNode> topRightSiblings = topRightExterior.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());
        if (topRightSiblings.size() != 1 || !topRightSiblings.contains(bottomRightExterior)) return false;

        List<GraphNode> bottomRightSiblings = bottomRightExterior.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());
        if (bottomRightSiblings.size() != 1 || !bottomRightSiblings.contains(topRightExterior)) return false;

        List<InteriorNode> interiorNodes = new ArrayList<>(3);
        getCommonInnerNode(topLeftExterior, middleLeftExterior).ifPresent(interiorNodes::add);
        getCommonInnerNode(middleLeftExterior, bottomLeftExterior).ifPresent(interiorNodes::add);
        getCommonInnerNode(topRightExterior, bottomRightExterior).ifPresent(interiorNodes::add);

        if (interiorNodes.stream().distinct().count() != 3) return false;

        Optional<InteriorNode> leftInterior = getCommonParent(interiorNodes.get(0), interiorNodes.get(1));
        Optional<InteriorNode> rightInterior = interiorNodes.get(2).getParent();

        if (!leftInterior.isPresent() || !rightInterior.isPresent()) return false;

        return nodes.get(0).getInteriors().filter(g -> g.equals(leftInterior.get()) || g.equals(rightInterior.get())).distinct().count() == 2;
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

}
