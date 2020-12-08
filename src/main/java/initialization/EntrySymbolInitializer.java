package initialization;

import model.TetrahedralGraph;

public class EntrySymbolInitializer implements Initializer {
    public TetrahedralGraph initializeGraph() {
        TetrahedralGraph graph = new TetrahedralGraph();

        graph.insertInteriorNode(0, "E");

        return graph;
    }
}
