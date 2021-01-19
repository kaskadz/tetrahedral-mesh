package production;


import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Production8 extends AbstractProduction {
    private GraphNode leftMiddleExterior;
    private GraphNode rightMiddleExterior;
    private GraphNode leftBottomExterior;
    private GraphNode rightBottomExterior;

    @Override
    public int getProductionId() {
        return 8;
    }

    /**
     * Applies production 8.
     *
     * @param interiorNode  expected to be null
     * @param graphNodeList list with upper exterior node and 5 lower exterior nodes (in that order)
     */
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
        graph.mergeNodes(leftBottomExterior, rightBottomExterior);
        graph.mergeNodes(leftMiddleExterior, rightMiddleExterior);
    }

    private boolean meetsProductionRequirements(List<GraphNode> nodes) {

        List<GraphNode> topLevelNodes = nodes.subList(1, 6);

        List<GraphNode> topExteriorList = topLevelNodes.stream().filter(n -> {
            List<GraphNode> siblings = n.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());
            return siblings.size() == 2 && siblings.get(0).getCoordinates().equals(siblings.get(1).getCoordinates());
        }).collect(Collectors.toList());

        if (topExteriorList.size() != 1) return false;

        GraphNode topExterior = topExteriorList.get(0);
        List<GraphNode> middleExteriors = topExterior.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());

        if (middleExteriors.size() != 2) return false;

        leftMiddleExterior = middleExteriors.get(0);
        rightMiddleExterior = middleExteriors.get(1);

        List<GraphNode> bottomLeftExteriorList = leftMiddleExterior.getSiblings().filter(g -> {
            List<GraphNode> middleBottomNodes = topLevelNodes.stream().filter(g1 -> !g1.equals(topExterior)).collect(Collectors.toList());
            return middleBottomNodes.contains(g);
        }).collect(Collectors.toList());

        if (bottomLeftExteriorList.size() != 1) return false;

        leftBottomExterior = bottomLeftExteriorList.get(0);

        List<GraphNode> bottomRightExteriorList = rightMiddleExterior.getSiblings().filter(g -> {
            List<GraphNode> middleBottomNodes = topLevelNodes.stream().filter(g1 -> !g1.equals(topExterior)).collect(Collectors.toList());
            return middleBottomNodes.contains(g);
        }).collect(Collectors.toList());

        if (bottomRightExteriorList.size() != 1) return false;

        rightBottomExterior = bottomRightExteriorList.get(0);

        if (leftMiddleExterior.equals(rightMiddleExterior) || leftBottomExterior.equals(rightBottomExterior))
            return false;

        double x1 = topExterior.getCoordinates().getX();
        double y1 = topExterior.getCoordinates().getY();

        if (!leftBottomExterior.getCoordinates().equals(rightBottomExterior.getCoordinates())) return false;

        double x2 = leftBottomExterior.getCoordinates().getX();
        double y2 = leftBottomExterior.getCoordinates().getY();

        if (leftMiddleExterior.getCoordinates().getX() != (x1 + x2) / 2 ||
                leftMiddleExterior.getCoordinates().getY() != (y1 + y2) / 2 ||
                rightMiddleExterior.getCoordinates().getX() != (x1 + x2) / 2 ||
                rightMiddleExterior.getCoordinates().getY() != (y1 + y2) / 2) return false;

        List<InteriorNode> interiorNodes = new ArrayList<>(4);

        getCommonInnerNode(topExterior, leftMiddleExterior).ifPresent(interiorNodes::add);
        getCommonInnerNode(topExterior, rightMiddleExterior).ifPresent(interiorNodes::add);
        getCommonInnerNode(leftMiddleExterior, leftBottomExterior).ifPresent(interiorNodes::add);
        getCommonInnerNode(rightMiddleExterior, rightBottomExterior).ifPresent(interiorNodes::add);

        if (interiorNodes.stream().distinct().count() != 4) return false;

        Optional<InteriorNode> leftInterior = getCommonParent(interiorNodes.get(0), interiorNodes.get(2));
        Optional<InteriorNode> rightInterior = getCommonParent(interiorNodes.get(1), interiorNodes.get(3));

        if (!leftInterior.isPresent() || !rightInterior.isPresent()) return false;

        return nodes.get(0).getInteriors().filter(g -> g.equals(leftInterior.get()) || g.equals(rightInterior.get())).distinct().count() == 2;
    }

    private Optional<InteriorNode> getCommonParent(InteriorNode node1, InteriorNode node2) {
        return node1.getParent()
                .filter(node -> node.equals(node2.getParent().orElse(null)));
    }

    private Optional<InteriorNode> getCommonInnerNode(GraphNode node1, GraphNode node2) {
        return node1.getInteriors()
                .filter(node -> node2.getInteriors().anyMatch(node::equals))
                .findFirst();
    }
}
