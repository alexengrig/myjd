package dev.alexengrig.myjdi;

import dev.alexengrig.myjdi.ui.JFileTree;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.logging.Logger;

public class MyDebuggerGUI extends JFrame {
    private static final Logger log = Logger.getLogger(MyDebuggerGUI.class.getName());

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
        JComponent projectTree = createFileTree("./example");
        contentPane.add(projectTree);
        setContentPane(contentPane);
    }

    private JTree createFileTree(String rootPath) {
        return new JFileTree(new File(rootPath), file -> file.getName().endsWith(".java"));
    }
}
