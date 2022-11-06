package game.engine.fight.actions.consumables;

import game.engine.fight.actions.FightAction;
import game.engine.items.consumables.Consumable;

public abstract class UseConsumableFightAction extends FightAction {

    public abstract Consumable getConsumable();
}
