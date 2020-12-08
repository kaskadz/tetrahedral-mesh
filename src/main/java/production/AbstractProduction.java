package production;

import common.ProductionApplicationException;

public abstract class AbstractProduction implements Production {

    protected void throwProductionApplicationException(String message) {
        throw new ProductionApplicationException(getProductionId(), message);
    }
}
