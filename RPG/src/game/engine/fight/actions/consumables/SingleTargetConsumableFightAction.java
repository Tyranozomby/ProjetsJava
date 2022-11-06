package game.engine.fight.actions.consumables;

import game.engine.entities.Entity;
import game.engine.fight.Fight;
import game.engine.items.consumables.Consumable;
import game.engine.items.consumables.SingleTargetConsumable;

public class SingleTargetConsumableFightAction extends UseConsumableFightAction {

    private final SingleTargetConsumable consumable;

    private final Entity target;

    public SingleTargetConsumableFightAction(SingleTargetConsumable consumable, Entity target) {
        this.consumable = consumable;
        this.target = target;
    }

    @Override
    public void doAction(Fight fight, Entity from) {
        consumable.consume(target, from);
    }

    @Override
    public Consumable getConsumable() {
        return consumable;
    }
}
