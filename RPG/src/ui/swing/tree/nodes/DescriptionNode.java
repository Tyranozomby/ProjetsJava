package ui.swing.tree.nodes;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class DescriptionNode implements TreeNode {

    private final String description;

    private final TreeNode parent;

    public DescriptionNode(ItemNode itemNode) {
        this.description = itemNode.getItem().getDescription();
        this.parent = itemNode;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return null;
    }

    public String getDescription() {
        return description;
    }
}
