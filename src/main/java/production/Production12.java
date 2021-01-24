package production;

import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Production12 extends AbstractProduction {
    private GraphNode topExterior;
    private GraphNode leftBottomExterior;
    private GraphNode rightBottomExterior;
    private GraphNode middleExterior;

    @Override
    public int getProductionId() {
        return 12;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        verifyInteriorNodeIsNull(interiorNode);
        verifyGraphNodeListIsNotEmpty(graphNodeList);
        verifyGraphNodeListSize(graphNodeList, 5);

        boolean requirementsSatisfied = meetsProductionRequirements(graphNodeList);

        if (requirementsSatisfied) applyProduction(graph);
        else throwProductionApplicationException("Graf nie spełnia warunków");
    }

    private void applyProduction(TetrahedralGraph graph) {
        graph.removeEdge(topExterior, rightBottomExterior);
        graph.mergeNodes(leftBottomExterior, rightBottomExterior);
    }

    private boolean meetsProductionRequirements(List<GraphNode> nodes) {
        List<GraphNode> topLevelNodes = nodes.subList(1, 5);

        List<GraphNode> topExteriorList = topLevelNodes.stream().filter(n -> {
            List<GraphNode> siblings = n.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());

            return siblings.size() == 2 &&
                    (siblings.get(0).getCoordinates().getX() + siblings.get(1).getCoordinates().getX()) / 2 != n.getCoordinates().getX() &&
                    (siblings.get(0).getCoordinates().getY() + siblings.get(1).getCoordinates().getY()) / 2 != n.getCoordinates().getY();
        }).collect(Collectors.toList());

        if (topExteriorList.size() != 1) return false;

        topExterior = topExteriorList.get(0);
        List<GraphNode> topSiblings = topExterior.getSiblings().filter(topLevelNodes::contains).collect(Collectors.toList());

        if (topSiblings.size() != 2) return false;


        List<GraphNode> bottomLeftExteriorList = topSiblings.get(0).getSiblings().filter(g -> {
            List<GraphNode> middleBottomNodes = topLevelNodes.stream().filter(g1 -> !g1.equals(topExterior)).collect(Collectors.toList());
            return middleBottomNodes.contains(g);
        }).collect(Collectors.toList());

        if (bottomLeftExteriorList.size() == 1) {
            middleExterior = topSiblings.get(0);
            leftBottomExterior = bottomLeftExteriorList.get(0);

        } else {
            List<GraphNode> secondBottomLeftExteriorList = topSiblings.get(1).getSiblings().filter(g -> {
                List<GraphNode> middleBottomNodes = topLevelNodes.stream().filter(g1 -> !g1.equals(topExterior)).collect(Collectors.toList());
                return middleBottomNodes.contains(g);
            }).collect(Collectors.toList());

            if (secondBottomLeftExteriorList.size() == 1) {
                middleExterior = topSiblings.get(1);
                leftBottomExterior = secondBottomLeftExteriorList.get(0);
            } else return false;
        }

        List<GraphNode> rightBottomExteriorList = topLevelNodes.stream().filter(g ->
                !g.equals(topExterior) &&
                        !g.equals(middleExterior) &&
                        !g.equals(leftBottomExterior)).collect(Collectors.toList());

        if (rightBottomExteriorList.size() != 1) return false;
        if (leftBottomExterior.equals(rightBottomExterior)) return false;

        rightBottomExterior = rightBottomExteriorList.get(0);

        double x1 = topExterior.getCoordinates().getX();
        double y1 = topExterior.getCoordinates().getY();

        if (!leftBottomExterior.getCoordinates().equals(rightBottomExterior.getCoordinates())) return false;

        double x2 = leftBottomExterior.getCoordinates().getX();
        double y2 = leftBottomExterior.getCoordinates().getY();

        if (middleExterior.getCoordinates().getX() != (x1 + x2) / 2 ||
                middleExterior.getCoordinates().getY() != (y1 + y2) / 2 ||
                middleExterior.getCoordinates().getX() != (x1 + x2) / 2 ||
                middleExterior.getCoordinates().getY() != (y1 + y2) / 2) return false;

        List<InteriorNode> interiorNodes = new ArrayList<>(3);
        getCommonInnerNode(topExterior, middleExterior).ifPresent(interiorNodes::add);
        getCommonInnerNode(middleExterior, leftBottomExterior).ifPresent(interiorNodes::add);
        getCommonInnerNode(topExterior, rightBottomExterior).ifPresent(interiorNodes::add);

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
