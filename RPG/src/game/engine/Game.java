package game.engine;

import game.*;
import game.engine.entities.Monster;
import game.engine.entities.Player;
import game.engine.fight.Fight;
import game.engine.items.Item;
import game.engine.items.Weapon;
import game.engine.items.consumables.Consumable;
import game.world.Map;
import game.world.Position;
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

        UI_CALLBACKS = uiCallbacks;
    }

    public void start() {
        LOGGER.info("Starting game");
        UI_CALLBACKS.onGameStart(map);

        boolean end = false;
        boolean victory = false;
        while (!end) {
            Action action = UI_CALLBACKS.getAction(this);

            if (action == null)
                break;

            if (action.type() instanceof Action.Move moveActionType) {
                Position currentPosition = new Position(map.getPosition().getX(), map.getPosition().getY());
                Tile tile = map.move(moveActionType);

                if (currentPosition.getX() != map.getPosition().getX() || currentPosition.getY() != map.getPosition().getY()) {
                    UI_CALLBACKS.onMove(map, tile);
                    if (tile instanceof EndTile) {
                        if (!map.goNextLevel()) {
                            end = true;
                            victory = true;
                        }
                    } else {
                        tile.onStep(this, player);
                    }
                } else {
                    UI_CALLBACKS.onInvalidMove();
                }
            } else if (action.type() instanceof Action.Shop shopActionType) {
                Item item = ((Item) action.data());
                if (shopActionType == Action.Shop.BUY) {
                    if (player.buy(item)) {
                        UI_CALLBACKS.onItemBuy(item);
                    } else {
                        UI_CALLBACKS.onItemBuyFail(item);
                    }
                }
            } else if (action.type() instanceof Action.Inventory inventoryActionType) {
                if (inventoryActionType == Action.Inventory.EQUIP) {
                    Weapon weapon = ((Weapon) action.data());
                    player.equipWeapon(weapon);
                } else if (inventoryActionType == Action.Inventory.CONSUME) {
                    Consumable consumable = ((Consumable) action.data());
                    consumable.consume(List.of(player), player);
                }
            }

            if (!player.isAlive()) {
                end = true;
                victory = false;
            }
        }

        LOGGER.info("Game ended");
        UI_CALLBACKS.onGameEnd(victory);
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

    public Player getPlayer() {
        return player;
    }
}
