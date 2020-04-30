package dev.alexengrig.myjdi.ui;

import javax.swing.*;
import java.util.List;

public class StackFramePane extends JPanel {
    public StackFramePane() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void rerender() {
        revalidate();
        repaint();
    }

    public void add(List<JComponent> components) {
        for (JComponent component : components) {
            add(component);
        }
    }
}
