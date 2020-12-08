package processing;

import model.TetrahedralGraph;

public interface Processor {
    String getProcessorId();

    TetrahedralGraph processGraph(TetrahedralGraph graph);
}
