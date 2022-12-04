package ui.swing.tree.nodes;

import game.engine.items.Item;
import game.engine.items.Weapon;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.List;

public class ItemNode extends DefaultMutableTreeNode implements TreeNode {

    private final Item item;

    private final List<AttackNode> children;

    private final TreeNode parent;

    public ItemNode(Item item, TreeNode parent) {
        this.item = item;
        this.parent = parent;

        if (item instanceof Weapon) {
            children = ((Weapon) item).getAttacks().stream().map(attack -> new AttackNode(attack, this)).toList();
        } else {
            children = List.of();
        }
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
        return children.isEmpty();
    }

    @Override
    public Enumeration<TreeNode> children() {
        return null;
    }

    public Item getItem() {
        return item;
    }
}
