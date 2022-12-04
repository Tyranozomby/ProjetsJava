package ui.swing.tree.nodes;

import game.engine.items.consumables.Consumable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ShopConsumableListNode extends DefaultMutableTreeNode implements TreeNode {

    private final ArrayList<ItemNode> children;

    private final TreeNode parent;

    public ShopConsumableListNode(List<Consumable> consumables, TreeNode parent) {
        this.parent = parent;
        children = new ArrayList<>(consumables.stream().map(consumable -> new ItemNode(consumable, this)).toList());
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
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        //noinspection SuspiciousMethodCalls
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Enumeration<TreeNode> children() {
        return null;
    }
}
