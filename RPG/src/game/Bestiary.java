package game;

import game.engine.effects.effects.ThornsEffect;
import game.engine.entities.Monster;
import game.engine.entities.monsters.MonsterAI;
import utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class containing all monsters present in the game.
 * Each monster is a supplier which returns a new instance of the monster.
 */
@SuppressWarnings("unused")
public class Bestiary {

    @Levels({1, 2, 3, 4, 5})
    public static final MonsterCreator SLIME = level -> new Monster("Slime",
            3 * level - 1,
            0,
            level - level % 2,
            0,
            2 * level + 3,
            5 * level + 5,
            level,
            null,
            MonsterAI.BASIC
    );

    @Levels({2, 3})
    public static final MonsterCreator CACTUS = level -> new Monster("Cactus",
            0,
            0,
            0,
            0,
            0,
            5 * level + 5,
            level,
            null,
            MonsterAI.SKIP_TURN,
            List.of(new ThornsEffect(2 * level))
    );

    private static final HashMap<Integer, ArrayList<MonsterCreator>> MONSTERS = new HashMap<>();

    static {
        Arrays.stream(Bestiary.class.getDeclaredFields()).filter(field -> field.getType().equals(MonsterCreator.class)).forEach(field -> {
            try {
                MonsterCreator monsterCreator = (MonsterCreator) field.get(null);
                if (field.isAnnotationPresent(Levels.class)) {
                    Levels levels = field.getAnnotation(Levels.class);
                    for (int level : levels.value()) {
                        if (!MONSTERS.containsKey(level)) {
                            MONSTERS.put(level, new ArrayList<>());
                        }
                        MONSTERS.get(level).add(monsterCreator);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Function to get a random monster with a level between level - 1 and level + 1.
     * You have a 10% chance to get a monster of level - 1, 80% chance to get a monster of level and 10% chance to get a monster of level + 1.
     *
     * @param level The current map level
     * @return A random monster with a level between level - 1 and level + 1
     */
    public static Monster getRandomMonster(int level) {
        int randomLevel = level;
        if (Random.tryChance(.2)) {
            if (Random.tryChance(.5)) {
                randomLevel = Math.max(1, level - 1);
            } else {
                randomLevel = Math.min(MONSTERS.size(), level + 1);
            }
        }
        return MONSTERS.get(level).get((int) (Math.random() * MONSTERS.get(level).size())).spawn(randomLevel);
    }

    @FunctionalInterface
    public interface MonsterCreator {
        /**
         * To change call from Bestiary.MONSTER.apply(level) to Bestiary.MONSTER.ofLevel(level)
         *
         * @param level the level of the monster
         * @return An instance of the wanted monster
         */
        Monster spawn(int level);
    }
}
