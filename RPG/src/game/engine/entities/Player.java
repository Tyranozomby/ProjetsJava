package game.engine.entities;

import game.Weaponry;
import game.engine.Game;
import game.engine.fight.Fight;
import game.engine.fight.actions.FightAction;
import game.engine.items.Item;
import game.engine.items.Weapon;

import java.util.logging.Logger;

public class Player extends Entity {

    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    private final Inventory inventory = new Inventory();

    private int gold = 0;

    public Player(String name, int physicalDamage, int magicalDamage, int physicalDefense, int magicalDefense, int speed, int maxHealth) {
        super(name, physicalDamage, magicalDamage, physicalDefense, magicalDefense, speed, maxHealth);
    }

    @Override
    public FightAction getFightAction(Fight fight) {
        return Game.UI_CALLBACKS.getFightAction(this, fight);
    }

    @Override
    public void equipWeapon(Weapon weapon) {
        if (!getWeapon().equals(Weaponry.NOTHING.forge())) {
            inventory.addItem(getWeapon());

            LOGGER.info("Unequipped " + getWeapon());
            if (Game.UI_CALLBACKS != null) {
                Game.UI_CALLBACKS.onWeaponUnequipped(getWeapon());
            }
        }

        if (weapon != null) {
            setWeapon(weapon);

            if (!this.getInventory().contains(weapon)) {
                LOGGER.warning(this + " equipped " + weapon + " that wasn't in his inventory.");
            }
            inventory.removeItem(weapon);

            LOGGER.info("Equipped " + weapon);
            if (Game.UI_CALLBACKS != null) {
                Game.UI_CALLBACKS.onWeaponEquipped(weapon);
            }
        }
    }

    public void addGold(int gold) {
        this.gold += gold;
        Game.UI_CALLBACKS.onGoldFound(gold);
    }

    public boolean buy(Item item) {
        int itemPrice = item.getPrice();
        if (gold >= itemPrice) {
            gold -= itemPrice;
            inventory.addItem(item);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player " + getName();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void giveItem(Item item) {
        inventory.addItem(item);

        if (Game.UI_CALLBACKS != null) {
            LOGGER.info("Received new item :" + item);
            Game.UI_CALLBACKS.onItemFound(item);
        }
    }

    public int getGold() {
        return gold;
    }
}
