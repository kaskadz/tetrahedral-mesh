package visualization;

import model.TetrahedralGraph;

import javax.swing.*;
import java.util.ArrayList;

public class MultiStepMultiLevelVisualizer implements MultiStepVisualizer {
    private final TabbedLevelViewFactory tabbedLevelViewFactory = new TabbedLevelViewFactory();
    private final ArrayList<JTabbedPane> steps = new ArrayList<>();

    @Override
    public void addStep(TetrahedralGraph graph) {
        JTabbedPane stepTabbedPane = tabbedLevelViewFactory.createTabbedLevelView(graph);
        steps.add(stepTabbedPane);
    }

    @Override
    public void displayAll() {
        if (!steps.isEmpty()) {
            JFrame mainFrame = new JFrame("Tetrahedral mesh");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTabbedPane tabbedPane = new JTabbedPane();

            int stepNo = 0;
            for (JTabbedPane stepTabbedPane : steps) {
                stepTabbedPane.setTabPlacement(JTabbedPane.LEFT);
                tabbedPane.addTab(String.format("Step: %d", stepNo++), stepTabbedPane);
            }

            mainFrame.getContentPane().add(tabbedPane);
            mainFrame.setSize(800, 800);
            mainFrame.setVisible(true);
        }
    }
}
