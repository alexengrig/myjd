package dev.alexengrig.myjdi.ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class JVariablePane extends JPanel {
    public JVariablePane() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void updateValues(List<String> values) {
        removeAll();
        for (String value : values) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(value);
            node.add(new DefaultMutableTreeNode("Sub-value"));
            JTree tree = new JTree(node);
            tree.collapseRow(0);
            add(tree);
        }
        revalidate();
        repaint();
    }
}
