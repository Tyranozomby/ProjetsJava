package ui.swing.tree.nodes;

import game.engine.fight.attacks.Attack;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import java.util.List;

public class AttackNode implements TreeNode {

    private final Attack attack;

    private final List<EffectNode> children;

    private final TreeNode parent;

    public AttackNode(Attack attack, TreeNode parent) {
        this.attack = attack;
        this.parent = parent;
        this.children = attack.getEffects().stream().map(effect -> new EffectNode(effect, this)).toList();
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
    public Enumeration<? extends TreeNode> children() {
        return null;
    }

    public Attack getAttack() {
        return attack;
    }
}
