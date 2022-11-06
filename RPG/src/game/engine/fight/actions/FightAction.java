package game.engine.fight.actions;

import game.engine.entities.Entity;
import game.engine.fight.Fight;

import java.util.logging.Logger;

public abstract class FightAction {

    protected final Logger LOGGER = Logger.getLogger(FightAction.class.getName());

    public abstract void doAction(Fight fight, Entity from);
}
