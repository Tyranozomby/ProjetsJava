package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import game.Action;
import game.PlayerClass;
import game.Stats;
import game.UICallbacks;
import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.entities.Entity;
import game.engine.entities.Monster;
import game.engine.entities.Player;
import game.engine.fight.Fight;
import game.engine.fight.actions.FightAction;
import game.engine.fight.attacks.Attack;
import game.engine.items.Item;
import game.engine.items.Weapon;
import game.world.Map;
import game.world.tile.Tile;
import ui.swing.GamePanel;
import ui.swing.MainMenu;
import utils.LogFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SwingUI implements UICallbacks {

    public static final Object actionLock = new Object();

    private final ResourceBundle bundle;

    private final JFrame frame;

    private GamePanel gamePanel;

    private Game game;

    public SwingUI() {
        bundle = ResourceBundle.getBundle("strings", Locale.getDefault());

        MainMenu mainMenu = new MainMenu(this);

        frame = new JFrame("RPG - Création du personnage");
        frame.setSize(1280, 720);
        frame.setContentPane(mainMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LogFormatter.initLogging();

        FlatDarculaLaf.setup();
        Locale.setDefault(Locale.ENGLISH);
        UIManager.getDefaults().put("Component.arrowType", "chevron");

        new SwingUI();
    }

    public void startGame(PlayerClass playerClass, String name) throws IOException {
        new Thread(() -> {
            try {
                game = new Game(this, playerClass, name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            game.start();
        }).start();
    }

    @Override
    public void onGameStart(Map map) {
        gamePanel = new GamePanel(game);
        frame.setContentPane(gamePanel);
        frame.setTitle(bundle.getString("SwingUI.title"));
        frame.revalidate();
        gamePanel.requestFocusInWindow();
    }

    @Override
    public void onGameEnd(boolean victory) {
        System.out.println("Game ended");
    }

    @Override
    public void onEndTileReached() {

    }

    @Override
    public void onNextLevel(int level) {
        gamePanel.getMapPanel().reload();
        gamePanel.getShopPanel().reload();
    }

    @Override
    public void onFightStart(Fight fight) {
        gamePanel.onFightStart(fight);
    }

    @Override
    public void onFightEnd() {
        gamePanel.onFightEnd();
    }

    @Override
    public void onTurnStart(Entity entity, int turn) {
        System.out.println("Turn " + turn + " - " + entity.getName());
        gamePanel.getFightPanel().reload();
    }

    @Override
    public void onTurnEnd(Fight fight) {
        System.out.println("Turn end");
        gamePanel.getFightPanel().reload();
    }

    @Override
    public void onPlayerDeath(Player player) {

    }

    @Override
    public void onMonsterDeath(Monster monster) {

    }

    @Override
    public void onFightVictory() {

    }

    @Override
    public void onFightDefeat() {

    }

    @Override
    public void onStatUpgrade(Stats stat, int amount) {

    }

    @Override
    public void onAttack(Entity attacker, Entity receiver, Attack damage) {
        System.out.println(attacker.getName() + " attacks " + receiver.getName() + " for " + damage.getPhysicalDamage() + " physical damage and " + damage.getMagicalDamage() + " magical damage");
        gamePanel.getFightPanel().reload();
    }

    @Override
    public void onDamage(Entity receiver, int physicalDamage, int magicalDamage) {
        System.out.println(receiver.getName() + " takes " + physicalDamage + " physical damage and " + magicalDamage + " magical damage");
        gamePanel.getFightPanel().reload();
        gamePanel.getPlayerPanel().reload();
    }

    @Override
    public void onHeal(Entity receiver, int heal) {
        gamePanel.getFightPanel().reload();
        gamePanel.getPlayerPanel().reload();
    }

    @Override
    public void onEffectApplied(Entity receiver, Effect effect) {

    }

    @Override
    public void onEffectFailedApply(Entity receiver, Effect effect) {

    }

    @Override
    public void onEffectActivation(Effect effect) {

    }

    @Override
    public void onEffectEnd(Entity entity, Effect effect) {

    }

    @Override
    public void onItemFound(Item item) {
        gamePanel.getInventoryPanel().reload();

        // Open popup to show item
        JOptionPane.showMessageDialog(frame, item.getName(), bundle.getString("SwingUI.itemFoundDialog.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onGoldFound(int amount) {
        gamePanel.getPlayerPanel().reload();
        gamePanel.getShopPanel().reload();

        // Open popup to show amount
        JOptionPane.showMessageDialog(frame, amount, bundle.getString("SwingUI.goldFoundDialog.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onConsumableUse(Item item) {
        gamePanel.getInventoryPanel().reload();
        gamePanel.getFightPanel().reload();
        gamePanel.getPlayerPanel().reload();
    }

    @Override
    public void onWeaponEquipped(Weapon weapon) {
        gamePanel.getPlayerPanel().reload();
        gamePanel.getInventoryPanel().reload();
    }

    @Override
    public void onWeaponUnequipped(Weapon weapon) {
        gamePanel.getPlayerPanel().reload();
        gamePanel.getInventoryPanel().reload();
    }

    @Override
    public void onItemBuy(Item item) {
        gamePanel.getShopPanel().reload();
        gamePanel.getInventoryPanel().reload();
        gamePanel.getPlayerPanel().reload();

        // Open popup to show item
        JOptionPane.showMessageDialog(frame, item.getName(), bundle.getString("SwingUI.itemBoughtDialog.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onItemBuyFail(Item item) {
        // TODO Shake the screen
        System.out.println("Not enough gold");
    }

    @Override
    public FightAction getFightAction(Player player, Fight fight) {
        synchronized (actionLock) {
            while (gamePanel.getFightAction() == null) {
                try {
                    actionLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        FightAction fightAction = gamePanel.getFightAction();
        gamePanel.setFightAction(null);
        return fightAction;
    }

    @Override
    public Action getAction(Game game) {
        synchronized (actionLock) {
            while (gamePanel.getAction() == null) {
                try {
                    actionLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Action action = gamePanel.getAction();
        gamePanel.setAction(null);
        return action;
    }

    @Override
    public void onInvalidMove() {
        // TODO Shake the screen
        System.out.println("Invalid move");
    }

    @Override
    public void onMove(Map map, Tile tile) {
        gamePanel.getMapPanel().reload();
    }
}
