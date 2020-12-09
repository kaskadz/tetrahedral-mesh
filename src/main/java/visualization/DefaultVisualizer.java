package visualization;

import model.TetrahedralGraph;

public class DefaultVisualizer implements Visualizer {
    private final int level;

    public DefaultVisualizer(int level) {
        this.level = level;
    }

    @Override
    public void displayGraph(TetrahedralGraph graph) {
        graph.displayLevel(level);
    }
}
