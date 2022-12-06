package ui.swing;

import game.Action;
import game.engine.entities.Player;
import game.engine.items.Item;
import ui.swing.tree.ShopTreeCellRenderer;
import ui.swing.tree.nodes.ItemNode;
import ui.swing.tree.nodes.ShopListNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ShopPanel extends JPanel {

    private final ArrayList<Item> items;

    private final GamePanel gamePanel;

    private final Player player;

    private boolean fightMode = false;

    public ShopPanel(ArrayList<Item> shopItems, GamePanel gamePanel, Player player) {
        this.items = shopItems;
        this.gamePanel = gamePanel;
        this.player = player;
        initComponents();
    }

    private void initComponents() {
        //======== this ========
        setLayout(new BorderLayout());

        //---- tree ----
        JTree tree = new JTree();
        tree.setCellRenderer(new ShopTreeCellRenderer(player, fightMode));
        tree.setModel(new DefaultTreeModel(new ShopListNode(items)));

        if (!fightMode) {
            tree.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        int row = tree.getClosestRowForLocation(e.getX(), e.getY());
                        tree.setSelectionRow(row);
                        TreePath path = tree.getSelectionPath();
                        if (path != null && path.getLastPathComponent() instanceof ItemNode node) {
                            ShopMenu treePopup = new ShopMenu(node, gamePanel);
                            treePopup.show(e.getComponent(), e.getX(), e.getY());
                        }
                    }
                }
            });
        }

        // Default select first node (0 is root)
        tree.setSelectionRow(1);

        // Get the first node and open its children
        Object root = tree.getModel().getRoot();
        for (int i = 0; i < tree.getModel().getChildCount(root); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) tree.getModel().getChild(root, i);
            tree.expandPath(new TreePath(child.getPath()));
        }

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

    static class ShopMenu extends JPopupMenu {
        public ShopMenu(ItemNode node, GamePanel gamePanel) {
            Item item = node.getItem();
            JMenuItem equip = new JMenuItem("Buy");
            equip.addActionListener(e -> {
                Action action = new Action(Action.Shop.BUY, item);
                gamePanel.setAction(action);
            });
            add(equip);
        }
    }
}
