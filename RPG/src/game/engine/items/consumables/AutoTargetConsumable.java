package game.engine.items.consumables;

import game.engine.entities.Entity;
import game.engine.fight.Fight;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class AutoTargetConsumable extends Consumable {

    private AutoTargetConsumable(String name, String description, int price, BiConsumer<List<Entity>, Entity> effectSupplier, boolean usableOutOfFight) {
        super(name, description, price, effectSupplier, usableOutOfFight);
    }

    public static AutoTargetConsumable create(String name, String description, int price, BiConsumer<List<Entity>, Entity> effectSupplier, boolean usableOutOfFight, BiFunction<Fight, Entity, List<Entity>> targetSupplier) {
        return new AutoTargetConsumable(name, description, price, effectSupplier, usableOutOfFight) {
            @Override
            public List<Entity> getTargets(Fight fight, Entity from) {
                return targetSupplier.apply(fight, from);
            }
        };
    }

    public void consume(List<Entity> entity, Entity from) {
        super.consume(entity, from);
    }

    public abstract List<Entity> getTargets(Fight fight, Entity from);
}
