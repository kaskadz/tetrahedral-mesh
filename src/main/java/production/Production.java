package production;

import model.TetrahedralGraph;

public interface Production {
    int getProductionId();
    boolean tryApply(TetrahedralGraph graph);
}
