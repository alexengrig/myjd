package dev.alexengrig.myjdi;

import javax.swing.*;

public class MyDebuggerGUI extends JFrame {
    public MyDebuggerGUI() {
        setTitle("MYJDI");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyDebuggerGUI();
    }
}
