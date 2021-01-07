package visualization;

import model.TetrahedralGraph;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        displayAll("Tetrahedral mesh");
    }

    public void displayAll(String title) {
        if (!steps.isEmpty()) {
            Object lock = new Object();

            SwingUtilities.invokeLater(() -> {
                JFrame mainFrame = new JFrame(title);
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JTabbedPane tabbedPane = new JTabbedPane();

                int stepNo = 0;
                for (JTabbedPane stepTabbedPane : steps) {
                    stepTabbedPane.setTabPlacement(JTabbedPane.LEFT);
                    tabbedPane.addTab(String.format("Step: %d", stepNo++), stepTabbedPane);
                }

                mainFrame.getContentPane().add(tabbedPane);
                mainFrame.setSize(800, 800);
                mainFrame.setVisible(true);
                mainFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        synchronized (lock) {
                            lock.notify();
                        }
                    }
                });
            });

            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
