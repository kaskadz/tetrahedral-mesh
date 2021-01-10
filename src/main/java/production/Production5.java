package production;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Production5 extends AbstractProduction {
    @Override
    public int getProductionId() {
        return 5;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        System.out.println("apply");
        verifyInteriorNodeSymbol(interiorNode, "I");
        verifyGraphNodeListIsEmpty(graphNodeList);
        verifyInteriorNodeIsValid(interiorNode, this::meetsProductionRequirements);

        applyProduction(graph, interiorNode);
    }

    private boolean meetsProductionRequirements(InteriorNode node) {
        List<GraphNode> cornerNodes = node.getSiblings().collect(Collectors.toList());

        if (cornerNodes.size() != 4) return false;

//      sprawdzam czy dokładnie dwa nody są ze sobą połączone bezpośrednio
        int directlyConnectedCornerNodes = 0;
        for (GraphNode cornerNode : cornerNodes) {
            List<GraphNode> siblings = cornerNode.getSiblings().collect(Collectors.toList());
            List<GraphNode> cornerNodesSiblings = siblings.stream().filter(cornerNodes::contains).collect(Collectors.toList());
            if (cornerNodesSiblings.size() == 0) {

            } else if (cornerNodesSiblings.size() == 1) {
                directlyConnectedCornerNodes += 1;
            } else {
                return false;
            }
        }
        if (directlyConnectedCornerNodes != 2) return false;

//      sprawdzam czy dokładnie 3 pary nodów mają wspólny wierzchołek, który jest dokładnie na środku pomiędzy nimi
        int nodesBetweenTwo = 0;
        for (GraphNode firstNode : cornerNodes) {
            for (GraphNode secondNode : cornerNodes) {
                if (!firstNode.equals(secondNode)) {
                    List<GraphNode> commonSiblings = firstNode
                            .getSiblings()
                            .filter(x -> secondNode.getSiblings().collect(Collectors.toList()).contains(x))
                            .collect(Collectors.toList());
                    boolean isNodeBetweenTwo = commonSiblings
                            .stream()
                            .anyMatch(common -> (common.getCoordinates().getX() == (firstNode.getCoordinates().getX() + secondNode.getCoordinates().getX()) / 2) ||
                                    (common.getCoordinates().getY() == (firstNode.getCoordinates().getY() + secondNode.getCoordinates().getY()) / 2));
                    if (!isNodeBetweenTwo) {
                        nodesBetweenTwo += 1;
                    }
                }
            }
        }
        if (nodesBetweenTwo != 6) {
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
        double midTop1 = corners
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
