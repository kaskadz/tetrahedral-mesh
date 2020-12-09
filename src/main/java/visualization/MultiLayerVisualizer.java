package visualization;

import common.Attributes;
import model.GraphNode;
import model.InteriorNode;
import model.Point2d;
import model.TetrahedralGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MultiLayerVisualizer implements Visualizer {

    @Override
    public void displayGraph(TetrahedralGraph graph) {
        JFrame mainFrame = new JFrame("Tetrahedral mesh");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();

        for (int i = 0; i <= graph.getMaxLevel(); i++) {
            JFrame frame = new JFrame();

            Graph viewGraph = generateGraphView(graph, i);

            Viewer viewer = new SwingViewer(viewGraph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
            View defaultView = viewer.addDefaultView(false);
            frame.add((JPanel) defaultView);
            tabbedPane.addTab(String.format("Level %d", i), frame.getContentPane());
        }

        mainFrame.getContentPane().add(tabbedPane);
        mainFrame.setSize(800, 800);
        mainFrame.setVisible(true);
    }

    private Graph generateGraphView(TetrahedralGraph graph, int level) {
        Collection<GraphNode> graphNodes = graph.getGraphNodesByLevel(level);
        Collection<InteriorNode> interiorNodes = graph.getInteriorNodesByLevel(level);
        Graph viewGraph = new SingleGraph(String.format("Level %d", level));

        graphNodes.forEach(x -> addGraphNode(viewGraph, x));
        interiorNodes.forEach(x -> addInteriorNode(viewGraph, x));

        return viewGraph;
    }

    private void addGraphNode(Graph graph, GraphNode graphNode) {
        Node node = graph.addNode(graphNode.getId());
        node.setAttribute(Attributes.FROZEN_LAYOUT);
        node.setAttribute(Attributes.LABEL, graphNode.getSymbol());
        node.setAttribute(Attributes.XY,
                graphNode.getCoordinates().getX(),
                graphNode.getCoordinates().getY());
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
    }
}
