package production;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Production3 extends AbstractProduction {
    @Override
    public int getProductionId() {
        return 3;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        verifyInteriorNodeSymbol(interiorNode, "I");
        verifyGraphNodeListIsEmpty(graphNodeList);
        verifyInteriorNodeIsValid(interiorNode, this::meetsProductionRequirements);

        applyProduction(graph, interiorNode);
    }

    private boolean meetsProductionRequirements(InteriorNode node) {
        List<GraphNode> cornerNodes = node.getSiblings().collect(Collectors.toList());
        if (cornerNodes.size() != 4) return false;

        int lonelyCornerNodes = 0, cornerNodesSeparateByOther = 0;

        for (GraphNode cornerNode : cornerNodes) {
            long numOfCornerSiblings = cornerNode.getSiblings().filter(cornerNodes::contains).count();

            if (numOfCornerSiblings == 2) {
                // siblings of corner nodes (x2, y2) and (x4, y4) go here
                Optional<Boolean> properSiblings = cornerNode.getSiblings().filter(cornerNodes::contains)
                        .map(otherNode ->
                                ((otherNode.getCoordinates().getX() ==
                                        cornerNode.getCoordinates().getX()) ||
                                        (otherNode.getCoordinates().getY() ==
                                                cornerNode.getCoordinates().getY())
                                ))
                        .reduce(Boolean::logicalAnd);

                if (!(properSiblings.isPresent() && properSiblings.get()))
                    return false;

                lonelyCornerNodes++;
            } else if (numOfCornerSiblings == 1) {
                // siblings of corner nodes (x1, y1) and (x3, y3) go here
                // first sibling is one of corners
                Optional<Boolean> properSiblings = cornerNode.getSiblings().filter(cornerNodes::contains)
                        .filter(cornerNodes::contains)
                        .map(otherNode ->
                                ((otherNode.getCoordinates().getX() ==
                                        cornerNode.getCoordinates().getX()) ||
                                        (otherNode.getCoordinates().getY() ==
                                                cornerNode.getCoordinates().getY())
                                ))
                        .reduce(Boolean::logicalAnd);

                if (!(properSiblings.isPresent() && properSiblings.get()))
                    return false;

                // second sibling is between corners
                Optional<Boolean> correctMiddleNodeExist = cornerNode.getSiblings()
                        .filter(otherNode -> !cornerNodes.contains(otherNode))
                        .map(otherNode -> {
                            List<GraphNode> otherNodeSiblings = otherNode.getSiblings().collect(Collectors.toList());

                            double x = otherNodeSiblings.stream()
                                    .map(middleNodeSibling -> middleNodeSibling.getCoordinates().getX())
                                    .reduce(0.0, Double::sum);

                            double y = otherNodeSiblings.stream()
                                    .map(middleNodeSibling -> middleNodeSibling.getCoordinates().getY())
                                    .reduce(0.0, Double::sum);

                            return otherNode.getCoordinates().getX() == (x / 2.0) && otherNode.getCoordinates().getY() == (y / 2.0);
                        })
                        .reduce(Boolean::logicalOr);

                if (!(correctMiddleNodeExist.isPresent() && correctMiddleNodeExist.get()))
                    return false;

                cornerNodesSeparateByOther++;
            } else {
                return false;
            }
        }

        return lonelyCornerNodes == 2 && cornerNodesSeparateByOther == 2;
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
