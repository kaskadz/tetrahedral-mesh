package model;

import common.NodeType;
import common.NodeWrapper;
import org.graphstream.graph.Element;
import org.graphstream.graph.Node;

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

    public Stream<String> getInteriorsIds() {
        return getNode().neighborNodes()
                .map(NodeWrapper::new)
                .filter(x -> x.getNodeType() == NodeType.INTERIOR)
                .map(NodeWrapper::getNode)
                .map(Element::getId);
    }

    public Stream<InteriorNode> getInteriors() {
        return getInteriorsIds().map(x -> getGraph().getInteriorNode(x));
    }

    @Override
    public boolean isDirectlyConnectedWith(String nodeId) {
        return isSibling(nodeId) || getInteriorsIds().anyMatch(x -> x.equals(nodeId));
    }

}
