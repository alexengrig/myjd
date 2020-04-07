package dev.alexengrig.myjdi.ui;

import javax.swing.*;
import java.awt.*;

public class DebugGUI extends JFrame {
    public DebugGUI() throws HeadlessException {
        setTitle("My Debugger");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    public static void main(String[] args) {
        new DebugGUI();
    }

    private void initComponents() {
        JPanel rootPane = new JPanel();
        rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.X_AXIS));
        final JPanel debugPane = createDebugPane();
        rootPane.add(debugPane);
        final JPanel filePane = createFilePane();
        rootPane.add(filePane);
        setContentPane(rootPane);
    }

    private JPanel createDebugPane() {
        final JPanel panel = new JPanel();
        panel.add(new JLabel("Debug pane"));
        return panel;
    }

    private JPanel createFilePane() {
        final JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLoweredBevelBorder());
        panel.add(new JLabel("File pane"));
        return panel;
    }
}
