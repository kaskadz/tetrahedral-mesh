package production;

import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;

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

        graph.connectNodes(rootInteriorNode, center);
    }

    private Optional<InteriorNode> leftProductionSideRoot(TetrahedralGraph graph){
        return graph.getInteriorNodes().stream().filter(this::meetsProductionRequirements).findFirst();
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
