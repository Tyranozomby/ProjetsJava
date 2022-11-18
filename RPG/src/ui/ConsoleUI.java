package ui;

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
import game.engine.fight.actions.ChangeWeaponFightAction;
import game.engine.fight.actions.FightAction;
import game.engine.fight.actions.attacks.AttackFightAction;
import game.engine.fight.actions.attacks.AutoTargetAttackFightAction;
import game.engine.fight.actions.attacks.SingleTargetAttackFightAction;
import game.engine.fight.actions.consumables.AutoTargetConsumableFightAction;
import game.engine.fight.actions.consumables.SingleTargetConsumableFightAction;
import game.engine.fight.actions.consumables.UseConsumableFightAction;
import game.engine.fight.attacks.Attack;
import game.engine.fight.attacks.AutoTargetAttack;
import game.engine.fight.attacks.SingleTargetAttack;
import game.engine.items.Item;
import game.engine.items.Weapon;
import game.engine.items.consumables.AutoTargetConsumable;
import game.engine.items.consumables.Consumable;
import game.engine.items.consumables.SingleTargetConsumable;
import game.world.Direction;
import game.world.Level;
import game.world.Map;
import game.world.Position;
import game.world.tile.Tile;
import game.world.tile.TileManager;
import game.world.tile.WallTile;
import utils.LogFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements UICallbacks {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LogFormatter.initLogging();

        try {
            new Game(new ConsoleUI(),getPlayerClass(), getPlayerName()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PlayerClass getPlayerClass() {
        while (true) {
            System.out.println("Choose a class :");
            for (int i = 0; i < PlayerClass.values().length; i++) {
                System.out.println((i + 1) + ". " + PlayerClass.values()[i]);
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                choice = 0;
            }

            if (choice > 0 && choice <= PlayerClass.values().length) {
                return PlayerClass.values()[choice - 1];
            } else {
                System.err.println("Invalid choice");
            }
        }
    }

    public static String getPlayerName() {
        while (true) {
            System.out.println("Choose a name :");
            String name = scanner.nextLine();
            name = name.trim();
            if (name.length() > 0) {
                return name;
            } else {
                System.err.println("Invalid name");
            }
        }
    }

    @Override
    public void onGameStart(Map map) {
        System.out.println("Game started : ");
        System.out.println(map.getCurrentLevel());
        System.out.println(getLevelString(map.getCurrentLevel(), map.getPosition()));
    }

    @Override
    public void onGameEnd(boolean victory) {
        if (victory) {
            System.out.println("\u001B[32mYou won the game !\u001B[0m");
        } else {
            System.out.println("\u001B[31mYou lost the game ! Git gud !\u001B[0m");
        }
    }

    @Override
    public void onEndTileReached() {
        System.out.println("End tile reached");
    }

    @Override
    public void onNextLevel(int level) {
        System.out.println("Next level : " + level);
    }

    @Override
    public void onFightStart(Fight fight) {
        System.out.println("Fight started : ");
        System.out.println(fight.getPlayer() + " (" + fight.getPlayer().getHealth() + "hp, " + fight.getPlayer().getStat(Stats.SPEED) + "speed)");
        for (Monster monster : fight.getMonsters()) {
            System.out.println(monster + " (" + monster.getHealth() + "hp, " + monster.getStat(Stats.SPEED) + "speed)");
        }
    }

    @Override
    public void onFightEnd() {
        System.out.println("Fight ended");
    }

    @Override
    public void onTurnStart(Entity entity, int turn) {
        System.out.println("Turn " + turn + " started for " + entity);
    }

    @Override
    public void onTurnEnd(Fight fight) {
        System.out.println("Turn ended");
        System.out.println(fight.getPlayer() + " (" + fight.getPlayer().getHealth() + "hp)");
        for (Monster monster : fight.getMonsters()) {
            System.out.println(monster + " (" + monster.getHealth() + "hp)");
        }
    }

    @Override
    public void onPlayerDeath(Player player) {
        System.out.println("Player died : " + player);
    }

    @Override
    public void onMonsterDeath(Monster monster) {
        System.out.println("Monster died : " + monster);
    }

    @Override
    public void onFightVictory() {
        System.out.println("Fight won");
    }

    @Override
    public void onFightDefeat() {
        System.out.println("Fight lost");
    }

    @Override
    public void onStatUpgrade(Stats stat, int amount) {
        System.out.println("Stat upgraded : " + stat + " by " + amount);
    }

    @Override
    public void onAttack(Entity attacker, Entity receiver, Attack damage) {
        System.out.println(attacker + " attacked " + receiver + " with " + damage);
    }

    @Override
    public void onDamage(Entity receiver, int physicalDamage, int magicalDamage) {
        System.out.println(receiver + " took " + physicalDamage + " physical damage and " + magicalDamage + " magical damage");
        System.out.println(receiver + " has " + receiver.getHealth() + " health left");
    }

    @Override
    public void onHeal(Entity receiver, int heal) {
        System.out.println(receiver + " healed " + heal + " hp (now : " + receiver.getHealth() + "hp)");
    }

    @Override
    public void onEffectApplied(Entity receiver, Effect effect) {
        System.out.println(receiver + " got effect " + effect);
    }

    @Override
    public void onEffectFailedApply(Entity receiver, Effect effect) {
        System.out.println(receiver + " failed to apply effect " + effect);
    }

    @Override
    public void onEffectActivation(Effect effect) {
        System.out.println(effect + " activated");
    }

    @Override
    public void onEffectEnd(Entity entity, Effect effect) {
        System.out.println(effect + " ended");
    }

    @Override
    public void onItemFound(Item item) {
        System.out.println("Found item : " + item);
    }

    @Override
    public void onGoldFound(int amount) {
        System.out.println("Found " + amount + " gold");
    }

    @Override
    public void onConsumableUse(Item item) {
        System.out.println("Used " + item);
    }

    @Override
    public Item onInventoryOpen(Player player) {
        System.out.println("Inventory opened : ");
        System.out.println("Weapons : " + player.getInventory().getWeapons());
        System.out.println("Consumables : " + player.getInventory().getConsumables(true));

        while (true) {
            System.out.println("What do you want to do ?");
            System.out.println("0. Cancel");
            System.out.println("1. Use item");
            System.out.println("2. Equip weapon");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Invalid choice");
                continue;
            }

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                return getConsumable(player.getInventory().getConsumables(true));
            } else if (choice == 2) {
                return getWeapon(player.getInventory().getWeapons());
            } else {
                System.err.println("Invalid choice");
            }
        }
        return null;
    }

    private Weapon getWeapon(ArrayList<Weapon> weapons) {
        while (true) {
            System.out.println("Choose a weapon : ");
            System.out.println("0. Cancel");
            for (int i = 0; i < weapons.size(); i++) {
                System.out.println((i + 1) + ". " + weapons.get(i));
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Invalid choice");
                continue;
            }

            if (choice == 0) return null;

            if (choice > 0 && choice <= weapons.size()) {
                return weapons.get(choice - 1);
            } else {
                System.err.println("Invalid choice");
            }
        }
    }

    private Consumable getConsumable(ArrayList<Consumable> consumables) {
        while (true) {
            System.out.println("Choose a consumable : ");
            System.out.println("0. Cancel");
            for (int i = 0; i < consumables.size(); i++) {
                System.out.println((i + 1) + ". " + consumables.get(i));
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Invalid choice");
                continue;
            }

            if (choice == 0) return null;

            if (choice > 0 && choice <= consumables.size()) {
                return consumables.get(choice - 1);
            } else {
                System.err.println("Invalid choice");
            }
        }
    }


    @Override
    public void onWeaponEquipped(Weapon weapon) {
        System.out.println("Equipped weapon " + weapon);
    }

    @Override
    public void onWeaponUnequipped(Weapon weapon) {
        System.out.println("Unequipped weapon " + weapon);
    }

    @Override
    public Item onShopOpen(Player player, List<Item> shopItems) {
        System.out.println("Shop opened");
        while (true) {
            System.out.println("Player has " + player.getGold() + " gold");
            System.out.println("Shop has " + shopItems.size() + " items");
            System.out.println("0. Close shop");
            for (int i = 0; i < shopItems.size(); i++) {
                Item item = shopItems.get(i);
                System.out.println((i + 1) + ". " + item);
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Invalid choice");
                continue;
            }

            if (choice == 0) return null;

            if (choice > 0 && choice <= shopItems.size()) {
                return shopItems.get(choice - 1);
            } else {
                System.err.println("Invalid choice");
            }
        }
    }

    @Override
    public void onItemBuy(Item item) {
        System.out.println("Successfully bought item " + item);
    }

    @Override
    public void onItemBuyFail(Item item) {
        System.err.println("Failed to buy " + item + "\nNot enough gold");
    }

    @Override
    public void onProfileOpen(Player player) {
        System.out.println(player.getName() + "'s profile : ");
        System.out.println(player.getHealth() + "/" + player.getStat(Stats.MAX_HEALTH) + " hp");
        System.out.println(player.getGold() + " gold");
        System.out.println("Current weapon : " + player.getWeapon());
        System.out.println(player.getInventory().getWeapons().size() + " weapons");
        System.out.println(player.getInventory().getConsumables(true).size() + " consumables");
        player.getStats().keySet().stream().filter(k -> k != Stats.MAX_HEALTH).forEach(stat -> System.out.println("Player has " + player.getStats().get(stat).toString().toLowerCase().replaceAll("_", " ") + " " + stat));
    }

    @Override
    public FightAction getFightAction(Player player, Fight fight) {
        while (true) {
            System.out.println("Choose an action :");
            System.out.println("1. Attack");
            System.out.println("2. Change Weapon");
            System.out.println("3. Use Item");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                choice = 0;
            }

            switch (choice) {
                case 1 -> {
                    AttackFightAction attack = getAttackFightAction(player, fight);
                    if (attack != null) return attack;
                }
                case 2 -> {
                    ChangeWeaponFightAction changeWeapon = getChangeWeaponFightAction(player);
                    if (changeWeapon != null) return changeWeapon;
                }
                case 3 -> {
                    UseConsumableFightAction useConsumable = getUseConsumableFightAction(player, fight);
                    if (useConsumable != null) return useConsumable;
                }
                default -> System.err.println("Invalid choice");
            }
        }
    }

    @Override
    public Direction getDirection(Map map) {
        Level level = map.getCurrentLevel();
        Position pos = map.getPosition();

        String levelString = getLevelString(level, pos);

        while (true) {
            System.out.println(levelString);
            System.out.println("Choose a direction :");
            System.out.println("0. Cancel");
            System.out.println("1. Up");
            System.out.println("2. Down");
            System.out.println("3. Left");
            System.out.println("4. Right");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                choice = 0;
            }

            if (choice == 0) return null;

            ArrayList<ArrayList<Tile>> tiles = level.getTiles();

            switch (choice) {
                case 1 -> {
                    if (pos.getY() > 0 && !(tiles.get(pos.getY() - 1).get(pos.getX()) instanceof WallTile))
                        return Direction.UP;
                    else System.err.println("You can't go this way");
                }
                case 2 -> {
                    if (pos.getY() < tiles.size() - 1 && !(tiles.get(pos.getY() + 1).get(pos.getX()) instanceof WallTile))
                        return Direction.DOWN;
                    else System.err.println("You can't go this way");
                }
                case 3 -> {
                    if (pos.getX() > 0 && !(tiles.get(pos.getY()).get(pos.getX() - 1) instanceof WallTile))
                        return Direction.LEFT;
                    else System.err.println("You can't go this way");
                }
                case 4 -> {
                    if (pos.getX() < tiles.get(0).size() - 1 && !(tiles.get(pos.getY()).get(pos.getX() + 1) instanceof WallTile))
                        return Direction.RIGHT;
                    else System.err.println("You can't go this way");
                }
                default -> System.err.println("Invalid choice");
            }
        }
    }

    private String getLevelString(Level level, Position pos) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<ArrayList<Tile>> tiles = level.getTiles();
        for (int i = 0; i < tiles.size(); i++) {
            ArrayList<Tile> tileRow = tiles.get(i);
            for (int j = 0; j < tileRow.size(); j++) {
                Tile tile = tileRow.get(j);
                if (i == pos.getY() && j == pos.getX()) {
                    stringBuilder.append("\u001B[31m⬤\u001B[0m");
                } else {
                    stringBuilder.append(TileManager.getTileChar(tile));
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public Action getAction(Player player) {
        while (true) {
            System.out.println("Choose an action :");
            for (int i = 0; i < Action.values().length; i++) {
                System.out.println((i + 1) + ". " + switch (Action.values()[i]) {
                    case MOVE -> "Move";
                    case INVENTORY -> "Open Inventory";
                    case SHOP -> "Open Shop";
                    case PROFILE -> "Show Profile";
                });
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                choice = 0;
            }

            if (choice > 0 && choice <= Action.values().length) {
                return Action.values()[choice - 1];
            } else {
                System.err.println("Invalid choice");
            }
        }
    }

    private AttackFightAction getAttackFightAction(Player player, Fight fight) {
        while (true) {
            List<Attack> attacks = player.getWeapon().getAttacks();

            System.out.println("Choose an attack :");
            System.out.println("0. Cancel");
            for (int i = 0; i < attacks.size(); i++) {
                System.out.println((i + 1) + ". " + attacks.get(i));
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Invalid choice");
                continue;
            }

            if (choice == 0) return null;

            if (choice > 0 && choice <= attacks.size()) {
                Attack attack = attacks.get(choice - 1);
                if (attack instanceof SingleTargetAttack) {
                    Entity target = getTarget(fight, false);
                    if (target != null) return new SingleTargetAttackFightAction((SingleTargetAttack) attack, target);
                } else if (attack instanceof AutoTargetAttack)
                    return new AutoTargetAttackFightAction((AutoTargetAttack) attack);
            } else {
                System.err.println("Invalid choice");
            }
        }
    }

    private ChangeWeaponFightAction getChangeWeaponFightAction(Player player) {
        while (true) {
            ArrayList<Weapon> weapons = player.getInventory().getWeapons();

            System.out.println("Choose a weapon :");
            System.out.println("0. Cancel");
            for (int i = 0; i < weapons.size(); i++) {
                System.out.println((i + 1) + ". " + weapons.get(i));
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Invalid choice");
                continue;
            }

            if (choice == 0) return null;

            if (choice > 0 && choice <= weapons.size()) {
                return new ChangeWeaponFightAction(weapons.get(choice - 1));
            } else {
                System.err.println("Invalid choice");
            }
        }
    }

    private UseConsumableFightAction getUseConsumableFightAction(Player player, Fight fight) {
        while (true) {
            ArrayList<Consumable> consumables = player.getInventory().getConsumables(false);

            System.out.println("Choose a consommable :");
            System.out.println("0. Cancel");
            for (int i = 0; i < consumables.size(); i++) {
                System.out.println((i + 1) + ". " + consumables.get(i));
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Invalid choice");
                continue;
            }

            if (choice == 0) return null;

            if (choice > 0 && choice <= consumables.size()) {
                Consumable consumable = consumables.get(choice - 1);
                if (consumable instanceof SingleTargetConsumable) {
                    Entity target = getTarget(fight, true);
                    if (target != null)
                        return new SingleTargetConsumableFightAction((SingleTargetConsumable) consumable, target);
                } else if (consumable instanceof AutoTargetConsumable)
                    return new AutoTargetConsumableFightAction((AutoTargetConsumable) consumable);
            } else {
                System.err.println("Invalid choice");
            }
        }
    }

    private Entity getTarget(Fight fight, boolean andPlayer) {
        while (true) {
            List<Monster> monsters = fight.getMonsters();

            System.out.println("Choose a target :");
            System.out.println("0. Cancel");
            for (int i = 0; i < monsters.size(); i++) {
                System.out.println((i + 1) + ". " + monsters.get(i));
            }
            if (andPlayer) System.out.println((monsters.size() + 1) + ". Myself");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.err.println("Invalid choice");
                continue;
            }

            if (choice == 0) return null;

            if (choice == monsters.size() + 1 && andPlayer) return fight.getPlayer();

            if (choice > 0 && choice <= monsters.size()) {
                return monsters.get(choice - 1);
            } else {
                System.err.println("Invalid choice");
            }
        }
    }
}