package model;

import common.NodeType;
import common.NodeWrapper;
import common.StreamUtils;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

import java.util.Iterator;

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

    public NodeWrapper getNodeWrapper() {
        return nodeWrapper;
    }

    public String getSymbol() {
        return nodeWrapper.getLabel();
    }

    public void setSymbol(String symbol) {
        nodeWrapper.setLabel(symbol);
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

    public Iterator<String> getDfsIterator() {
        return StreamUtils.asStream(getNode().getDepthFirstIterator())
                .map(Element::getId)
                .iterator();
    }

    public Iterator<String> getBfsIterator() {
        return StreamUtils.asStream(getNode().getBreadthFirstIterator())
                .map(Element::getId)
                .iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeBase)) return false;
        NodeBase nodeBase = (NodeBase) o;
        return getId().equals(nodeBase.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
