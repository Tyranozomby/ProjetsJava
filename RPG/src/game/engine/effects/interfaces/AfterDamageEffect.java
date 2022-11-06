package game.engine.effects.interfaces;

import game.engine.fight.DamageInstance;

public interface AfterDamageEffect extends EffectTrigger {
    void afterDamageApply(DamageInstance damageInstance);
}
