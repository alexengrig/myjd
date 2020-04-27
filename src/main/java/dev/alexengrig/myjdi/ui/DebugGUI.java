package dev.alexengrig.myjdi.ui;

import com.sun.jdi.*;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.MyDebugger;
import dev.alexengrig.myjdi.util.MyConnector;
import dev.alexengrig.myjdi.util.MyConnectors;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DebugGUI extends JFrame {
    private final ExecutorService background = Executors.newCachedThreadPool();

    private List<String> fileLines;
    private JTextField hostField;
    private JTextField portField;
    private JButton resumeButton;

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
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout(FlowLayout.CENTER));
        hostField = new JTextField("localhost");
        hostField.setPreferredSize(new Dimension(100, 25));
        pane.add(new JLabel("host: "));
        pane.add(hostField);
        portField = new JTextField("8000");
        portField.setPreferredSize(new Dimension(100, 25));
        pane.add(new JLabel("port: "));
        pane.add(portField);
        resumeButton = new JButton("Resume");
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(a -> background.execute(() -> {
            try {
                String hostname = hostField.getText();
                String port = portField.getText();
                System.out.println("Started.");
                MyConnector connector = MyConnectors.socket(hostname, Integer.parseInt(port));
                VirtualMachine vm = connector.connect();
                MyDebugger debugger = new MyDebugger(vm);
                resumeButton.addActionListener(ac -> vm.resume());
                debugger.addBreakpointHandler(e -> {
                    try {
                        StackFrame frame = e.thread().frame(0);
                        Map<LocalVariable, Value> values = frame.getValues(frame.visibleVariables());
                        StringJoiner joiner = new StringJoiner("\n");
                        for (Map.Entry<LocalVariable, Value> entry : values.entrySet()) {
                            joiner.add(entry.getKey().name() + " = " + entry.getValue());
                        }
                        System.out.println("Stack:\n" + joiner.toString());
                        e.virtualMachine().suspend();
                    } catch (IncompatibleThreadStateException | AbsentInformationException ex) {
                        ex.printStackTrace();
                    }
                });
                debugger.addBreakpoint("dev.alexengrig.example.Main", 9);
                debugger.addBreakpoint("dev.alexengrig.example.Main", 14);
                debugger.run();
                System.out.println("Finished.");
            } catch (IllegalConnectorArgumentsException | IOException | VMStartException ex) {
                ex.printStackTrace();
            }

        }));
        pane.add(connectButton);
        pane.add(resumeButton);
        debugPane.add(pane);
        return debugPane;
    }

    private JPanel createFilePane() {
        final JPanel rootPane = new JPanel(new BorderLayout());
        JPanel fileLinesPane = new JPanel();
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
