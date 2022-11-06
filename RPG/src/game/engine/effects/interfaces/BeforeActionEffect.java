package game.engine.effects.interfaces;

import game.engine.fight.actions.FightAction;

public interface BeforeActionEffect extends EffectTrigger {
    FightAction beforeActionAction(FightAction action);
}
