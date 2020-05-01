package dev.alexengrig.myjdi.ui;

import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.VMStartException;
import dev.alexengrig.myjdi.MyDebugger;
import dev.alexengrig.myjdi.util.MyConnector;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DebugGUI extends JFrame {
    private final ExecutorService background = Executors.newCachedThreadPool();

    private final List<String> fileLines;
    private JButton resumeButton;
    private JStackFramePane stackFramePane;

    public DebugGUI() throws HeadlessException {
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

    public static void main(String[] args) {
        new DebugGUI();
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
        resumeButton = new JButton("Resume");
        stackFramePane = new JStackFramePane();
        pane.add(resumeButton);
        pane.add(stackFramePane);
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
                        e.virtualMachine().suspend();
                    } catch (IncompatibleThreadStateException | InterruptedException | InvocationTargetException ex) {
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
