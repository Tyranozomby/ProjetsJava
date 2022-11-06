package game.engine.effects.effects;

import game.engine.effects.Effect;
import game.engine.effects.interfaces.InstantEffect;
import game.engine.entities.Player;
import game.engine.items.Weapon;

public class WeaponDestroy extends Effect implements InstantEffect {
    public WeaponDestroy() {
        super("Weapon Destroy", "Destroy the source's weapon");
    }

    @Override
    public void immediatelyApply() {
        Weapon weapon = effectSource.getWeapon();
        effectSource.equipWeapon(null);

        if (effectSource instanceof Player) {
            ((Player) effectSource).getInventory().removeItem(weapon);
        }

        effectSource.removeEffect(this);
    }
}
