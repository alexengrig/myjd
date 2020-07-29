package dev.alexengrig.myjdi.ui.model;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.logging.Logger;

public class FileTreeModel implements TreeModel {
    private static final Logger log = Logger.getLogger(FileTreeModel.class.getName());

    private final FileTreeNode root;

    public FileTreeModel(FileTreeNode root) {
        this.root = root;
    }

    @Override
    public FileTreeNode getRoot() {
        return root;
    }

    @Override
    public FileTreeNode getChild(Object parent, int index) {
        return getChild((FileTreeNode) parent, index);
    }

    public FileTreeNode getChild(FileTreeNode parent, int index) {
        return parent.getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return getChildCount((FileTreeNode) parent);
    }

    public int getChildCount(FileTreeNode parent) {
        return parent.getChildCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        return isLeaf((FileTreeNode) node);
    }

    public boolean isLeaf(FileTreeNode node) {
        return node.isLeaf();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return getIndexOfChild((FileTreeNode) parent, (FileTreeNode) child);
    }

    public int getIndexOfChild(FileTreeNode parent, FileTreeNode child) {
        return parent.getIndex(child);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        valueForPathChanged(path, (FileTreeNode) newValue);
    }

    public void valueForPathChanged(TreePath path, FileTreeNode newValue) {
        //TODO: Complete this method();
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        //TODO: Complete this method
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        //TODO: Complete this method
    }
}
