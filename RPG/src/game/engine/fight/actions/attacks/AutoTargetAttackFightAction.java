package game.engine.fight.actions.attacks;

import game.engine.entities.Entity;
import game.engine.fight.Fight;
import game.engine.fight.attacks.Attack;
import game.engine.fight.attacks.AutoTargetAttack;

import java.util.List;

public class AutoTargetAttackFightAction extends AttackFightAction {

    private final AutoTargetAttack attack;

    public AutoTargetAttackFightAction(AutoTargetAttack attack) {
        this.attack = attack;
    }

    @Override
    public Attack getAttack() {
        return attack;
    }

    @Override
    public void doAction(Fight fight, Entity from) {
        List<Entity> targets = attack.getTargets(fight, from);
        attack.doAttack(from, targets, fight);
    }

    @Override
    public String toString() {
        return "AutoTargetAttackFightAction{" +
                "attack=" + attack +
                '}';
    }
}
