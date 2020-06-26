package dev.alexengrig.myjdi.ui;

import dev.alexengrig.myjdi.connect.MyConnector;
import dev.alexengrig.myjdi.connect.MyConnectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class JRunDialog extends JDialog {
    public JRunDialog(Window owner, Consumer<MyConnector> connectorConsumer) {
        super(owner);
        setTitle("My Debugger | Run");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Dimension size = new Dimension(100, 25);

        JTextField classpathFiled = new JTextField("./example/build/classes/java/main");
        classpathFiled.setPreferredSize(new Dimension(250, 25));

        JTextField mainClassField = new JTextField("dev.alexengrig.example.Main");
        mainClassField.setPreferredSize(new Dimension(360, 25));

        Button runButton = new Button("Run");
        runButton.setPreferredSize(size);
        runButton.addActionListener(ignore -> {
            String classpath = classpathFiled.getText();
            String mainClass = mainClassField.getText();
            MyConnector connector = MyConnectors.commandLine(classpath, mainClass);
            setVisible(false);
            dispose();
            connectorConsumer.accept(connector);
        });

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagLayout grid = new GridBagLayout();
        panel.setLayout(grid);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel hostLabel = new JLabel("classpath:");
        grid.setConstraints(hostLabel, constraints);
        panel.add(hostLabel);

        constraints.gridx = 1;
        grid.setConstraints(classpathFiled, constraints);
        panel.add(classpathFiled);

        JFileChooser chooser = new JFileChooser(".");
        chooser.setMultiSelectionEnabled(false);

        constraints.gridx = 2;
        JButton classpathButton = new JButton("Browse...");
        classpathButton.setPreferredSize(size);
        classpathButton.addActionListener(ignore -> {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                classpathFiled.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });
        grid.setConstraints(classpathButton, constraints);
        panel.add(classpathButton);

        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel portLabel = new JLabel("main:");
        grid.setConstraints(portLabel, constraints);
        panel.add(portLabel);

        constraints.gridx = 1;
        constraints.gridwidth = 2;
        grid.setConstraints(mainClassField, constraints);
        panel.add(mainClassField);

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 0;
        grid.setConstraints(runButton, constraints);
        panel.add(runButton);

        add(panel);
        pack();
        setVisible(true);
    }
}
