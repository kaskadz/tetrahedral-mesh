package processing;

import model.TetrahedralGraph;
import visualization.MultiStepVisualizer;

public interface Processor {
    String getProcessorId();
    void setMultiStepVisualizer(MultiStepVisualizer multiStepVisualizer);

    TetrahedralGraph processGraph(TetrahedralGraph graph);
}
