package ui.swing.tree.nodes;

import game.engine.items.Weapon;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ShopWeaponListNode extends DefaultMutableTreeNode implements TreeNode {

    private final ArrayList<ItemNode> children;

    private final TreeNode parent;

    public ShopWeaponListNode(List<Weapon> weapons, TreeNode parent) {
        this.parent = parent;
        children = new ArrayList<>(weapons.stream().map(weapon -> new ItemNode(weapon, this)).toList());
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
