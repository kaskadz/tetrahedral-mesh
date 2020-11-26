package model;

import common.Attributes;
import org.graphstream.graph.Edge;
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

    public GraphNode insertGraphNode(String id, String symbol, Point2d coordinates) {
        Node node = this.addNode(id);
        node.setAttribute(Attributes.FROZEN_LAYOUT);
        node.setAttribute(Attributes.LABEL, symbol);
        node.setAttribute(Attributes.NodeType.REGULAR);
        node.setAttribute(Attributes.XY, coordinates.getX(), coordinates.getY());
        var graphNode = new GraphNode(this, node, coordinates);
        graphNodes.put(id, graphNode);
        return graphNode;
    }

    public GraphNode getGraphNode(String id) {
        return graphNodes.get(id);
    }

    public InteriorNode insertInteriorNode(String id, String symbol) {
        Node node = this.addNode(id);
        node.setAttribute(Attributes.FROZEN_LAYOUT);
        node.setAttribute(Attributes.LABEL, symbol);
        node.setAttribute(Attributes.NodeType.INTERIOR);
        var interiorNode = new InteriorNode(this, node, symbol);
        interiorNodes.put(id, interiorNode);
        return interiorNode;
    }

    public InteriorNode getInteriorNode(String id) {
        return interiorNodes.get(id);
    }

    public Edge connectNodes(String id, GraphNode graphNode1, GraphNode graphNode2) {
        var edge = this.addEdge(id, graphNode1.getNode(), graphNode2.getNode(), false);
        edge.addAttribute(Attributes.EdgeType.SAME_LEVEL);
        return edge;
    }

    public Edge connectNodes(String id, InteriorNode interiorNode, GraphNode graphNode) {
        var edge = this.addEdge(id, interiorNode.getNode(), graphNode.getNode(), false);
        edge.addAttribute(Attributes.EdgeType.PARENT);
        return edge;
    }

    public Edge connectNodes(String id, InteriorNode interiorNode1, InteriorNode interiorNode2) {
        var edge = this.addEdge(id, interiorNode1.getNode(), interiorNode2.getNode(), false);
        edge.addAttribute(Attributes.EdgeType.SAME_LEVEL);
        return edge;
    }

    public void mergeNodes(GraphNode n1, GraphNode n2) {

    }
}
