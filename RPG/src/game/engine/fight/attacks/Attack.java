package game.engine.fight.attacks;

import game.Stats;
import game.engine.Game;
import game.engine.effects.RandomChanceEffect;
import game.engine.entities.Entity;
import game.engine.fight.DamageInstance;
import game.engine.fight.Fight;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class Attack {

    private final Logger LOGGER = Logger.getLogger(Attack.class.getName());

    private final String name;

    private final String description;

    private final int physicalDamage;

    private final int magicalDamage;

    private final List<RandomChanceEffect> effects;


    public Attack(String name, String description, int physicalDamage, int magicalDamage, List<RandomChanceEffect> effects) {
        this.name = name;
        this.description = description;
        this.physicalDamage = physicalDamage;
        this.magicalDamage = magicalDamage;
        this.effects = effects;
    }


    public void doAttack(Entity from, List<Entity> targets, Fight fight) {
        // Check entity has attack in weapon
        if (!from.getWeapon().getAttacks().contains(this)) {
            LOGGER.warning(from.getWeapon().toString());
            LOGGER.warning("Entity " + from + " tried to attack with an attack that is not in its weapon.");
        }

        for (Entity target : targets) {
            LOGGER.info(from + " attacks " + target + " with " + this + ".");
            Game.UI_CALLBACKS.onAttack(from, target, this);

            DamageInstance damageInstance = new DamageInstance(
                    physicalDamage != 0 ? physicalDamage + from.getStat(Stats.PHYSICAL_DAMAGE) : 0,
                    magicalDamage != 0 ? magicalDamage + from.getStat(Stats.MAGICAL_DAMAGE) : 0,
                    from
            );

            target.dealDamage(damageInstance);

            for (RandomChanceEffect randomChanceEffect : effects) {
                randomChanceEffect.tryApply(from, target, fight);
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public int getMagicalDamage() {
        return magicalDamage;
    }

    public List<RandomChanceEffect> getEffects() {
        return effects;
    }

    @Override
    public String toString() {
        return "Attack{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", physicalDamage=" + physicalDamage +
                ", magicalDamage=" + magicalDamage +
                ", effects=" + effects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attack attack = (Attack) o;
        return name.equals(attack.name)
                && description.equals(attack.description)
                && physicalDamage == attack.physicalDamage
                && magicalDamage == attack.magicalDamage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, physicalDamage, magicalDamage, effects);
    }
}
