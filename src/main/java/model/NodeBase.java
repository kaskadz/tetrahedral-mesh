package model;

import common.NodeWrapper;
import common.NodeType;
import common.StreamUtils;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

import java.util.stream.Stream;

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

    public void setSymbol(String symbol){
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

    public Stream<String> getDfsIdsStream() {
        return StreamUtils.asStream(getNode().getDepthFirstIterator())
                .map(Element::getId);
    }

    public Stream<String> getBfsIdsStream() {
        return StreamUtils.asStream(getNode().getBreadthFirstIterator())
                .map(Element::getId);
    }
}
