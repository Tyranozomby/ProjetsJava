package ui.swing.tree.nodes;

import game.engine.entities.Inventory;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class InventoryListNode extends DefaultMutableTreeNode implements TreeNode {

    private final ArrayList<TreeNode> children;

    public InventoryListNode(Inventory inventory) {
        children = new ArrayList<>();
        children.addAll(inventory.getAll().stream().map(item -> new ItemNode(item, this)).toList());
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return null;
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration<TreeNode> children() {
        return Collections.enumeration(children);
    }
}
