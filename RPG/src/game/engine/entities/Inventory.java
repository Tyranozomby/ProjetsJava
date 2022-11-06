package game.engine.entities;

import game.engine.items.Item;
import game.engine.items.Weapon;
import game.engine.items.consumables.Consumable;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Inventory {

    public static final Logger LOGGER = Logger.getLogger(Inventory.class.getName());

    private final ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ArrayList<Weapon> getWeapons() {
        ArrayList<Weapon> weapons = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Weapon) {
                weapons.add((Weapon) item);
            }
        }
        return weapons;
    }

    public ArrayList<Consumable> getConsumables(boolean outOfFight) {
        ArrayList<Consumable> consumables = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Consumable consumable && (!outOfFight || consumable.isUsableOutOfFight())) {
                consumables.add((Consumable) item);
            }
        }
        return consumables;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "Weapons: " + getWeapons() +
                "\nConsumables: " + getConsumables(true) +
                "}";
    }

    public boolean contains(Item item) {
        return items.contains(item);
    }
}
