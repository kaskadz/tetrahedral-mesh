package production;

import model.TetrahedralGraph;

public interface Production {
    int getProductionId();
    TetrahedralGraph tryApply(TetrahedralGraph graph);
}
