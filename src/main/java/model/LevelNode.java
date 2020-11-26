package model;

import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleNode;

public class LevelNode extends SingleNode {
    private final int level;

    protected LevelNode(AbstractGraph graph, String id, int level) {
        super(graph, id);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
