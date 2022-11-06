package game.engine.entities.monsters;

import game.Stats;
import game.engine.entities.Monster;
import game.engine.fight.Fight;
import game.engine.fight.actions.FightAction;
import game.engine.fight.actions.SkipTurn;
import game.engine.fight.actions.attacks.AttackFightAction;
import game.engine.fight.actions.attacks.AutoTargetAttackFightAction;
import game.engine.fight.actions.attacks.SingleTargetAttackFightAction;
import game.engine.fight.attacks.Attack;
import game.engine.fight.attacks.AutoTargetAttack;
import game.engine.fight.attacks.SingleTargetAttack;

/**
 * This class is used to determine the AI of a monster.
 * It is used to determine the actions of a monster in a fight.
 */
@FunctionalInterface
public interface MonsterAI {

    /**
     * Do nothing
     */
    MonsterAI SKIP_TURN = (monster, fight) -> new SkipTurn();

    /**
     * Choose a random attack
     */
    MonsterAI RANDOM = (monster, fight) -> {
        Attack attack = monster.getWeapon().getAttacks().get((int) (Math.random() * monster.getWeapon().getAttacks().size()));
        if (attack instanceof AutoTargetAttack) {
            return new AutoTargetAttackFightAction((AutoTargetAttack) attack);
        } else {
            return new SingleTargetAttackFightAction((SingleTargetAttack) attack, fight.getPlayer());
        }
    };

    /**
     * Ai that only uses the first/normal attack
     */
    MonsterAI BASIC = (monster, fight) -> {
        Attack attack = monster.getWeapon().getAttacks().get(0);
        if (attack instanceof AutoTargetAttack) {
            return new AutoTargetAttackFightAction((AutoTargetAttack) attack);
        } else {
            return new SingleTargetAttackFightAction((SingleTargetAttack) attack, fight.getPlayer());
        }
    };

    /**
     * Ai that only uses the second/special attack if exists
     */
    MonsterAI SPECIAL = (monster, fight) -> {
        Attack attack = monster.getWeapon().getAttacks().get(monster.getWeapon().getAttacks().size() - 1);
        if (attack instanceof AutoTargetAttack) {
            return new AutoTargetAttackFightAction((AutoTargetAttack) attack);
        } else {
            return new SingleTargetAttackFightAction((SingleTargetAttack) attack, fight.getPlayer());
        }
    };

    /**
     * Ai that is random when health is above 50% and basic when health is below 50%
     */
    MonsterAI RANDOM_WHEN_HEALTH_ABOVE_50_THEN_BASIC = (monster, fight) -> {
        if (monster.getHealth() > monster.getStat(Stats.MAX_HEALTH) / 2) {
            return RANDOM.getAction(monster, fight);
        } else {
            return BASIC.getAction(monster, fight);
        }
    };

    /**
     * Ai that is random when health is above 50% and special when health is below 50%
     */
    MonsterAI RANDOM_WHEN_HEALTH_ABOVE_50_THEN_SPECIAL = (monster, fight) -> {
        if (monster.getHealth() > monster.getStat(Stats.MAX_HEALTH) / 2) {
            return RANDOM.getAction(monster, fight);
        } else {
            return SPECIAL.getAction(monster, fight);
        }
    };

    /**
     * Complex AI that looks at the damage and the effects of each attack and chooses the best one based on the situation
     */
    MonsterAI COMPLEX = (monster, fight) -> {
        FightAction bestAction = null;
        double bestScore = 0;
        for (Attack attack : monster.getWeapon().getAttacks()) {
            AttackFightAction action;
            if (attack instanceof AutoTargetAttack) {
                action = new AutoTargetAttackFightAction((AutoTargetAttack) attack);
            } else {
                action = new SingleTargetAttackFightAction((SingleTargetAttack) attack, fight.getPlayer());
            }
            double score = getScore(action, monster, fight);
            if (score > bestScore) {
                bestAction = action;
                bestScore = score;
            }
        }
        return bestAction;
    };


    /**
     * Get the score of an action based on the damage and effects
     *
     * @param action  The action to score
     * @param monster The monster doing the action
     * @param fight   The current fight
     * @return The score of the action
     */
    private static double getScore(AttackFightAction action, Monster monster, Fight fight) {
        double score = 0;
        int physicalDamage = Math.max(0, monster.getStat(Stats.PHYSICAL_DAMAGE) != 0 ? action.getAttack().getPhysicalDamage() + monster.getStat(Stats.PHYSICAL_DAMAGE) - fight.getPlayer().getStat(Stats.PHYSICAL_DEFENSE) : 0);
        int magicalDamage = Math.max(0, monster.getStat(Stats.MAGICAL_DAMAGE) != 0 ? action.getAttack().getMagicalDamage() + monster.getStat(Stats.MAGICAL_DAMAGE) - fight.getPlayer().getStat(Stats.MAGICAL_DEFENSE) : 0);
        int damage = physicalDamage + magicalDamage;
        score += damage;

        // TODO take into account the effects
        return score;
    }

    /**
     * Get the action to do depending on the AI
     *
     * @param monster The monster doing the action
     * @param fight   The current fight
     * @return The action to do
     */
    FightAction getAction(Monster monster, Fight fight);
}
