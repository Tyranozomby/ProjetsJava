package game.engine.entities;

import game.Stats;
import game.Weaponry;
import game.engine.Game;
import game.engine.effects.Effect;
import game.engine.effects.interfaces.AfterDamageEffect;
import game.engine.effects.interfaces.BeforeDamageEffect;
import game.engine.effects.interfaces.EffectTrigger;
import game.engine.effects.interfaces.InstantEffect;
import game.engine.fight.DamageInstance;
import game.engine.fight.Fight;
import game.engine.fight.actions.FightAction;
import game.engine.items.Weapon;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public abstract class Entity {

    protected final Logger LOGGER = Logger.getLogger(Entity.class.getName());

    private final HashSet<Effect> effects = new HashSet<>();

    private final String name;

    private final HashMap<Stats, Integer> stats = new HashMap<>();

    private int currentHealth;

    private Weapon equippedWeapon = null;

    public Entity(String name, int physicalDamage, int magicalDamage, int physicalDefense, int magicalDefense, int speed, int maxHealth) {
        this.name = name;
        this.currentHealth = maxHealth;

        stats.put(Stats.PHYSICAL_DAMAGE, physicalDamage);
        stats.put(Stats.MAGICAL_DAMAGE, magicalDamage);
        stats.put(Stats.PHYSICAL_DEFENSE, physicalDefense);
        stats.put(Stats.MAGICAL_DEFENSE, magicalDefense);
        stats.put(Stats.SPEED, speed);
        stats.put(Stats.MAX_HEALTH, maxHealth);
    }

    /**
     * Set the new equipped weapon and store the old one.
     *
     * @param newWeapon The new weapon to equip.
     */
    public void equipWeapon(Weapon newWeapon) {
        if (equippedWeapon != null)
            Game.UI_CALLBACKS.onWeaponUnequipped(equippedWeapon);

        equippedWeapon = newWeapon;

        if (equippedWeapon != null)
            Game.UI_CALLBACKS.onWeaponEquipped(equippedWeapon);
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    /**
     * Apply the given effect to this entity.
     * If the effect is an instant effect, it will be applied immediately.
     * An entity can only have one instance of an effect at a time.
     *
     * @param effect The effect to apply.
     * @param from   The source entity of the effect.
     * @param fight  The current fight.
     */
    public void applyEffect(Effect effect, Entity from, Fight fight) {
        effect.setEffectSource(from);
        effect.setTarget(this);
        effect.setFight(fight);

        if (fight != null) {
            if (effects.add(effect)) {
                LOGGER.info(from + " applies " + effect + " to " + this + ".");
                Game.UI_CALLBACKS.onEffectApplied(this, effect);
            } else {
                LOGGER.info(from + " tries to apply " + effect + " to " + this + ", but it already has this effect.");
                Game.UI_CALLBACKS.onEffectFailedApply(this, effect);
            }
        } else {
            effects.add(effect);
        }

        if (effect instanceof InstantEffect) {
            ((InstantEffect) effect).immediatelyApply();
        }
    }

    public void removeEffect(Effect effect) {
        Game.UI_CALLBACKS.onEffectEnd(this, effect);
        effects.remove(effect);
    }

    /**
     * {@link #dealDamage(DamageInstance, boolean)} with {@code false} as the second parameter.
     *
     * @param damageInstance The damage instance to deal.
     */
    public void dealDamage(DamageInstance damageInstance) {
        dealDamage(damageInstance, false);
    }

    /**
     * Deal damage to this entity. This method will apply all BeforeDamageEffects and AfterDamageEffects.
     *
     * @param damageInstance The damage instance to deal.
     * @param piercing       Whether the damage should ignore defense.
     */
    public void dealDamage(DamageInstance damageInstance, boolean piercing) {
        triggerEffect(BeforeDamageEffect.class, effect -> effect.beforeDamageApply(damageInstance));

        int physicalDamage = damageInstance.getPhysicalDamage(0);
        int magicalDamage = damageInstance.getMagicalDamage(0);
        if (!piercing) {
            physicalDamage = damageInstance.getPhysicalDamage(stats.get(Stats.PHYSICAL_DEFENSE));
            magicalDamage = damageInstance.getMagicalDamage(stats.get(Stats.MAGICAL_DEFENSE));
        }

        currentHealth -= physicalDamage + magicalDamage;

        LOGGER.info(this + " took " + physicalDamage + " physical damage and " + magicalDamage + " magical damage.");
        Game.UI_CALLBACKS.onDamage(this, physicalDamage, magicalDamage);

        triggerEffect(AfterDamageEffect.class, effect -> effect.afterDamageApply(damageInstance));

        if (currentHealth < 0) {
            LOGGER.info(this + " died.");
            if (this instanceof Player) {
                Game.UI_CALLBACKS.onPlayerDeath((Player) this);
            } else if (this instanceof Monster) {
                Game.UI_CALLBACKS.onMonsterDeath((Monster) this);
            }
            currentHealth = 0;
        } else {
            LOGGER.info(this + " has " + currentHealth + " health left.");
        }
    }

    /**
     * Trigger all effects that implement the given interface.
     *
     * @param effectClass   The EffectTrigger interface. (e.g. {@link BeforeDamageEffect})
     * @param effectTrigger The method to call on the effect. (e.g. {@link BeforeDamageEffect#beforeDamageApply(DamageInstance)})
     * @return TODO
     */
    public <T extends EffectTrigger, V> V triggerEffectWithReturn(Class<T> effectClass, Function<T, V> effectTrigger) {
        LOGGER.warning(effects.toString());
        return List.copyOf(effects)
                .stream()
                .filter(effectClass::isInstance)
                .map(effectClass::cast)
                .findFirst()
                .map(effectTrigger)
                .orElse(null);
    }

    public <T extends EffectTrigger> void triggerEffect(Class<T> effectClass, Consumer<T> effectTrigger) {
        triggerEffectWithReturn(effectClass, effect -> {
            effectTrigger.accept(effect);
            return null;
        });
    }

    public abstract FightAction getFightAction(Fight fight);

    public int getHealth() {
        return currentHealth;
    }

    public String getName() {
        return name;
    }

    public Weapon getWeapon() {
        if (equippedWeapon == null)
            return Weaponry.NOTHING.forge();

        return equippedWeapon;
    }

    public HashMap<Stats, Integer> getStats() {
        return stats;
    }

    public int getStat(Stats stat) {
        return stats.get(stat);
    }

    public void upgradePhysicalDamage(int amount) {
        stats.put(Stats.PHYSICAL_DAMAGE, stats.get(Stats.PHYSICAL_DAMAGE) + amount);
        Game.UI_CALLBACKS.onStatUpgrade(Stats.PHYSICAL_DAMAGE, amount);
    }

    public void upgradeMagicalDamage(int amount) {
        stats.put(Stats.MAGICAL_DAMAGE, stats.get(Stats.MAGICAL_DAMAGE) + amount);
        Game.UI_CALLBACKS.onStatUpgrade(Stats.MAGICAL_DAMAGE, amount);
    }

    public void upgradePhysicalDefense(int amount) {
        stats.put(Stats.PHYSICAL_DEFENSE, stats.get(Stats.PHYSICAL_DEFENSE) + amount);
        Game.UI_CALLBACKS.onStatUpgrade(Stats.PHYSICAL_DEFENSE, amount);
    }

    public void upgradeMagicalDefense(int amount) {
        stats.put(Stats.MAGICAL_DEFENSE, stats.get(Stats.MAGICAL_DEFENSE) + amount);
        Game.UI_CALLBACKS.onStatUpgrade(Stats.MAGICAL_DEFENSE, amount);
    }

    public void upgradeSpeed(int amount) {
        stats.put(Stats.SPEED, stats.get(Stats.SPEED) + amount);
        Game.UI_CALLBACKS.onStatUpgrade(Stats.SPEED, amount);
    }

    public void upgradeMaxHealth(int amount) {
        stats.put(Stats.MAX_HEALTH, stats.get(Stats.MAX_HEALTH) + amount);
        Game.UI_CALLBACKS.onStatUpgrade(Stats.MAX_HEALTH, amount);
    }

    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > getStat(Stats.MAX_HEALTH)) {
            currentHealth = getStat(Stats.MAX_HEALTH);
        }

        Game.UI_CALLBACKS.onHeal(this, amount);
    }

    public void removeAllEffects() {
        effects.clear();
    }
}
