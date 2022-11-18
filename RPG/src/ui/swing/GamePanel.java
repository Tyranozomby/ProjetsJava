package ui.swing;

import game.engine.Game;
import ui.SwingUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final SwingUI swingUI;

    private final Game game;

    private MapGrid mapGrid;

    private PlayerPanel playerPanel;

    private InventoryPanel inventoryPanel;

    private ShopPanel shopPanel;

    public GamePanel(SwingUI swingUI, Game game) {
        this.swingUI = swingUI;
        this.game = game;
        initComponents();
    }

    private void initComponents() {
        mapGrid = new MapGrid(game.getMap());
        playerPanel = new PlayerPanel();
        inventoryPanel = new InventoryPanel();
        shopPanel = new ShopPanel();

        //======== this ========
        setLayout(new GridBagLayout());

        //---- mapGrid ----
        this.add(mapGrid, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        //---- playerPanel ----
        this.add(playerPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        //======== TabPanel ========

        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.addTab("Inventory", inventoryPanel);
        tabPanel.addTab("Shop", shopPanel);

        this.add(tabPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));


        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyCode());
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getPoint());
            }
        });
    }
}
