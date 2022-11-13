package game.engine;

import game.*;
import game.engine.entities.Monster;
import game.engine.entities.Player;
import game.engine.fight.Fight;
import game.engine.items.Item;
import game.engine.items.Weapon;
import game.engine.items.consumables.Consumable;
import game.world.Direction;
import game.world.Map;
import game.world.tile.EndTile;
import game.world.tile.Tile;
import utils.Random;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Game {

    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    public static UICallbacks UI_CALLBACKS = null;

    private final Map map;

    private final Player player;

    public Game(UICallbacks uiCallbacks, PlayerClass playerClass, String playerName) throws IOException {
        UI_CALLBACKS = uiCallbacks;

        try {
            map = new Map("RPG/maps");
        } catch (IOException e) {
            throw new IOException("Failed to load map", e);
        }

        int physicalDamage, magicalDamage, physicalDefense, magicalDefense, speed, maxHealth;
        Weapon defaultWeapon;

        switch (playerClass) {
            case WARRIOR -> {
                physicalDamage = 2;
                magicalDamage = 1;
                physicalDefense = 2;
                magicalDefense = 0;
                speed = 5;
                maxHealth = 15;
                defaultWeapon = Weaponry.FORK.forge();
            }
            case MAGE -> {
                physicalDamage = 1;
                magicalDamage = 2;
                physicalDefense = 0;
                magicalDefense = 2;
                speed = 5;
                maxHealth = 12;
                defaultWeapon = Weaponry.SPOON.forge();
            }
            case THIEF -> {
                physicalDamage = 1;
                magicalDamage = 1;
                physicalDefense = 1;
                magicalDefense = 1;
                speed = 10;
                maxHealth = 10;
                defaultWeapon = Weaponry.KNIFE.forge();
            }
            case TANK -> {
                physicalDamage = 1;
                magicalDamage = 1;
                physicalDefense = 3;
                magicalDefense = 1;
                speed = 3;
                maxHealth = 20;
                defaultWeapon = Weaponry.PLATE.forge();
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + playerClass);
        }

        this.player = new Player(playerName, physicalDamage, magicalDamage, physicalDefense, magicalDefense, speed, maxHealth);
        player.getInventory().addItem(defaultWeapon);
        player.equipWeapon(defaultWeapon);
        player.giveItem(Brewery.HEALTH_POTION.brew(1));
    }

    public void start() {
        LOGGER.info("Starting game");
        UI_CALLBACKS.onGameStart(map);

        boolean end = false;
        boolean victory = false;
        while (!end) {
            Action action = UI_CALLBACKS.getAction(player);

            switch (action) {
                case MOVE -> {
                    Direction direction = UI_CALLBACKS.getDirection(map);
                    if (direction != null) {
                        Tile tile = map.move(direction);
                        if (tile instanceof EndTile) {
                            if (!map.goNextLevel()) {
                                end = true;
                                victory = true;
                            }
                        } else {
                            tile.onStep(this, player);
                        }
                    }
                }
                case SHOP -> {
                    Item item = UI_CALLBACKS.onShopOpen(player, map.getShopItems());
                    if (item != null) {
                        if (player.buy(item)) {
                            UI_CALLBACKS.onItemBuy(item);
                            map.getShopItems().remove(item);
                        } else
                            UI_CALLBACKS.onItemBuyFail(item);
                    }
                }
                case INVENTORY -> {
                    Item item = UI_CALLBACKS.onInventoryOpen(player);
                    if (item != null) {
                        if (item instanceof Weapon) {
                            player.equipWeapon((Weapon) item);
                        } else /*if (item instanceof Consumable)*/ {
                            ((Consumable) item).consume(List.of(player), player);
                        }
                    }
                }
                case PROFILE -> UI_CALLBACKS.onProfileOpen(player);
            }

            if (!player.isAlive()) {
                end = true;
                victory = false;
            }
        }

        LOGGER.info("Game ended");
        UI_CALLBACKS.onGameEnd(victory);
    }


    public int getCurrentLevel() {
        return map.getLevelValue();
    }

    public void battle(Monster randomMonster) {
        new Fight(player, randomMonster).doFight();
        handleFightEnd(List.of(randomMonster));
    }

    public void battle(Monster randomMonster1, Monster randomMonster2) {
        new Fight(player, randomMonster1, randomMonster2).doFight();
        handleFightEnd(List.of(randomMonster1, randomMonster2));
    }

    private void handleFightEnd(List<Monster> monsters) {
        if (player.isAlive()) {
            UI_CALLBACKS.onFightVictory();
            player.addGold(Random.getRandomInt(1, 2) * map.getLevelValue() + 2 * monsters.stream().mapToInt(Monster::getLevel).sum() + Random.getRandomInt(0, 2));
        } else {
            UI_CALLBACKS.onFightDefeat();
        }
    }

    public Map getMap() {
        return map;
    }
}
