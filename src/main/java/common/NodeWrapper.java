package common;

import org.graphstream.graph.Node;

public class NodeWrapper {
    private final Node node;

    public NodeWrapper(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public int getLevel() {
        return (int) node.getAttribute(Attributes.LEVEL);
    }

    public NodeType getNodeType() {
        if (node.hasAttribute(Attributes.NodeType.INTERIOR)) {
            return NodeType.INTERIOR;
        }

        if (node.hasAttribute(Attributes.NodeType.REGULAR)) {
            return NodeType.REGULAR;
        }

        throw new IllegalStateException("Illegal node type");
    }

    public String getLabel() {
        return (String) node.getAttribute(Attributes.LABEL);
    }

    public void setLabel(String label) {
        node.setAttribute(Attributes.LABEL, label);
    }
}
