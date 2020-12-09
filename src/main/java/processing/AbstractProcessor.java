package processing;

import app.App;
import common.ProcessingException;
import production.Production;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractProcessor implements Processor {
    private final Map<Integer, Production> productionMap;

    public AbstractProcessor() {
        this.productionMap = Arrays.stream(App.productions)
                .collect(Collectors.toMap(
                        Production::getProductionId,
                        production -> production));
    }

    protected Production getProductionById(int productionId) {
        return productionMap.get(productionId);
    }

    protected void throwProcessorException(String message) {
        throw newProcessorException(message);
    }

    protected ProcessingException newProcessorException(String message) {
        return new ProcessingException(getProcessorId(), message);
    }
}
