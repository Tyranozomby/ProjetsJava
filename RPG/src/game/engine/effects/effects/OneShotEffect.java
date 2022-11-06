package game.engine.effects.effects;

import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.effects.interfaces.InstantEffect;
import game.engine.fight.DamageInstance;

public class OneShotEffect extends Effect implements InstantEffect {


    public OneShotEffect() {
        super("One shot", "One shot the target");
    }

    @Override
    public void immediatelyApply() {
        Game.UI_CALLBACKS.onEffectActivation(this);

        thisEntity.dealDamage(new DamageInstance(thisEntity.getHealth(), 0, effectSource), true);
        thisEntity.removeEffect(this);
    }
}
