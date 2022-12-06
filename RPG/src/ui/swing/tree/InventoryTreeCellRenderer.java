package ui.swing.tree;

import game.engine.effects.Effect;
import ui.swing.tree.nodes.AttackNode;
import ui.swing.tree.nodes.EffectNode;
import ui.swing.tree.nodes.ItemNode;

import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Component;
import java.awt.Font;

public class InventoryTreeCellRenderer extends DefaultTreeCellRenderer {

    private final Border border = BorderFactory.createEmptyBorder(4, 4, 4, 4);

    @Override
    @SuppressWarnings("DuplicatedCode")
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        setIcon(null);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBorder(border);

        if (value instanceof ItemNode itemNode) {
            setText(itemNode.getItem().getName() + " | " + itemNode.getItem().getDescription());
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
}
