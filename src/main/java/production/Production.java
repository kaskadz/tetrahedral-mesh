package production;

import model.TetrahedralGraph;

public interface Production {
    Integer getProductionId();
    TetrahedralGraph tryApply(TetrahedralGraph graph, int level);
}
