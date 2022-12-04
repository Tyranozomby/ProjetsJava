package ui.swing;

import game.Action;
import game.engine.Game;
import game.engine.entities.Player;
import game.engine.fight.Fight;
import game.engine.fight.actions.FightAction;
import ui.SwingUI;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Game game;

    private MapGrid mapGrid;

    private PlayerPanel playerPanel;

    private InventoryPanel inventoryPanel;

    private ShopPanel shopPanel;

    private FightPanel fightPanel;

    private Action action = null;

    private FightAction fightAction = null;

    public GamePanel(Game game) {
        this.game = game;
        initComponents();
    }

    private void initComponents() {
        Player player = game.getPlayer();
        fightPanel = new FightPanel(this);
        mapGrid = new MapGrid(game.getMap(), this);
        playerPanel = new PlayerPanel(player);
        inventoryPanel = new InventoryPanel(player.getInventory(), this);
        shopPanel = new ShopPanel(game.getMap().getShopItems(), this, player);

        //======== this ========
        setBorder(new EmptyBorder(50, 0, 50, 50));
        setLayout(new GridBagLayout());
        ((GridBagLayout) getLayout()).columnWeights = new double[]{1.0, 0.6};
//        ((GridBagLayout) getLayout()).columnWidths = new int[]{600, 600};
        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.2, 1.0};

        setFocusable(true);
        // TODO move with arrow keys and zqsd or wasd
        // Need to constantly have focus
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                System.out.println(e.getKeyChar());
//            }
//        });

        //======== Map/Fight ========
        JPanel mapFightPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        mapFightPanel.setLayout(cardLayout);
        mapFightPanel.setBackground(Color.CYAN);

        mapFightPanel.add(mapGrid, "map");
        mapFightPanel.add(fightPanel, "fight");

        cardLayout.show(mapFightPanel, "map");

        JScrollPane mapFightScrollPane = new JScrollPane(mapFightPanel);
        mapFightScrollPane.setBorder(null);
        mapFightScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        mapFightScrollPane.getHorizontalScrollBar().setUnitIncrement(12);

        this.add(mapFightScrollPane, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        //---- playerPanel ----
        this.add(playerPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 20, 20));

        //======== TabPanel ========
        JTabbedPane tabPanel = new JTabbedPane();

        JScrollPane inventoryScrollPane = new JScrollPane(inventoryPanel);
        inventoryScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        tabPanel.addTab("Inventory", null, inventoryScrollPane);
        tabPanel.setMnemonicAt(0, 'I');

        JScrollPane shopScrollPane = new JScrollPane(shopPanel);
        shopScrollPane.getVerticalScrollBar().setUnitIncrement(12);
        tabPanel.addTab("Shop", null, shopScrollPane);
        tabPanel.setMnemonicAt(1, 'S');

        this.add(tabPanel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
        synchronized (SwingUI.actionLock) {
            SwingUI.actionLock.notify();
        }
    }

    public FightAction getFightAction() {
        return fightAction;
    }

    public void setFightAction(FightAction fightAction) {
        this.fightAction = fightAction;
        synchronized (SwingUI.actionLock) {
            SwingUI.actionLock.notify();
        }
    }

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public ShopPanel getShopPanel() {
        return shopPanel;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public MapGrid getMapPanel() {
        return mapGrid;
    }

    public FightPanel getFightPanel() {
        return fightPanel;
    }

    public void onFightStart(Fight fight) {
        fightPanel.setFight(fight);

        // Show FightPanel
        CardLayout cardLayout = (CardLayout) fightPanel.getParent().getLayout();
        cardLayout.show(mapGrid.getParent(), "fight");

        // Disable
        shopPanel.toggleFightMode();
        inventoryPanel.toggleFightMode();

        revalidate();
    }

    public void onFightEnd() {
        fightPanel.setFight(null);

        // Show MapPanel
        CardLayout cardLayout = (CardLayout) fightPanel.getParent().getLayout();
        cardLayout.show(mapGrid.getParent(), "map");

        // Enable
        shopPanel.toggleFightMode();
        inventoryPanel.toggleFightMode();

        revalidate();
    }
}
