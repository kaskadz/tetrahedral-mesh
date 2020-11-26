package model;

import org.graphstream.graph.Node;

public class NodeBase {
    private final TetrahedralGraph graph;
    private final Node node;
    private final int level;
    private final String symbol;

    protected NodeBase(TetrahedralGraph graph, Node node, int level, String symbol) {
        this.graph = graph;
        this.node = node;
        this.level = level;
        this.symbol = symbol;
    }

    public TetrahedralGraph getGraph() {
        return graph;
    }

    public Node getNode() {
        return node;
    }

    public int getLevel() {
        return level;
    }

    public String getSymbol() {
        return symbol;
    }
}
