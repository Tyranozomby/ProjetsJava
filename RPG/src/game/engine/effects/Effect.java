package game.engine.effects;

import game.engine.entities.Entity;
import game.engine.fight.Fight;

import java.util.Objects;

/**
 * Class representing an effect that can be applied to an entity.
 * Each effect can trigger at any of the following times:
 * - Immediately after being applied
 * - At the start of the entity's turn
 * - At the end of the entity's turn
 * - Before the entity takes damage
 * - After the entity takes damage
 */
public abstract class Effect {

    protected final String name;

    protected final String description;

    protected Entity effectSource;

    protected Entity thisEntity;

    protected Fight fight;

    protected Effect(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setEffectSource(Entity effectSource) {
        this.effectSource = effectSource;
    }

    public void setTarget(Entity to) {
        this.thisEntity = to;
    }

    public void setFight(Fight fight) {
        this.fight = fight;
    }

    @Override
    public String toString() {
        return "Effect{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Effect effect = (Effect) o;
        return name.equals(effect.name) && description.equals(effect.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
