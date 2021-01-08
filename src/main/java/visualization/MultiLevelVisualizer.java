package visualization;

import model.TetrahedralGraph;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MultiLevelVisualizer implements Visualizer {
    private final TabbedLevelViewFactory tabbedLevelViewFactory = new TabbedLevelViewFactory();

    @Override
    public void displayGraph(TetrahedralGraph graph) {
        displayGraph(graph, "Tetrahedral mesh");
    }

    public void displayGraph(TetrahedralGraph graph, String title) {
        Object lock = new Object();

        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame(title);
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JTabbedPane tabbedPane = tabbedLevelViewFactory.createTabbedLevelView(graph);

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
