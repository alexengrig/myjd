package dev.alexengrig.myjdi.ui;

import dev.alexengrig.myjdi.ui.model.FileTreeModel;
import dev.alexengrig.myjdi.ui.model.FileTreeNode;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;

public class JFileTree extends JTree {
    public JFileTree(File file, FileFilter fileFilter) {
        super(new FileTreeModel(new FileTreeNode(file, fileFilter)));
    }
}
