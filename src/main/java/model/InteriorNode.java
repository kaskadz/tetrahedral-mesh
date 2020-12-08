package model;

import common.NodeType;
import common.NodeWrapper;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

import java.util.Optional;
import java.util.stream.Stream;

public class InteriorNode extends NodeBase {
    protected InteriorNode(TetrahedralGraph graph, Node node, String symbol) {
        super(graph, node);
    }

    public Optional<String> getParentId() {
        return getNode().neighborNodes()
                .map(NodeWrapper::new)
                .filter(x -> x.getNodeType() == NodeType.INTERIOR)
                .filter(x -> x.getLevel() == (getLevel() - 1))
                .map(NodeWrapper::getNode)
                .map(Element::getId)
                .findFirst();
    }

    public Optional<InteriorNode> getParent() {
        return getParentId().map(x -> getGraph().getInteriorNode(x));
    }

    public Stream<String> getChildrenIds() {
        return getNode().neighborNodes()
                .map(NodeWrapper::new)
                .filter(x -> x.getNodeType() == NodeType.INTERIOR)
                .filter(x -> x.getLevel() == (getLevel() + 1))
                .map(NodeWrapper::getNode)
                .map(Element::getId);
    }

    public Stream<InteriorNode> getChildren() {
        return getChildrenIds().map(x -> getGraph().getInteriorNode(x));
    }


    public Stream<String> getSiblingsIds() {
        return getNode().neighborNodes()
                .map(NodeWrapper::new)
                .filter(x -> x.getNodeType() == NodeType.REGULAR)
                .map(NodeWrapper::getNode)
                .map(Element::getId);
    }

    public Stream<GraphNode> getSiblings() {
        return getSiblingsIds().map(x -> getGraph().getGraphNode(x));
    }
}
