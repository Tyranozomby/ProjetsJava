package game.engine.effects.effects;

import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.effects.interfaces.BeforeActionEffect;
import game.engine.fight.DamageInstance;
import game.engine.fight.actions.FightAction;
import game.engine.fight.actions.SkipTurn;
import utils.Random;

public class LegoEffect extends Effect implements BeforeActionEffect {

    public LegoEffect() {
        super("Lego effect", "Each time an enemy attacks, there is a 60% chance it will walk on a lego on therefore end its turn in pain. Lasts until the end of the game");
    }

    @Override
    public FightAction beforeActionAction(FightAction action) {
        Game.UI_CALLBACKS.onEffectActivation(this);

        if (Random.tryChance(.6)) {
            LOGGER.info("The enemy walked on a lego and ended its turn while screaming");
            thisEntity.dealDamage(new DamageInstance(1, 0, thisEntity));
            return new SkipTurn();
        }
        return action;
    }
}
