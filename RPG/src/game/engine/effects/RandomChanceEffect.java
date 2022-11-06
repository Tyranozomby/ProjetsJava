package game.engine.effects;

import game.engine.Game;
import game.engine.entities.Entity;
import game.engine.fight.Fight;
import utils.Random;

import java.util.function.Supplier;
import java.util.logging.Logger;

public class RandomChanceEffect {

    private final Logger LOGGER = Logger.getLogger(RandomChanceEffect.class.getName());

    private final Supplier<Effect> effectSupplier;

    private final double chance;

    public RandomChanceEffect(Supplier<Effect> effectSupplier, double chance) {
        this.effectSupplier = effectSupplier;
        this.chance = chance;
    }

    public void tryApply(Entity from, Entity to, Fight fight) {
        Effect effect = effectSupplier.get();
        if (Random.tryChance(chance)) {
            try {
                to.applyEffect(effect, from, fight);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LOGGER.info("failed to apply " + effect + " to " + to);
            Game.UI_CALLBACKS.onEffectFailedApply(to, effect);
        }
    }

    @Override
    public String toString() {
        Effect effect = effectSupplier.get();
        return "{" +
                "name='" + effect.name + '\'' +
                ", description='" + effect.description + '\'' +
                ", chance=" + chance +
                '}';
    }
}
