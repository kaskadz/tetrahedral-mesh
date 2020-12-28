package visualization;

import model.TetrahedralGraph;

import javax.swing.*;

public class MultiLevelVisualizer implements Visualizer {
    private final TabbedLevelViewFactory tabbedLevelViewFactory = new TabbedLevelViewFactory();

    @Override
    public void displayGraph(TetrahedralGraph graph) {
        JFrame mainFrame = new JFrame("Tetrahedral mesh");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = tabbedLevelViewFactory.createTabbedLevelView(graph);

        mainFrame.getContentPane().add(tabbedPane);
        mainFrame.setSize(800, 800);
        mainFrame.setVisible(true);
    }
}
