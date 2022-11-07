package game.engine.entities;

import game.engine.effects.Effect;
import game.engine.entities.monsters.MonsterAI;
import game.engine.fight.Fight;
import game.engine.fight.actions.FightAction;
import game.engine.items.Weapon;

import java.util.List;

public class Monster extends Entity {

    private final int level;

    private final MonsterAI monsterAI;

    public Monster(String name, int physicalDamage, int magicalDamage, int physicalDefense, int magicalDefense, int speed, int maxHealth, int level, Weapon weapon, MonsterAI monsterAI) {
        super(name, physicalDamage, magicalDamage, physicalDefense, magicalDefense, speed, maxHealth);
        this.level = level;
        this.monsterAI = monsterAI;
        equipWeapon(weapon);
    }

    public Monster(String name, int physicalDamage, int magicalDamage, int physicalDefense, int magicalDefense, int speed, int maxHealth, int level, Weapon weapon, MonsterAI monsterAI, List<Effect> effects) {
        this(name, physicalDamage, magicalDamage, physicalDefense, magicalDefense, speed, maxHealth, level, weapon, monsterAI);
        for (Effect effect : effects) {
            applyEffect(effect, this, null);
        }
        LOGGER.warning("Monster " + name + " has " + effects.size() + " effects." + (effects.size() > 0 ? " Effects: " + effects : ""));
    }

    @Override
    public FightAction getFightAction(Fight fight) {
        return monsterAI.getAction(this, fight);
    }

    @Override
    public String toString() {
        return "Level " + level + " " + getName();
    }

    public int getLevel() {
        return level;
    }
}
