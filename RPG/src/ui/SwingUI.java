package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import game.Action;
import game.PlayerClass;
import game.Stats;
import game.UICallbacks;
import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.effects.interfaces.InstantEffect;
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

import javax.swing.*;
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

    public void startGame(PlayerClass playerClass, String name) {
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
        String message = victory ? bundle.getString("SwingUI.victory") : bundle.getString("SwingUI.defeat");

        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        JOptionPane.showMessageDialog(frame, label, "Game Over", JOptionPane.WARNING_MESSAGE);

        frame.dispose();
        System.exit(0);
    }

    @Override
    public void onEndTileReached(boolean isEnd) {
        if (!isEnd) {
            JOptionPane.showMessageDialog(frame, bundle.getString("SwingUI.endTileReached"));
        }
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
        gamePanel.getFightPanel().reload();
    }

    @Override
    public void onTurnEnd(Fight fight) {
        // wait for a second if it's a monster turn
        if (fight.getPlaying() instanceof Monster) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gamePanel.getFightPanel().reload();
    }

    @Override
    public void onPlayerDeath(Player player) {

    }

    @Override
    public void onMonsterDeath(Monster monster) {
        JOptionPane.showMessageDialog(frame, bundle.getString("SwingUI.monsterDeath") + " " + monster.getName() + " (lvl " + monster.getLevel() + ")");
    }

    @Override
    public void onFightVictory() {

    }

    @Override
    public void onFightDefeat() {
        JOptionPane.showMessageDialog(frame, bundle.getString("SwingUI.fightLost.text"), bundle.getString("SwingUI.fightLost.title"), JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void onStatUpgrade(Stats stat, int amount) {
        gamePanel.getPlayerPanel().reload();
    }

    @Override
    public void onAttack(Entity attacker, Entity receiver, Attack damage) {
        gamePanel.getFightPanel().setAttack(damage, attacker, receiver);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDamage(Entity receiver, int physicalDamage, int magicalDamage) {
        if (receiver instanceof Player) {
            vibrate(true);
        }
        gamePanel.getPlayerPanel().reload();
    }

    @Override
    public void onHeal(Entity receiver, int heal) {
        gamePanel.getFightPanel().reload();
        gamePanel.getPlayerPanel().reload();
    }

    @Override
    public void onEffectApplied(Entity receiver, Effect effect) {
        gamePanel.getFightPanel().setEffectSuccess(effect);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEffectFailedApply(Entity receiver, Effect effect) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEffectActivation(Effect effect) {
        System.out.println("Effect activation");
        if (!(effect instanceof InstantEffect)) {
            JOptionPane.showMessageDialog(frame, effect.getName() + " activated");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEffectEnd(Entity entity, Effect effect) {
        if (!(effect instanceof InstantEffect)) {
            JOptionPane.showMessageDialog(frame, effect.getName() + " ended on " + entity.getName());
            gamePanel.getFightPanel().reload();
        }
    }

    @Override
    public void onItemFound(Item item) {
        gamePanel.getInventoryPanel().reload();

        // Open popup to show item
        JOptionPane.showMessageDialog(frame, item.getName(), bundle.getString("SwingUI.itemFoundDialog.title"), JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void onItemRemoved(Item item) {
        gamePanel.getInventoryPanel().reload();
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
        System.out.println(game.getPlayer().getInventory());
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
        vibrate(false);
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
        vibrate(false);
    }

    @Override
    public void onMove(Map map, Tile tile) {
        gamePanel.getMapPanel().reload();
    }

    public void vibrate(boolean small) {
        int VIBRATION_LENGTH = small ? 2 : 5;
        int VIBRATION_VELOCITY = small ? 2 : 5;

        try {
            final int originalX = frame.getLocationOnScreen().x;
            final int originalY = frame.getLocationOnScreen().y;
            for (int i = 0; i < VIBRATION_LENGTH; i++) {
                Thread.sleep(10);
                frame.setLocation(originalX, originalY + VIBRATION_VELOCITY);
                Thread.sleep(10);
                frame.setLocation(originalX, originalY - VIBRATION_VELOCITY);
                Thread.sleep(10);
                frame.setLocation(originalX + VIBRATION_VELOCITY, originalY);
                Thread.sleep(10);
                frame.setLocation(originalX, originalY);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
