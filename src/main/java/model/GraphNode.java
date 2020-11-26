package model;

import org.graphstream.graph.Node;

public class GraphNode extends NodeBase {
    private final Point2d coordinates;

    protected GraphNode(TetrahedralGraph graph, Node node, int level, String symbol, Point2d coordinates) {
        super(graph, node, level, symbol);
        this.coordinates = coordinates;
    }

    public Point2d getCoordinates() {
        return coordinates;
    }
}
