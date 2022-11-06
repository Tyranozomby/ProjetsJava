package game.engine.items.consumables;

import game.engine.entities.Entity;

import java.util.List;
import java.util.function.BiConsumer;

public class SingleTargetConsumable extends Consumable {

    public SingleTargetConsumable(String name, String description, int price, BiConsumer<Entity, Entity> consumableEffect, boolean usableOutOfFight) {
        super(name, description, price, (e, from) -> consumableEffect.accept(e.get(0), from), usableOutOfFight);
    }

    public void consume(Entity entity, Entity from) {
        super.consume(List.of(entity), from);
    }
}
