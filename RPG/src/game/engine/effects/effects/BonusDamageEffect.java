package game.engine.effects.effects;

import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.effects.interfaces.InstantEffect;
import game.engine.fight.DamageInstance;

public class BonusDamageEffect extends Effect implements InstantEffect {

    private final int bonusDamage;

    private final boolean isMagical;

    public BonusDamageEffect(int bonusDamage, boolean isMagical) {
        super("Bonus Damage", "Deals " + bonusDamage + " bonus damage.");
        this.bonusDamage = bonusDamage;
        this.isMagical = isMagical;
    }

    @Override
    public void immediatelyApply() {
        Game.UI_CALLBACKS.onEffectActivation(this);

        if (isMagical)
            thisEntity.dealDamage(new DamageInstance(0, bonusDamage, thisEntity));
        else
            thisEntity.dealDamage(new DamageInstance(bonusDamage,
                    0, thisEntity));
        thisEntity.removeEffect(this);
    }
}
