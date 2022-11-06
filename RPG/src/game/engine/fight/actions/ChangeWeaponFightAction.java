package game.engine.fight.actions;

import game.engine.entities.Entity;
import game.engine.fight.Fight;
import game.engine.items.Weapon;

public class ChangeWeaponFightAction extends FightAction {

    private final Weapon weapon;

    public ChangeWeaponFightAction(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void doAction(Fight fight, Entity from) {
        from.equipWeapon(weapon);
    }
}
