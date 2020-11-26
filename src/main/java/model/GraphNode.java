package model;

import common.NodeAttributesAccessor;
import common.NodeType;
import common.StreamUtils;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

import java.util.Optional;
import java.util.stream.Stream;

public class GraphNode extends NodeBase {
    private final Point2d coordinates;

    protected GraphNode(TetrahedralGraph graph, Node node, int level, String symbol, Point2d coordinates) {
        super(graph, node);
        this.coordinates = coordinates;
    }

    public Point2d getCoordinates() {
        return coordinates;
    }

    public Stream<String> getSameLevelNeighbourIds() {
        return StreamUtils.asStream(getNode().getNeighborNodeIterator())
                .map(NodeAttributesAccessor::new)
                .filter(x -> x.getNodeType() == NodeType.REGULAR)
                .map(NodeAttributesAccessor::getNode)
                .map(Element::getId);
    }

    public Optional<String> getParentId() {
        return StreamUtils.asStream(getNode().getNeighborNodeIterator())
                .map(NodeAttributesAccessor::new)
                .filter(x -> x.getNodeType() == NodeType.INTERIOR)
                .filter(x -> x.getLevel() == (getLevel() - 1))
                .map(NodeAttributesAccessor::getNode)
                .map(Element::getId)
                .findFirst();
    }
}
