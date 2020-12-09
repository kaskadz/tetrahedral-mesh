package processing;

import common.CustomCollectors;
import model.InteriorNode;
import model.TetrahedralGraph;

import java.util.Collections;

public class Assignment1Processor extends AbstractProcessor {
    @Override
    public String getProcessorId() {
        return "zad1";
    }

    @Override
    public TetrahedralGraph processGraph(TetrahedralGraph graph) {
        InteriorNode entryNode = graph
                .getInteriorNodes()
                .stream()
                .filter(x -> x.getSymbol().equals("E"))
                .collect(CustomCollectors.toSingle());

        getProductionById(1).apply(graph, entryNode, Collections.emptyList());

        InteriorNode level1Interior = entryNode.getChildren().collect(CustomCollectors.toSingle());

        getProductionById(2).apply(graph, level1Interior, Collections.emptyList());

        level1Interior.getChildren()
                .forEach(level2Interior -> getProductionById(2)
                        .apply(graph, level2Interior, Collections.emptyList()));

        return graph;
    }
}
