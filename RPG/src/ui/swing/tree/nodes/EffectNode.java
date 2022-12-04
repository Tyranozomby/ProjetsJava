package ui.swing.tree.nodes;

import game.engine.effects.RandomChanceEffect;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class EffectNode implements TreeNode {

    private final RandomChanceEffect effect;

    private final TreeNode parent;

    public EffectNode(RandomChanceEffect effect, TreeNode parent) {
        this.effect = effect;
        this.parent = parent;
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

    public RandomChanceEffect getEffect() {
        return effect;
    }
}
