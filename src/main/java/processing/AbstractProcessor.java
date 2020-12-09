package processing;

import common.ProcessingException;
import production.Production;
import production.Productions;

public abstract class AbstractProcessor implements Processor {
    protected Production getProductionById(int productionId) {
        return Productions.productionsMap.get(productionId);
    }

    protected void throwProcessorException(String message) {
        throw newProcessorException(message);
    }

    protected ProcessingException newProcessorException(String message) {
        return new ProcessingException(getProcessorId(), message);
    }
}
