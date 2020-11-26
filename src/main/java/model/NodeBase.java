package model;

import common.NodeAttributesAccessor;
import common.NodeType;
import org.graphstream.graph.Node;

public class NodeBase {
    private final TetrahedralGraph graph;
    private final Node node;
    private final NodeAttributesAccessor nodeAttributesAccessor;

    protected NodeBase(TetrahedralGraph graph, Node node) {
        this.graph = graph;
        this.node = node;
        this.nodeAttributesAccessor = new NodeAttributesAccessor(node);
    }

    public TetrahedralGraph getGraph() {
        return graph;
    }

    public Node getNode() {
        return node;
    }

    public NodeAttributesAccessor getNodeAttributesAccessor() {
        return nodeAttributesAccessor;
    }

    public String getSymbol() {
        return nodeAttributesAccessor.getLabel();
    }

    public String getId() {
        return node.getId();
    }

    public NodeType getNodeType() {
        return nodeAttributesAccessor.getNodeType();
    }

    public int getLevel() {
        return nodeAttributesAccessor.getLevel();
    }
}
