package dev.alexengrig.myjdi;

import javax.swing.*;
import java.awt.*;

public class MyDebuggerGUI extends JFrame {
    public MyDebuggerGUI() {
        init();
        setTitle("MYJDI");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyDebuggerGUI();
    }

    private void init() {
        BorderLayout layout = new BorderLayout();
        JPanel contentPane = new JPanel(layout);
        JTabbedPane fileTabPane = new JTabbedPane(SwingConstants.TOP);
        fileTabPane.addTab("File 1", new JPanel());
        fileTabPane.addTab("File 2", new JPanel());
        contentPane.add(fileTabPane);
        setContentPane(contentPane);
    }
}
