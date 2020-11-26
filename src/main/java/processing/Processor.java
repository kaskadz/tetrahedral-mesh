package processing;

import model.TetrahedralGraph;
import production.Production;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Processor {
    private final Map<Integer, Production> productionMap;

    public Processor(List<Production> productions) {
        this.productionMap = productions
                .stream()
                .collect(Collectors.toMap(
                        Production::getProductionId,
                        production -> production));
    }

    public TetrahedralGraph applyProductions(TetrahedralGraph graph, int[] productionIds) {
        List<Production> productionStream = Arrays.stream(productionIds)
                .mapToObj(productionMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        for (Production p : productionStream) {
            graph = p.tryApply(graph);
        }

        return graph;
    }
}
