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
import game.world.Direction;
import game.world.Map;
import ui.swing.GamePanel;
import ui.swing.MainMenu;
import utils.LogFormatter;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class SwingUI implements UICallbacks {

    private final JFrame frame;

    private GamePanel gamePanel;

    private Game game;

    public SwingUI() {
        MainMenu mainMenu = new MainMenu(this);

        frame = new JFrame("RPG - Création du personnage");
        frame.setSize(1080, 720);
        frame.setContentPane(mainMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LogFormatter.initLogging();

        FlatDarculaLaf.setup();
        Locale.setDefault(Locale.ENGLISH);

        new SwingUI();
    }

    public void startGame(PlayerClass playerClass, String name) throws IOException {
        game = new Game(this, playerClass, name);
        game.start();
    }

    @Override
    public void onGameStart(Map map) {
        gamePanel = new GamePanel(this, game);
        frame.setContentPane(gamePanel);
        frame.setTitle("RPG - Jeu en cours");
        frame.revalidate();
        gamePanel.requestFocusInWindow();
    }

    @Override
    public void onGameEnd(boolean victory) {

    }

    @Override
    public void onEndTileReached() {

    }

    @Override
    public void onNextLevel(int level) {

    }

    @Override
    public void onFightStart(Fight fight) {

    }

    @Override
    public void onFightEnd() {

    }

    @Override
    public void onTurnStart(Entity entity, int turn) {

    }

    @Override
    public void onTurnEnd(Fight fight) {

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

    }

    @Override
    public void onDamage(Entity receiver, int physicalDamage, int magicalDamage) {

    }

    @Override
    public void onHeal(Entity receiver, int heal) {

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

    }

    @Override
    public void onGoldFound(int amount) {

    }

    @Override
    public void onConsumableUse(Item item) {

    }

    @Override
    public Item onInventoryOpen(Player inventory) {
        return null;
    }

    @Override
    public void onWeaponEquipped(Weapon weapon) {

    }

    @Override
    public void onWeaponUnequipped(Weapon weapon) {

    }

    @Override
    public Item onShopOpen(Player player, List<Item> shopItems) {
        return null;
    }

    @Override
    public void onItemBuy(Item item) {

    }

    @Override
    public void onItemBuyFail(Item item) {

    }

    @Override
    public void onProfileOpen(Player player) {

    }

    @Override
    public FightAction getFightAction(Player player, Fight fight) {
        return null;
    }

    @Override
    public Direction getDirection(Map map) {
        return null;
    }

    @Override
    public Action getAction(Player player) {
        return null;
    }
}
