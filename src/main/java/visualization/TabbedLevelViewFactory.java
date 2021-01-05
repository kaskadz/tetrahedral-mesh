package visualization;

import model.TetrahedralGraph;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;

public class TabbedLevelViewFactory {
    private final GraphLevelViewFactory graphLevelViewFactory = new GraphLevelViewFactory();

    public JTabbedPane createTabbedLevelView(TetrahedralGraph graph) {
        JTabbedPane tabbedPane = new JTabbedPane();

        for (int i = 0; i <= graph.getMaxLevel(); i++) {
            JFrame frame = new JFrame();

            Graph viewGraph = graphLevelViewFactory.createGraphView(graph, i);

            Viewer viewer = new SwingViewer(viewGraph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
            View defaultView = viewer.addDefaultView(false);
            defaultView.getCamera().setViewCenter(0, 0, 0);
            defaultView.getCamera().setGraphViewport(-1, -1, 1, 1);
            frame.add((JPanel) defaultView);
            tabbedPane.addTab(String.format("Level %d", i), frame.getContentPane());
        }

        return tabbedPane;
    }
}
