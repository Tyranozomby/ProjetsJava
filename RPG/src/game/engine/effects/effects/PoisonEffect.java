package game.engine.effects.effects;

import game.engine.effects.Effect;
import game.engine.effects.interfaces.EndTurnEffect;
import game.engine.fight.DamageInstance;

public class PoisonEffect extends Effect implements EndTurnEffect {

    public PoisonEffect() {
        super("Poison", "Poison the target for 2 damage per turn. Lasts 3 turns.");
    }

    @Override
    public void endTurnApply() {
        thisEntity.dealDamage(new DamageInstance(2, 0, effectSource));
    }
}
