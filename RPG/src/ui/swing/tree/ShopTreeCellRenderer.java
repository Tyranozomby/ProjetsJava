package ui.swing.tree;

import game.engine.effects.Effect;
import game.engine.entities.Player;
import ui.swing.tree.nodes.*;

import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import java.awt.Component;
import java.awt.Font;

public class ShopTreeCellRenderer extends DefaultTreeCellRenderer {

    private final Border border = BorderFactory.createEmptyBorder(4, 4, 4, 4);

    private final Player player;

    private final boolean fightMode;

    public ShopTreeCellRenderer(Player player, boolean fightMode) {
        this.player = player;
        this.fightMode = fightMode;
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        setIcon(null);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBorder(border);

        if (fightMode || !canBeBought((TreeNode) value)) {
            setEnabled(false);
        }

        if (value instanceof ShopWeaponListNode) {
            setText("Weapons");
            setEnabled(!fightMode);
        } else if (value instanceof ShopConsumableListNode) {
            setText("Consumables");
            setEnabled(!fightMode);
        } else if (value instanceof ItemNode itemNode) {
            setText(itemNode.getItem().getName() + " | " + itemNode.getItem().getDescription() + " (" + itemNode.getItem().getPrice() + " gold)");
        } else if (value instanceof EffectNode effectNode) {
            Effect effect = effectNode.getEffect().getEffect();
            setText(effect.getName() + " | " + effect.getDescription() + " (" + effectNode.getEffect().getChance() * 100 + "%)");
        } else if (value instanceof AttackNode attackNode) {
            setText(attackNode.getAttack().getName() + " (" + attackNode.getAttack().getPhysicalDamage() + " : " + attackNode.getAttack().getMagicalDamage() + ")");
        } else {
            setText("Unknown node");
        }

        return this;
    }

    /**
     * Checks if the item can be bought by the player.
     * If it's not a nodeItem, it will check the parent.
     *
     * @param node the node to check
     * @return true if the item can be bought, false otherwise
     */
    private boolean canBeBought(TreeNode node) {
        if (node instanceof ItemNode itemNode) {
            return itemNode.getItem().getPrice() <= player.getGold();
        } else if (node.getParent() != null) {
            return canBeBought(node.getParent());
        } else {
            return false;
        }
    }
}
