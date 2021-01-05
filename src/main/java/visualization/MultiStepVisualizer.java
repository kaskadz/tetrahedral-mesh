package visualization;

import model.TetrahedralGraph;

public interface MultiStepVisualizer {
    void addStep(TetrahedralGraph graph);
    void displayAll();
}
