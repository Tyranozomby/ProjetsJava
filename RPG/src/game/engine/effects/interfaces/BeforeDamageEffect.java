package game.engine.effects.interfaces;

import game.engine.fight.DamageInstance;

public interface BeforeDamageEffect extends EffectTrigger {
    void beforeDamageApply(DamageInstance damageInstance);
}
