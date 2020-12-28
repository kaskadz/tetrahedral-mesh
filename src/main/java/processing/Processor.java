package processing;

import model.TetrahedralGraph;
import visualization.MultiStepVisualizer;

public interface Processor {
    String getProcessorId();

    TetrahedralGraph processGraph(TetrahedralGraph graph, MultiStepVisualizer visualizer);
}
