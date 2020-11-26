package model;

import common.NodeWrapper;
import common.NodeType;
import org.graphstream.graph.Node;

public class NodeBase {
    private final TetrahedralGraph graph;
    private final NodeWrapper nodeWrapper;

    protected NodeBase(TetrahedralGraph graph, Node node) {
        this.graph = graph;
        this.nodeWrapper = new NodeWrapper(node);
    }

    public TetrahedralGraph getGraph() {
        return graph;
    }

    public Node getNode() {
        return nodeWrapper.getNode();
    }

    public NodeWrapper getNodeAttributesAccessor() {
        return nodeWrapper;
    }

    public String getSymbol() {
        return nodeWrapper.getLabel();
    }

    public String getId() {
        return nodeWrapper.getNode().getId();
    }

    public NodeType getNodeType() {
        return nodeWrapper.getNodeType();
    }

    public int getLevel() {
        return nodeWrapper.getLevel();
    }
}
