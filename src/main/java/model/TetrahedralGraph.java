package model;

import common.Attributes;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;
import java.util.stream.Collectors;

public class TetrahedralGraph {
    private final Graph graph;
    private final Map<String, GraphNode> graphNodes = new HashMap<>();
    private final Map<String, InteriorNode> interiorNodes = new HashMap<>();

    public TetrahedralGraph() {
        graph = new SingleGraph("tetrahedralGraph");
    }

    public Graph getGraph() {
        return graph;
    }

    public GraphNode insertGraphNode(String symbol, Point2d coordinates) {
        String id = generateId();
        Node node = graph.addNode(id);
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

    public void removeGraphNode(String id) {
        graphNodes.remove(id);
        graph.removeNode(id);
    }

    public void removeGraphNode(GraphNode graphNode) {
        graph.removeNode(graphNode.getId());
    }

    public InteriorNode insertInteriorNode(String symbol) {
        String id = generateId();
        Node node = graph.addNode(id);
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

    public Edge connectNodes(GraphNode graphNode1, GraphNode graphNode2) {
        String id = generateId();
        var edge = graph.addEdge(id, graphNode1.getNode(), graphNode2.getNode(), false);
        edge.addAttribute(Attributes.EdgeType.SAME_LEVEL);
        return edge;
    }

    public Edge connectNodes(InteriorNode interiorNode, GraphNode graphNode) {
        String id = generateId();
        var edge = graph.addEdge(id, interiorNode.getNode(), graphNode.getNode(), false);
        edge.addAttribute(Attributes.EdgeType.PARENT);
        return edge;
    }

    public Edge connectNodes(InteriorNode interiorNode1, InteriorNode interiorNode2) {
        String id = generateId();
        var edge = graph.addEdge(id, interiorNode1.getNode(), interiorNode2.getNode(), false);
        edge.addAttribute(Attributes.EdgeType.SAME_LEVEL);
        return edge;
    }

    public void mergeNodes(GraphNode n1, GraphNode n2) {
        Set<String> n2Siblings = n2.getSiblingsIds().collect(Collectors.toUnmodifiableSet());
        List<GraphNode> savedSiblings = n1.getSiblings()
                .filter(x -> !x.getId().equals(n2.getId()))
                .filter(x -> !n2Siblings.contains(x.getId()))
                .collect(Collectors.toList());

        Set<String> n2Interiors = n2.getInteriorsIds().collect(Collectors.toUnmodifiableSet());
        List<InteriorNode> savedInteriors = n1.getInteriors()
                .filter(x -> !n2Interiors.contains(x.getId()))
                .collect(Collectors.toList());

        removeGraphNode(n1);

        savedSiblings.forEach(x -> connectNodes(x, n2));
        savedInteriors.forEach(x -> connectNodes(x, n2));
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
