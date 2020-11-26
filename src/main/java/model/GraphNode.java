package model;

import org.graphstream.graph.implementations.AbstractGraph;

public class GraphNode extends LevelNode {
    private final String symbol;
    private final Point2d coordinates;

    protected GraphNode(AbstractGraph graph, String id, String symbol, int level, Point2d coordinates) {
        super(graph, id, level);
        this.symbol = symbol;
        this.coordinates = coordinates;
    }

    public String getSymbol() {
        return symbol;
    }

    public Point2d getCoordinates() {
        return coordinates;
    }
}
