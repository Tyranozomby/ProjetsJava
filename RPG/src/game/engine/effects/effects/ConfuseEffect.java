package game.engine.effects.effects;

import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.effects.interfaces.BeforeActionEffect;
import game.engine.fight.actions.FightAction;
import game.engine.fight.actions.SkipTurn;
import utils.Random;

public class ConfuseEffect extends Effect implements BeforeActionEffect {

    private int turnsLeft;

    public ConfuseEffect() {
        super("Confuse", "Can skip the turn of the entity");

        // Duration between 2 and 5 turns (included)
        turnsLeft = Random.getRandomInt(2, 5);
    }

    /**
     * Skip the turn of the entity that played the action one third of the time.
     *
     * @param action The action that was played.
     * @return The action to play instead.
     */
    @Override
    public FightAction beforeActionAction(FightAction action) {
        Game.UI_CALLBACKS.onEffectActivation(this);

        if (Random.tryChance(1) && !(action instanceof SkipTurn)) {
            LOGGER.info(thisEntity + " is confused. Turn skipped.");
            return new SkipTurn();
        }

        if (--turnsLeft == 0) {
            LOGGER.info(thisEntity + " is no longer confused.");
            thisEntity.removeEffect(this);
        }

        return action;
    }
}
