package production;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Production10 extends AbstractProduction {
    @Override
    public int getProductionId() {
        return 10;
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
        InteriorNode interior = graph.insertInteriorNode(
                subgraphLevel,
                "I");

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

        graph.connectNodes(rootInteriorNode, interior);

        graph.connectNodes(interior, topLeft);
        graph.connectNodes(interior, topRight);
        graph.connectNodes(interior, bottomLeft);
        graph.connectNodes(interior, bottomRight);

        graph.connectNodes(topLeft, topRight);
        graph.connectNodes(topLeft, bottomLeft);
        graph.connectNodes(bottomRight, topRight);
        graph.connectNodes(bottomRight, bottomLeft);
        
    }
}
