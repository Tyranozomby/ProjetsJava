package game.engine.fight.attacks;

import game.engine.effects.RandomChanceEffect;

import java.util.List;

public class SingleTargetAttack extends Attack {

    public SingleTargetAttack(String name, String description, int physicalDamage, int magicalDamage, List<RandomChanceEffect> effects) {
        super(name, description, physicalDamage, magicalDamage, effects);
    }

    public SingleTargetAttack(String name, String description, int physicalDamage, int magicalDamage) {
        this(name, description, physicalDamage, magicalDamage, List.of());
    }
}
