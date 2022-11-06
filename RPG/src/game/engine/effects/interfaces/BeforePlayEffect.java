package game.engine.effects.interfaces;

import game.engine.fight.actions.FightAction;

public interface BeforePlayEffect extends EffectTrigger {
    FightAction beforePlayAction();
}
