package production;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

import java.util.List;

public class Production1 extends AbstractProduction {
    @Override
    public int getProductionId() {
        return 1;
    }

    @Override
    public void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        verifyInteriorNodeSymbol(interiorNode, "E");
        verifyGraphNodeListIsEmpty(graphNodeList);

        applyProduction(graph, interiorNode);
    }

    private void applyProduction(TetrahedralGraph graph, InteriorNode interiorNode) {
        interiorNode.setSymbol("e");

        int subgraphLevel = interiorNode.getLevel() + 1;

        GraphNode topLeft = graph.insertGraphNode(subgraphLevel, "E", new Point2d(-1, 1));
        GraphNode topRight = graph.insertGraphNode(subgraphLevel, "E", new Point2d(1, 1));
        GraphNode bottomLeft = graph.insertGraphNode(subgraphLevel, "E", new Point2d(-1, -1));
        GraphNode bottomRight = graph.insertGraphNode(subgraphLevel, "E", new Point2d(1, -1));
        InteriorNode center = graph.insertInteriorNode(subgraphLevel, "I");

        graph.connectNodes(center, topLeft);
        graph.connectNodes(center, topRight);
        graph.connectNodes(center, bottomLeft);
        graph.connectNodes(center, bottomRight);

        graph.connectNodes(topLeft, topRight);
        graph.connectNodes(topRight, bottomRight);
        graph.connectNodes(bottomRight, bottomLeft);
        graph.connectNodes(bottomLeft, topLeft);

        graph.connectNodes(interiorNode, center);
    }
}
