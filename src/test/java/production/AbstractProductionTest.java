package production;

import model.TetrahedralGraph;

import java.util.function.Consumer;

public abstract class AbstractProductionTest {
    protected static TetrahedralGraph initializeGraph(Consumer<TetrahedralGraph> initializer) {
        TetrahedralGraph graph = new TetrahedralGraph();
        initializer.accept(graph);
        return graph;
    }
}
