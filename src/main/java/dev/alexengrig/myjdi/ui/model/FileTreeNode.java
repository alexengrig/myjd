package dev.alexengrig.myjdi.ui.model;

import io.github.alexengrig.lambdax.ChainX;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileTreeNode implements TreeNode {
    private final String name;
    private final boolean isFile;
    private final FileTreeNode parent;
    private final List<FileTreeNode> children;

    public FileTreeNode(File file, FileFilter fileFilter) {
        this(null, file, fileFilter);
    }

    public FileTreeNode(FileTreeNode parent, File file, FileFilter fileFilter) {
        if (!Objects.requireNonNull(file, "File must not be null").exists()) {
            throw new IllegalArgumentException("File doesn't exist: " + file);
        }
        this.name = file.getName();
        this.isFile = file.isFile();
        this.parent = parent;
        this.children = ChainX.of(file)
                .map(File::listFiles)
                .stream()
                .flatMap(Arrays::stream)
                .filter(child -> child.isDirectory() || (fileFilter != null && fileFilter.accept(child)))
                .map(child -> new FileTreeNode(this, child, fileFilter))
                .collect(Collectors.toList());
    }

    @Override
    public FileTreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public FileTreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return getIndex((FileTreeNode) node);
    }

    public int getIndex(FileTreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return !isFile;
    }

    @Override
    public boolean isLeaf() {
        return isFile;
    }

    @Override
    public Enumeration<FileTreeNode> children() {
        return new FileTreeNodeEnumeration(children);
    }

    @Override
    public String toString() {
        return name;
    }

    public static class FileTreeNodeEnumeration implements Enumeration<FileTreeNode> {
        private final List<FileTreeNode> nodes;
        private int index = 0;

        public FileTreeNodeEnumeration(List<FileTreeNode> nodes) {
            this.nodes = nodes;
        }

        @Override
        public boolean hasMoreElements() {
            return index < nodes.size();
        }

        @Override
        public FileTreeNode nextElement() {
            return nodes.get(index++);
        }
    }
}
