package visualization;

import common.Attributes;
import common.CustomPredicates;
import common.ResourceLoader;
import model.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GraphLevelViewFactory {
    private final String styles = ResourceLoader.readStyleSheet();

    public Graph createGraphView(TetrahedralGraph graph, int level) {
        Collection<GraphNode> graphNodes = graph.getGraphNodesByLevel(level);
        Collection<InteriorNode> interiorNodes = graph.getInteriorNodesByLevel(level);
        Graph viewGraph = new SingleGraph(String.format("Level %d", level));
        viewGraph.setAttribute(Attributes.STYLESHEET, styles);

        // copy nodes
        graphNodes.forEach(x -> addGraphNode(viewGraph, x));
        interiorNodes.forEach(x -> addInteriorNode(viewGraph, x));

        // copy edges
        Set<String> graphNodeIdsFromLevel = graphNodes.stream()
                .map(NodeBase::getId)
                .collect(Collectors.toSet());

        // interior edges
        interiorNodes.stream()
                .map(NodeBase::getNode)
                .flatMap(Node::edges)
                .filter(x -> graphNodeIdsFromLevel.contains(x.getNode0().getId()) ||
                        graphNodeIdsFromLevel.contains(x.getNode1().getId()))
                .filter(CustomPredicates.distinctByKey(Element::getId))
                .forEach(x -> addInteriorEdge(viewGraph, x.getId(), x.getNode0().getId(), x.getNode1().getId()));

        // regular edges
        graphNodes.stream()
                .map(NodeBase::getNode)
                .flatMap(Node::edges)
                .filter(x -> graphNodeIdsFromLevel.contains(x.getNode0().getId()) &&
                        graphNodeIdsFromLevel.contains(x.getNode1().getId()))
                .filter(CustomPredicates.distinctByKey(Element::getId))
                .forEach(x -> addRegularEdge(viewGraph, x.getId(), x.getNode0().getId(), x.getNode1().getId()));

        viewGraph.setAttribute("ui.quality");
        viewGraph.setAttribute("ui.antialias");

        return viewGraph;
    }

    private void addRegularEdge(Graph graph, String edgeId, String node0Id, String node1Id) {
        Edge edge = graph.addEdge(edgeId, node0Id, node1Id);
        edge.setAttribute(Attributes.CLASS, "regular");
    }

    private void addInteriorEdge(Graph graph, String edgeId, String node0Id, String node1Id) {
        Edge edge = graph.addEdge(edgeId, node0Id, node1Id);
        edge.setAttribute(Attributes.CLASS, "interior");
    }

    private void addGraphNode(Graph graph, GraphNode graphNode) {
        Node node = graph.addNode(graphNode.getId());
        node.setAttribute(Attributes.FROZEN_LAYOUT);
        node.setAttribute(Attributes.LABEL, graphNode.getSymbol());
        node.setAttribute(Attributes.XY,
                graphNode.getCoordinates().getX(),
                graphNode.getCoordinates().getY());
        node.setAttribute(Attributes.CLASS, "exterior");
    }

    private void addInteriorNode(Graph graph, InteriorNode interiorNode) {
        Node node = graph.addNode(interiorNode.getId());
        node.setAttribute(Attributes.FROZEN_LAYOUT);
        node.setAttribute(Attributes.LABEL, interiorNode.getSymbol());

        List<Point2d> siblingsLocations = interiorNode.getSiblings()
                .map(GraphNode::getCoordinates)
                .collect(Collectors.toList());

        Point2d interiorCoordinates = Point2d.center(siblingsLocations).orElse(new Point2d(0, 0));

        node.setAttribute(Attributes.XY,
                interiorCoordinates.getX(),
                interiorCoordinates.getY());

        Optional<String> interiorNodeIconClass = getInteriorNodeIconClass(interiorNode);
        interiorNodeIconClass.ifPresent(s -> node.setAttribute(Attributes.ICON, s));

        node.setAttribute(Attributes.CLASS, "interior");
    }

    private Optional<String> getInteriorNodeIconClass(InteriorNode interiorNode) {
        boolean extendsDown = interiorNode.getChildrenIds().findAny().isPresent();
        boolean extendsUp = interiorNode.getParentId().isPresent();

        if (extendsDown && extendsUp) {
            return Optional.of(ResourceLoader.getUpDownIconPath().toString());
        }

        if (extendsDown) {
            return Optional.of(ResourceLoader.getDownIconPath().toString());
        }

        if (extendsUp) {
            return Optional.of(ResourceLoader.getUpIconPath().toString());
        }

        return Optional.empty();
    }
}
