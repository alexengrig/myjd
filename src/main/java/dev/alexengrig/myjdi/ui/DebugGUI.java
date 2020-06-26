package dev.alexengrig.myjdi.ui;

import com.sun.jdi.*;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.MyDebugger;
import dev.alexengrig.myjdi.connect.MyConnector;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DebugGUI extends JFrame {
    private final ExecutorService background = Executors.newCachedThreadPool();

    private final List<String> fileLines;
    private JButton resumeButton;
    private JStackFramePane stackFramePane;
    private JVariablePane variablePane;

    public DebugGUI() {
        fileLines = Arrays.asList("First line", "Second line", "Third line", "Fourth line", "Fifth line",
                "Sixth line", "Seventh line", "Eighth line", "Ninth line", "Tenth line", "Eleventh line");
        setTitle("My Debugger");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        initMenuBar();
        initComponents();
        setVisible(true);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu newMenu = new JMenu("New");

        JMenuItem runMenuItem = new JMenuItem("Run");
        runMenuItem.addActionListener(ignore -> new JRunDialog(this, this::doDebug));
        newMenu.add(runMenuItem);

        JMenuItem connectMenuItem = new JMenuItem("Connect");
        connectMenuItem.addActionListener(ignore -> new JConnectDialog(this, this::doDebug));
        newMenu.add(connectMenuItem);

        menuBar.add(newMenu);
        setJMenuBar(menuBar);
    }

    private void initComponents() {
        final JPanel filePane = createFilePane();
        final JComponent debugPane = createDebugPane();
        JSplitPane rootPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filePane, debugPane);
        setContentPane(rootPane);
    }

    private JComponent createDebugPane() {
        JPanel rootPane = new JPanel(new BorderLayout());
        JPanel actionPane = new JPanel();
        resumeButton = new JButton("Resume");
        actionPane.add(resumeButton);
        rootPane.add(resumeButton, BorderLayout.NORTH);

        stackFramePane = new JStackFramePane();
        JPanel panel = new JPanel();
        variablePane = new JVariablePane();
        panel.add(variablePane);

        JSplitPane debugPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, stackFramePane, panel);
        rootPane.add(debugPane, BorderLayout.CENTER);
        return rootPane;
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

    private void doDebug(MyConnector connector) {
        background.execute(() -> {
            try {
                VirtualMachine vm = connector.connect();
                MyDebugger debugger = new MyDebugger(vm);
                resumeButton.addActionListener(ac -> vm.resume());
                debugger.addBreakpointHandler(e -> {
                    try {
                        ThreadReference thread = e.thread();
                        List<StackFrame> frames = thread.frames();
                        List<String> frameValues = new ArrayList<>();
                        for (StackFrame frame : frames) {
                            frameValues.add(frame.location().toString());
                        }
                        SwingUtilities.invokeAndWait(() -> stackFramePane.updateValues(frameValues));
                        StackFrame frame = frames.get(0);
                        Map<LocalVariable, Value> values = frame.getValues(frame.visibleVariables());
                        ArrayList<String> variables = new ArrayList<>();
                        variables.add("this=" + frame.thisObject());
                        for (Map.Entry<LocalVariable, Value> entry : values.entrySet()) {
                            variables.add(entry.getKey().name() + "=" + entry.getValue());
                        }
                        SwingUtilities.invokeAndWait(() -> variablePane.updateValues(variables));
                        e.virtualMachine().suspend();
                    } catch (IncompatibleThreadStateException | InterruptedException | InvocationTargetException | AbsentInformationException ex) {
                        ex.printStackTrace();
                    }
                });
                debugger.addBreakpoint("dev.alexengrig.example.Main", 9);
                debugger.addBreakpoint("dev.alexengrig.example.Main", 14);
                debugger.addBreakpoint("dev.alexengrig.example.Main", 20);
                debugger.run();
                System.out.println("Finished.");
            } catch (IllegalConnectorArgumentsException | IOException | VMStartException ex) {
                ex.printStackTrace();
            }
        });
    }
}
