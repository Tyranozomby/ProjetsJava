package game.engine.fight.actions.consumables;

import game.engine.entities.Entity;
import game.engine.fight.Fight;
import game.engine.items.consumables.AutoTargetConsumable;
import game.engine.items.consumables.Consumable;

public class AutoTargetConsumableFightAction extends UseConsumableFightAction {

    private final AutoTargetConsumable consumable;

    public AutoTargetConsumableFightAction(AutoTargetConsumable consumable) {
        this.consumable = consumable;
    }

    @Override
    public void doAction(Fight fight, Entity from) {
        consumable.consume(consumable.getTargets(fight, from), from);
    }

    @Override
    public Consumable getConsumable() {
        return consumable;
    }
}
