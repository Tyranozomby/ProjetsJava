package ui.swing;

import game.Action;
import game.engine.entities.Inventory;
import game.engine.fight.actions.ChangeWeaponFightAction;
import game.engine.fight.actions.FightAction;
import game.engine.fight.actions.consumables.AutoTargetConsumableFightAction;
import game.engine.fight.actions.consumables.SingleTargetConsumableFightAction;
import game.engine.items.Item;
import game.engine.items.Weapon;
import game.engine.items.consumables.AutoTargetConsumable;
import game.engine.items.consumables.Consumable;
import game.engine.items.consumables.SingleTargetConsumable;
import ui.swing.tree.InventoryTreeCellRenderer;
import ui.swing.tree.nodes.InventoryListNode;
import ui.swing.tree.nodes.ItemNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InventoryPanel extends JPanel {

    private final Inventory inventory;

    private final GamePanel gamePanel;

    private boolean fightMode = false;

    public InventoryPanel(Inventory inventory, GamePanel gamePanel) {
        this.inventory = inventory;
        this.gamePanel = gamePanel;
        initComponents();
    }

    private void initComponents() {
        //======== this ========
        setLayout(new BorderLayout());

        //---- tree ----
        JTree tree = new JTree();
        tree.setCellRenderer(new InventoryTreeCellRenderer());
        tree.setModel(new DefaultTreeModel(new InventoryListNode(inventory)));

        tree.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tree.getClosestRowForLocation(e.getX(), e.getY());
                    tree.setSelectionRow(row);
                    TreePath path = tree.getSelectionPath();
                    if (path != null && path.getLastPathComponent() instanceof ItemNode node) {
                        InventoryMenu treePopup = new InventoryMenu(node, gamePanel, fightMode);
                        treePopup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        // Default select first node (0 is root)
        tree.setSelectionRow(1);

        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);

        add(tree, BorderLayout.CENTER);
    }

    public void reload() {
        removeAll();
        initComponents();
        revalidate();
    }

    public void toggleFightMode() {
        fightMode = !fightMode;
        reload();
    }

    private static class InventoryMenu extends JPopupMenu {

        public InventoryMenu(ItemNode node, GamePanel gamePanel, boolean fightMode) {
            Item item = node.getItem();
            if (item instanceof Weapon weapon) {
                JMenuItem equip = new JMenuItem("Equip");
                equip.addActionListener(e -> {
                    if (!fightMode) {
                        Action action = new Action(Action.Inventory.EQUIP, weapon);
                        gamePanel.setAction(action);
                    } else {
                        FightAction action = new ChangeWeaponFightAction(weapon);
                        gamePanel.setFightAction(action);
                    }
                });
                add(equip);
            } else if (item instanceof Consumable consumable) {
                JMenuItem use = new JMenuItem("Use");
                use.addActionListener(e -> {
                    if (!fightMode) {
                        Action action = new Action(Action.Inventory.CONSUME, consumable);
                        gamePanel.setAction(action);
                    } else {
                        FightAction action;

                        if (consumable instanceof SingleTargetConsumable singleTargetConsumable) {
                            action = new SingleTargetConsumableFightAction(singleTargetConsumable, gamePanel.getFightPanel().getTarget());
                        } else {
                            action = new AutoTargetConsumableFightAction((AutoTargetConsumable) consumable);
                        }
                        gamePanel.setFightAction(action);
                    }
                });
                add(use);
            }
        }
    }
}
