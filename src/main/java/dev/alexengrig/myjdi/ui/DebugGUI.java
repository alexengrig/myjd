package dev.alexengrig.myjdi.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class DebugGUI extends JFrame {
    private List<String> fileLines;
    private JPanel fileLinesPane;

    public DebugGUI() throws HeadlessException {
        fileLines = Arrays.asList("First line", "Second line", "Third line", "Fourth line", "Fifth line",
                "Sixth line", "Seventh line", "Eighth line", "Ninth line", "Tenth line", "Eleventh line");
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
        final JPanel debugPane = new JPanel(new BorderLayout());
        debugPane.add(new JLabel("Debug pane"));
        return debugPane;
    }

    private JPanel createFilePane() {
        final JPanel rootPane = new JPanel(new BorderLayout());
        fileLinesPane = new JPanel();
        fileLinesPane.setLayout(new BoxLayout(fileLinesPane, BoxLayout.Y_AXIS));
        fileLinesPane.setBorder(BorderFactory.createLoweredBevelBorder());
        for (int i = 0, size = fileLines.size(); i < size; i++) {
            String fileLine = fileLines.get(i);
            final String text = String.format("%2d | %s", i + 1, fileLine);
            final JLabel label = new JLabel(text);
            label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            fileLinesPane.add(label);
        }
        rootPane.add(new JScrollPane(fileLinesPane));
        return rootPane;
    }

}
