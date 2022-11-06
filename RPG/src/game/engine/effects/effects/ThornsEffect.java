package game.engine.effects.effects;

import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.effects.interfaces.AfterDamageEffect;
import game.engine.entities.Entity;
import game.engine.fight.DamageInstance;

public class ThornsEffect extends Effect implements AfterDamageEffect {

    private final int damage;

    public ThornsEffect(int damage) {
        super("Thorns", "Deals " + damage + " damage to the attacker.");
        this.damage = damage;
    }

    @Override
    public void afterDamageApply(DamageInstance damageInstance) {
        Game.UI_CALLBACKS.onEffectActivation(this);

        Entity attacker = damageInstance.getSource();
        if (attacker != null && attacker != thisEntity && damageInstance.getPhysicalDamage(0) > 0) {
            LOGGER.info("Thorns effect deals " + damage + " damage to " + attacker + ".");
            attacker.dealDamage(new DamageInstance(damage, 0, thisEntity), true);
        }
    }
}
