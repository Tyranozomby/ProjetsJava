package game.engine.fight.actions.attacks;

import game.engine.entities.Entity;
import game.engine.fight.Fight;
import game.engine.fight.attacks.Attack;
import game.engine.fight.attacks.SingleTargetAttack;

import java.util.List;

public class SingleTargetAttackFightAction extends AttackFightAction {

    private final SingleTargetAttack attack;

    private final Entity target;

    public SingleTargetAttackFightAction(SingleTargetAttack attack, Entity target) {
        this.attack = attack;
        this.target = target;
    }

    @Override
    public Attack getAttack() {
        return attack;
    }

    @Override
    public void doAction(Fight fight, Entity from) {
        attack.doAttack(from, List.of(target), fight);
    }

    @Override
    public String toString() {
        return "SingleTargetAttackFightAction{" +
                "attack=" + attack +
                ", target=" + target +
                '}';
    }
}
