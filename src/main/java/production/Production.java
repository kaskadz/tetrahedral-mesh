package production;

import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.List;

public interface Production {
    int getProductionId();

    void apply(TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList);
}
