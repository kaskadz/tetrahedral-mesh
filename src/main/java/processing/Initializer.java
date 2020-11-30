package processing;

import model.TetrahedralGraph;

public class Initializer {
    public TetrahedralGraph initializeGraph() {
        TetrahedralGraph graph = new TetrahedralGraph();

        graph.insertInteriorNode(0, "E");

        return graph;
    }
}
