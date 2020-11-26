package model;

import org.graphstream.graph.implementations.AbstractGraph;
import org.javatuples.Quartet;

public class InteriorNode extends LevelNode {
    private final String symbol;
    private Quartet<GraphNode, GraphNode, GraphNode, GraphNode> quartet;

    public InteriorNode(AbstractGraph graph,
                        String id,
                        String symbol,
                        int level,
                        GraphNode g1,
                        GraphNode g2,
                        GraphNode g3,
                        GraphNode g4) {
        super(graph, id, level);
        this.symbol = symbol;
        quartet = new Quartet<>(g1, g2, g3, g4);
    }

    public String getSymbol() {
        return symbol;
    }
}
