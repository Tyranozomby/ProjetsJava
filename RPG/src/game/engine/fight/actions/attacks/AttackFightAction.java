package game.engine.fight.actions.attacks;

import game.engine.fight.actions.FightAction;
import game.engine.fight.attacks.Attack;

public abstract class AttackFightAction extends FightAction {

    public abstract Attack getAttack();
}
