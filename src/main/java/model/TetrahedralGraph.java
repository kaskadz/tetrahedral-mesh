package model;

import common.Attributes;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.HashMap;
import java.util.Map;

public class TetrahedralGraph extends MultiGraph {
    private final Map<String, GraphNode> graphNodes = new HashMap<>();
    private final Map<String, InteriorNode> interiorNodes = new HashMap<>();

    public TetrahedralGraph(String id) {
        super(id);
    }

    public GraphNode insertGraphNode(String id, int level, String symbol, Point2d coordinates) {
        Node node = this.addNode(id);
        node.setAttribute(Attributes.FROZEN_LAYOUT);
        node.setAttribute(Attributes.Label, symbol);
        node.setAttribute(Attributes.XY, coordinates.getX(), coordinates.getY());
        var graphNode = new GraphNode(this, node, level, "H", coordinates);
        graphNodes.put(id, graphNode);
        return graphNode;
    }

    public GraphNode getGraphNode(String id) {
        return graphNodes.get(id);
    }

    public InteriorNode insertInteriorNode(String id, int level, String symbol) {
        Node node = this.addNode(id);
        node.setAttribute(Attributes.FROZEN_LAYOUT);
        node.setAttribute(Attributes.Label, symbol);
        var interiorNode = new InteriorNode(this, node, level, symbol);
        interiorNodes.put(id, interiorNode);
        return interiorNode;
    }

    public InteriorNode getInteriorNode(String id) {
        return interiorNodes.get(id);
    }

    public void mergeNodes(GraphNode n1, GraphNode n2) {

    }
}
