package model;

import common.NodeWrapper;
import common.NodeType;
import common.StreamUtils;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

import java.util.Optional;
import java.util.stream.Stream;

public class GraphNode extends NodeBase {
    private final Point2d coordinates;

    protected GraphNode(TetrahedralGraph graph, Node node, Point2d coordinates) {
        super(graph, node);
        this.coordinates = coordinates;
    }

    public Point2d getCoordinates() {
        return coordinates;
    }

    public Stream<String> getSiblingsIds() {
        return StreamUtils.asStream(getNode().getNeighborNodeIterator())
                .map(NodeWrapper::new)
                .filter(x -> x.getNodeType() == NodeType.REGULAR)
                .map(NodeWrapper::getNode)
                .map(Element::getId);
    }

    public Stream<GraphNode> getSiblings() {
        return getSiblingsIds().map(x -> getGraph().getGraphNode(x));
    }

    public Optional<String> getInteriorId() {
        return StreamUtils.asStream(getNode().getNeighborNodeIterator())
                .map(NodeWrapper::new)
                .filter(x -> x.getNodeType() == NodeType.INTERIOR)
                .map(NodeWrapper::getNode)
                .map(Element::getId)
                .findFirst();
    }

    public Optional<InteriorNode> getInterior() {
        return getInteriorId().map(x -> getGraph().getInteriorNode(x));
    }
}
