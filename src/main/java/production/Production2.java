package production;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Production2 extends AbstractProduction {
    @Override
    public int getProductionId() {
        return 2;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        verifyInteriorNodeSymbol(interiorNode, "I");
        verifyGraphNodeListIsEmpty(graphNodeList);
        verifyInteriorNodeIsValid(interiorNode, this::meetsProductionRequirements);

        applyProduction(graph, interiorNode);
    }

    private boolean meetsProductionRequirements(InteriorNode node) {
        List<GraphNode> gns = node.getSiblings().collect(Collectors.toList());
        if (gns.size() != 4)
            return false;

        for (GraphNode oneNode : gns) {
            Set<GraphNode> oneSet = oneNode
                    .getSiblings()
                    .filter(gns::contains)
                    .collect(Collectors.toSet());

            Set<GraphNode> otherSet = gns.stream()
                    .flatMap(x -> x.getSiblings().filter(gns::contains))
                    .collect(Collectors.toSet());

            oneSet.retainAll(otherSet);

            if (oneSet.size() != 2)
                return false;
        }
        for (GraphNode oneNode : gns) {
            Optional<Boolean> properSiblings = oneNode.getSiblings().filter(gns::contains)
                    .map(otherNode ->
                            ((otherNode.getCoordinates().getX() ==
                                    oneNode.getCoordinates().getX()) ||
                                    (otherNode.getCoordinates().getY() ==
                                            oneNode.getCoordinates().getY())
                            ))
                    .reduce(Boolean::logicalAnd);
            if (!(properSiblings.isPresent() && properSiblings.get()))
                return false;
        }
        return true;
    }

    private void applyProduction(TetrahedralGraph graph, InteriorNode rootInteriorNode) {
        rootInteriorNode.setSymbol("i");
        int subgraphLevel = rootInteriorNode.getLevel() + 1;

        List<GraphNode> corners = rootInteriorNode.getSiblings().collect(Collectors.toList());

        double left = corners
                .stream()
                .map(x -> x.getCoordinates().getX())
                .min(Double::compareTo)
                .get();
        double right = corners
                .stream()
                .map(x -> x.getCoordinates().getX())
                .max(Double::compareTo)
                .get();
        double bottom = corners
                .stream()
                .map(x -> x.getCoordinates().getY())
                .min(Double::compareTo)
                .get();
        double top = corners
                .stream()
                .map(x -> x.getCoordinates().getY())
                .max(Double::compareTo)
                .get();
        GraphNode center = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d((left + right) / 2, (top + bottom) / 2));
        GraphNode topLeft = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d(left, top));
        GraphNode topRight = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d(right, top));
        GraphNode bottomLeft = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d(left, bottom));
        GraphNode bottomRight = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d(right, bottom));


        GraphNode midLeft = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d(left, (top + bottom) / 2));
        GraphNode midRight = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d(right, (top + bottom) / 2));
        GraphNode midTop = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d((left + right) / 2, top));
        GraphNode midBottom = graph.insertGraphNode(
                subgraphLevel,
                "E",
                new Point2d((left + right) / 2, bottom));

        InteriorNode upperLeft = graph.insertInteriorNode(
                subgraphLevel,
                "I");
        InteriorNode upperRight = graph.insertInteriorNode(
                subgraphLevel,
                "I");
        InteriorNode lowerLeft = graph.insertInteriorNode(
                subgraphLevel,
                "I");
        InteriorNode lowerRight = graph.insertInteriorNode(
                subgraphLevel,
                "I");

        graph.connectNodes(rootInteriorNode, upperLeft);
        graph.connectNodes(rootInteriorNode, upperRight);
        graph.connectNodes(rootInteriorNode, lowerRight);
        graph.connectNodes(rootInteriorNode, lowerLeft);

        graph.connectNodes(upperLeft, topLeft);
        graph.connectNodes(upperLeft, midTop);
        graph.connectNodes(upperLeft, center);
        graph.connectNodes(upperLeft, midLeft);

        graph.connectNodes(upperRight, midTop);
        graph.connectNodes(upperRight, topRight);
        graph.connectNodes(upperRight, midRight);
        graph.connectNodes(upperRight, center);

        graph.connectNodes(lowerRight, center);
        graph.connectNodes(lowerRight, midRight);
        graph.connectNodes(lowerRight, bottomRight);
        graph.connectNodes(lowerRight, midBottom);

        graph.connectNodes(lowerLeft, midLeft);
        graph.connectNodes(lowerLeft, center);
        graph.connectNodes(lowerLeft, midBottom);
        graph.connectNodes(lowerLeft, bottomLeft);

        graph.connectNodes(topLeft, midTop);
        graph.connectNodes(midTop, topRight);
        graph.connectNodes(topRight, midRight);
        graph.connectNodes(midRight, bottomRight);
        graph.connectNodes(bottomRight, midBottom);
        graph.connectNodes(midBottom, bottomLeft);
        graph.connectNodes(bottomLeft, midLeft);
        graph.connectNodes(midLeft, topLeft);

        graph.connectNodes(center, midTop);
        graph.connectNodes(center, midRight);
        graph.connectNodes(center, midBottom);
        graph.connectNodes(center, midLeft);
    }
}
