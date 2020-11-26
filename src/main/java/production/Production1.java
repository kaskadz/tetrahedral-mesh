package production;

import model.TetrahedralGraph;

public class Production1 implements Production {
    @Override
    public int getProductionId() {
        return 1;
    }

    @Override
    public TetrahedralGraph tryApply(TetrahedralGraph graph) {
        return null;
    }
}
