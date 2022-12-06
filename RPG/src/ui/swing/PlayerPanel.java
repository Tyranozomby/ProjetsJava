package ui.swing;

import game.Stats;
import game.engine.effects.Effect;
import game.engine.entities.Player;
import ui.swing.tree.nodes.AttackNode;
import ui.swing.tree.nodes.EffectNode;
import ui.swing.tree.nodes.ItemNode;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class PlayerPanel extends JPanel {

    private final Player player;

    public PlayerPanel(Player player) {
        this.player = player;
        initComponent();
    }

    private void initComponent() {
        setLayout(new GridBagLayout());

//        JLabel name = new JLabel(player.getName());
//        name.setFont(new Font("Segoe UI", Font.PLAIN, 24));
//        this.add(name);

        // Health
        JLabel health = new JLabel("Health: " + player.getHealth() + "/" + player.getStat(Stats.MAX_HEALTH));
        health.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        health.setForeground(new Color(225, 90, 135));
        this.add(health, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));

        // Gold
        JLabel gold = new JLabel("Gold: " + player.getGold());
        gold.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        gold.setForeground(new Color(255, 220, 0));
        this.add(gold, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));

        // Physical damage
        JLabel physicalDamage = new JLabel("Physical damage: " + player.getStat(Stats.PHYSICAL_DAMAGE));
        physicalDamage.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        physicalDamage.setForeground(new Color(235, 115, 15));
        this.add(physicalDamage, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));

        // Magical damage
        JLabel magicalDamage = new JLabel("Magical damage: " + player.getStat(Stats.MAGICAL_DAMAGE));
        magicalDamage.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        magicalDamage.setForeground(new Color(235, 115, 15));
        this.add(magicalDamage, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));

        // Physical defense
        JLabel physicalDefense = new JLabel("Physical defense: " + player.getStat(Stats.PHYSICAL_DEFENSE));
        physicalDefense.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        physicalDefense.setForeground(new Color(115, 170, 235));
        this.add(physicalDefense, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));

        // Magical defense
        JLabel magicalDefense = new JLabel("Magical defense: " + player.getStat(Stats.MAGICAL_DEFENSE));
        magicalDefense.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        magicalDefense.setForeground(new Color(115, 170, 235));
        this.add(magicalDefense, new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));

        // Speed
        JLabel speed = new JLabel("Speed: " + player.getStat(Stats.SPEED));
        speed.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        speed.setForeground(new Color(35, 205, 140));
        this.add(speed, new GridBagConstraints(0, 3, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets(5, 5, 5, 5), 0, 0));

        // Weapon
        JTree treeWeapon = new JTree();
        treeWeapon.setCellRenderer(new ProfileCellRenderer());
        treeWeapon.setModel(new DefaultTreeModel(new ItemNode(player.getWeapon(), null)));
        treeWeapon.setShowsRootHandles(true);

        // Open all
        for (int i = 0; i < treeWeapon.getRowCount(); i++) {
            treeWeapon.expandRow(i);
        }

        JScrollPane treePane = new JScrollPane(treeWeapon);
        this.add(treePane, new GridBagConstraints(0, 4, 2, 2, 0, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    }

    public void reload() {
        removeAll();
        initComponent();
        revalidate();
        repaint();
    }

    private static class ProfileCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

            setIcon(null);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
//            setBorder(border);

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
}
