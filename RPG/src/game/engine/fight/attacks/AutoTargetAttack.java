package game.engine.fight.attacks;

import game.engine.effects.RandomChanceEffect;
import game.engine.entities.Entity;
import game.engine.fight.Fight;

import java.util.List;
import java.util.function.BiFunction;

public abstract class AutoTargetAttack extends Attack {

    private AutoTargetAttack(String name, String description, int physicalDamage, int magicalDamage, List<RandomChanceEffect> effects) {
        super(name, description, physicalDamage, magicalDamage, effects);
    }

    public static AutoTargetAttack create(String name, String description, int physicalDamage, int magicalDamage, List<RandomChanceEffect> effects, BiFunction<Fight, Entity, List<Entity>> targetSupplier) {
        return new AutoTargetAttack(name, description, physicalDamage, magicalDamage, effects) {
            @Override
            public List<Entity> getTargets(Fight fight, Entity from) {
                return targetSupplier.apply(fight, from);
            }
        };
    }

    public abstract List<Entity> getTargets(Fight fight, Entity from);
}
