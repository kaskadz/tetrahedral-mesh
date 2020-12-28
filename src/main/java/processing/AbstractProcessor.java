package processing;

import common.ProcessingException;
import model.GraphNode;
import model.InteriorNode;
import model.TetrahedralGraph;
import production.Production;
import production.Productions;
import visualization.MultiStepVisualizer;

import java.util.List;

public abstract class AbstractProcessor implements Processor {
    private MultiStepVisualizer multiStepVisualizer;

    public void setMultiStepVisualizer(MultiStepVisualizer multiStepVisualizer) {
        this.multiStepVisualizer = multiStepVisualizer;
    }

    @Override
    public TetrahedralGraph processGraph(TetrahedralGraph graph) {
        multiStepVisualizer.addStep(graph);
        return processGraphInternal(graph);
    }

    protected void throwProcessorException(String message) {
        throw newProcessorException(message);
    }

    protected ProcessingException newProcessorException(String message) {
        return new ProcessingException(getProcessorId(), message);
    }

    protected void applyProduction(int productionId, TetrahedralGraph graph, InteriorNode interiorNode, List<GraphNode> graphNodeList) {
        Production production = getProductionById(productionId);
        production.apply(graph, interiorNode, graphNodeList);
        multiStepVisualizer.addStep(graph);
    }

    abstract protected TetrahedralGraph processGraphInternal(TetrahedralGraph graph);

    private Production getProductionById(int productionId) {
        return Productions.productionsMap.get(productionId);
    }
}