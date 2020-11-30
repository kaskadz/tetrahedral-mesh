package production;

import common.NodeType;
import model.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Optional;

public class Production1 implements Production {
    @Override
    public int getProductionId() {
        return 1;
    }

    private boolean meetsProductionRequirements(InteriorNode node){
        return node.getSymbol().equals("E");
    }

    private void applyProduction(TetrahedralGraph graph, InteriorNode rootInteriorNode){
        rootInteriorNode.setSymbol("e");
        int subgraphLevel = rootInteriorNode.getLevel() + 1;
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
    }

    private Optional<InteriorNode> leftProductionSideRoot(TetrahedralGraph graph){
        for (InteriorNode interNode: graph.getInteriorNodes()) {
            if(meetsProductionRequirements(interNode)){
                return Optional.of(interNode);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean tryApply(TetrahedralGraph graph) {
        Optional<InteriorNode> productionRoot = leftProductionSideRoot(graph);
        if(productionRoot.isPresent()){
            applyProduction(graph, productionRoot.get());
            return true;
        }
        return false;
    }
}
