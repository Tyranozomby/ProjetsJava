package game.engine.items.consumables;

import game.engine.Game;
import game.engine.entities.Entity;
import game.engine.entities.Player;
import game.engine.items.Item;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public class Consumable extends Item {

    public static final Logger LOGGER = Logger.getLogger(Consumable.class.getName());

    private final BiConsumer<List<Entity>, Entity> consumableEffect;

    private final boolean usableOutOfFight;

    public Consumable(String name, String description, int price, BiConsumer<List<Entity>, Entity> consumableEffect, boolean usableOutOfFight) {
        super(name, description, price);
        this.consumableEffect = consumableEffect;
        this.usableOutOfFight = usableOutOfFight;
    }

    public void consume(List<Entity> targets, Entity from) {
        if (from instanceof Player player && player.getInventory().contains(this)) {
            player.getInventory().removeItem(this);
        } else {
            LOGGER.warning(from + " does not have this item in their inventory!");
        }

        Game.UI_CALLBACKS.onConsumableUse(this);
        consumableEffect.accept(targets, from);
    }

    public boolean isUsableOutOfFight() {
        return usableOutOfFight;
    }

    @Override
    public String toString() {
        return "Consumable{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", usableOutOfFight=" + usableOutOfFight +
                ", price=" + price +
                '}';
    }
}
