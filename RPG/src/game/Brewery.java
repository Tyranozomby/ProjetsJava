package game;

import game.engine.effects.effects.PoisonEffect;
import game.engine.entities.Entity;
import game.engine.fight.Fight;
import game.engine.items.consumables.AutoTargetConsumable;
import game.engine.items.consumables.Consumable;
import game.engine.items.consumables.SingleTargetConsumable;
import utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class Brewery {

    private static final Logger LOGGER = Logger.getLogger(Brewery.class.getName());

    @Levels({1, 2, 3, 4, 5})
    public static final ConsumableCreator HEALTH_POTION = (level) -> {
        int efficiency = 5 * level + 5;
        return AutoTargetConsumable.create("Health Potion", "Restores " + efficiency + " HP", 5 * level, (entities, from) -> {
            LOGGER.info("Consuming health potion, restoring " + efficiency + " HP");
            entities.forEach(e -> e.heal(efficiency));
        }, true, Fight::getMyself);
    };

    @Levels({2, 3, 4, 5})
    public static final ConsumableCreator PHYSICAL_DAMAGE_UPGRADE = level -> AutoTargetConsumable.create("Physical Damage Upgrade", "Increases physical damage by " + level, 20 * level, (entities, from) -> {
        LOGGER.info("Consuming physical damage upgrade, increasing damage by " + level);
        for (Entity entity : entities) {
            entity.upgradePhysicalDamage(level);
        }
    }, true, Fight::getMyself);

    @Levels({2, 3, 4, 5})
    public static final ConsumableCreator MAGIC_DAMAGE_UPGRADE = level -> AutoTargetConsumable.create("Magic Damage Upgrade", "Increases magic damage by " + level, 20 * level, (entities, from) -> {
        LOGGER.info("Consuming magic damage upgrade, increasing damage by " + level);
        for (Entity entity : entities) {
            entity.upgradeMagicalDamage(level);
        }
    }, true, Fight::getMyself);

    @Levels({3, 4, 5})
    public static final ConsumableCreator PHYSICAL_DEFENSE_UPGRADE = level -> AutoTargetConsumable.create("Physical Defense Upgrade", "Increases physical defense by " + level, 20 * level, (entities, from) -> {
        LOGGER.info("Consuming physical defense upgrade, increasing defense by " + level);
        for (Entity entity : entities) {
            entity.upgradePhysicalDefense(level);
        }
    }, true, Fight::getMyself);

    @Levels({3, 4, 5})
    public static final ConsumableCreator MAGIC_DEFENSE_UPGRADE = level -> AutoTargetConsumable.create("Magic Defense Upgrade", "Increases magic defense by " + level, 20 * level, (entities, from) -> {
        LOGGER.info("Consuming magic defense upgrade, increasing defense by " + level);
        for (Entity entity : entities) {
            entity.upgradeMagicalDefense(level);
        }
    }, true, Fight::getMyself);

    @Levels({2, 3, 4, 5})
    public static final ConsumableCreator SPEED_UPGRADE = level -> AutoTargetConsumable.create("Speed Upgrade", "Increases speed by " + level, 20 * level, (entities, from) -> {
        LOGGER.info("Consuming speed upgrade, increasing speed by " + level);
        for (Entity entity : entities) {
            entity.upgradeSpeed(level);
        }
    }, true, Fight::getMyself);

    @Levels({3, 4, 5})
    public static final ConsumableCreator HEAL_UPGRADE = level -> {
        int efficiency = 2 * level;
        return AutoTargetConsumable.create("Heal Upgrade", "Increases heal by " + efficiency, 20 * level, (entities, from) -> {
            LOGGER.info("Consuming heal upgrade, increasing heal by " + efficiency);
            for (Entity entity : entities) {
                entity.upgradeMaxHealth(efficiency);
            }
        }, true, Fight::getMyself);
    };

    @Levels({1, 2, 3, 4, 5})
    public static final ConsumableCreator MILK_BUCKET = level -> new SingleTargetConsumable("Milk Bucket", "Removes all effects on the player", 10, (entity, from) -> {
        LOGGER.info("Consuming milk bucket, removing all effects");
        entity.removeAllEffects();
    }, false);

    @Levels({2, 3, 4, 5})
    public static final ConsumableCreator POISON = level -> {
        int efficiency = 5 * level;
        return new SingleTargetConsumable("Poison", "Poisons the target, dealing " + efficiency + " damage every turn for 3 turns", 10 + level * 5, (entity, from) -> {
            LOGGER.info("Consuming poison, poisoning the target");
            entity.applyEffect(new PoisonEffect(), from, null);
        }, false);
    };

    private static final HashMap<Integer, ArrayList<ConsumableCreator>> CONSUMABLES = new HashMap<>();

    static {
        Arrays.stream(Brewery.class.getDeclaredFields()).filter(field -> field.getType().equals(ConsumableCreator.class)).forEach(field -> {
            try {
                ConsumableCreator consumableCreator = (ConsumableCreator) field.get(null);
                if (field.isAnnotationPresent(Levels.class)) {
                    Levels levels = field.getAnnotation(Levels.class);
                    for (int i = 0; i < levels.value().length; i++) {
                        int level = levels.value()[i];
                        if (!CONSUMABLES.containsKey(level)) {
                            CONSUMABLES.put(level, new ArrayList<>());
                        }
                        CONSUMABLES.get(level).add(consumableCreator);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static Consumable getRandomConsumable(int level) {
        return Random.getRandomValue(CONSUMABLES.get(level)).brew(level);
    }

    public static List<Consumable> getAll(int level) {
        return CONSUMABLES.get(level).stream().map(consumableCreator -> consumableCreator.brew(level)).toList();
    }

    @FunctionalInterface
    public interface ConsumableCreator {
        Consumable brew(int level);
    }
}
