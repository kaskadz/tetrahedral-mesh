package processing;

import model.TetrahedralGraph;

public class Initializer {
    public TetrahedralGraph initializeGraph() {
        TetrahedralGraph graph = new TetrahedralGraph();

        graph.insertInteriorNode("e");

        return graph;
    }
}
