package game.engine.effects.effects;

import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.effects.interfaces.BeforeDamageEffect;
import game.engine.effects.interfaces.BeforePlayEffect;
import game.engine.fight.DamageInstance;
import game.engine.fight.actions.FightAction;
import game.engine.fight.actions.SkipTurn;

public class ProtectEffect extends Effect implements BeforeDamageEffect, BeforePlayEffect {

    private final double physicalReduction;

    private final double magicalReduction;

    private final boolean skipNextTurn;

    public ProtectEffect(double physicalReduction, double magicalReduction, boolean skipNextTurn) {
        super("Protect", "Reduces damage by " + (int) (physicalReduction * 100) + "% physical and " + (int) (magicalReduction * 100) + "% magical." + (skipNextTurn ? "Next turn wll be skipped" : ""));
        this.physicalReduction = physicalReduction;
        this.magicalReduction = magicalReduction;
        this.skipNextTurn = skipNextTurn;
    }

    @Override
    public void beforeDamageApply(DamageInstance damageInstance) {
        Game.UI_CALLBACKS.onEffectActivation(this);

        damageInstance.setPhysicalDamage((int) (damageInstance.getPhysicalDamage(0) * physicalReduction));
        damageInstance.setMagicalDamage((int) (damageInstance.getMagicalDamage(0) * magicalReduction));

        if (!skipNextTurn) {
            thisEntity.removeEffect(this);
        }
    }

    @Override
    public FightAction beforePlayAction() {
        Game.UI_CALLBACKS.onEffectActivation(this);

        thisEntity.removeEffect(this);
        return new SkipTurn();
    }
}
