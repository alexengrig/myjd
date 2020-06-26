package dev.alexengrig.myjdi.ui;

import dev.alexengrig.myjdi.connect.MyConnector;
import dev.alexengrig.myjdi.connect.MyConnectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class JConnectDialog extends JDialog {
    public JConnectDialog(Window owner, Consumer<MyConnector> connectorConsumer) {
        super(owner);
        setTitle("My Debugger | Connect");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Dimension size = new Dimension(100, 25);

        JTextField hostField = new JTextField("localhost");
        hostField.setPreferredSize(size);

        JTextField portField = new JTextField("8000");
        portField.setPreferredSize(size);

        Button connectButton = new Button("Connect");
        connectButton.setPreferredSize(size);
        connectButton.addActionListener(ignore -> {
            String hostname = hostField.getText();
            int port = Integer.parseInt(portField.getText());
            MyConnector connector = MyConnectors.socket(hostname, port);
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
        JLabel hostLabel = new JLabel("host:");
        grid.setConstraints(hostLabel, constraints);
        panel.add(hostLabel);

        constraints.gridx = 1;
        grid.setConstraints(hostField, constraints);
        panel.add(hostField);

        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel portLabel = new JLabel("port:");
        grid.setConstraints(portLabel, constraints);
        panel.add(portLabel);

        constraints.gridx = 1;
        grid.setConstraints(portField, constraints);
        panel.add(portField);

        constraints.gridx = 1;
        constraints.gridy = 2;
        grid.setConstraints(connectButton, constraints);
        panel.add(connectButton);

        add(panel);
        pack();
        setVisible(true);
    }
}
