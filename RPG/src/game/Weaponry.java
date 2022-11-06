package game;

import game.engine.effects.RandomChanceEffect;
import game.engine.effects.effects.*;
import game.engine.fight.Fight;
import game.engine.fight.attacks.AutoTargetAttack;
import game.engine.fight.attacks.SingleTargetAttack;
import game.engine.items.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class Weaponry {

    public static final WeaponCreator NOTHING = () -> new Weapon("Nothing", "No weapon", 0,
            List.of(new SingleTargetAttack("Punch", "Punch the enemy", 1, 0)));

    // DEFAULT WEAPONS
    @Levels({1, 2, 3, 4, 5})
    public static final WeaponCreator FORK = () -> new Weapon("Fork", "A small but skipy plastic fork", 5,
            List.of(new SingleTargetAttack("Simple stab", "Calmly stab the enemy with your fork", 2, 0),
                    new SingleTargetAttack("Blind stab", "Stab the enemy with your fork, but with your eyes closed. May deal 3 damage if a vital spot has been hit, else 0", 0, 0,
                            List.of(new RandomChanceEffect(() -> new BonusDamageEffect(3, false), .5)))));

    @Levels({1, 2, 3, 4, 5})
    public static final WeaponCreator SPOON = () -> new Weapon("Spoon", "A beautiful and shiny spoon made out of pure raw iron", 5,
            List.of(new SingleTargetAttack("Fast swing", "Frenetically swing your spoon in hope to reach the enemy", 2, 0),
                    AutoTargetAttack.create("Spoon bending", "Make your spoon bend just by looking at it. May confuse the enemies", 0, 1,
                            List.of(new RandomChanceEffect(ConfuseEffect::new, .75)), Fight::getEnemies)));

    @Levels({1, 2, 3, 4, 5})
    public static final WeaponCreator KNIFE = () -> new Weapon("Knife", "A rusty round tip knife", 5,
            List.of(new SingleTargetAttack("Stab", "Stab the enemy with your knife", 2, 0),
                    new SingleTargetAttack("Throw", "Throw your knife at the enemy. 50% chance to one shot, 100% chance to lose knife", 0, 0,
                            List.of(new RandomChanceEffect(OneShotEffect::new, .5), new RandomChanceEffect(WeaponDestroy::new, 1)))));

    @Levels({1, 2, 3, 4, 5})
    public static final WeaponCreator PLATE = () -> new Weapon("Plate", "A pink birthday cardboard plate", 5,
            List.of(new SingleTargetAttack("Smash", "Smash the enemy with your beautiful plate", 2, 0),
                    AutoTargetAttack.create("Defend", "Defend yourself with your plate. 20% chance to fail", 0, 0,
                            List.of(new RandomChanceEffect(() -> new ProtectEffect(.5, 0, false), .8)), Fight::getMyself)));

    // OTHER WEAPONS
    @Levels({3, 4, 5})
    public static final WeaponCreator RUBIKS_CUBE = () -> new Weapon("Rubik's cube", "A 90s masterpiece whose solving can blow minds", 42,
            List.of(AutoTargetAttack.create("Solve", "Solve the Rubik's cube. Deals emotional damage to the enemies and might also confuse them", 0, 5,
                    List.of(new RandomChanceEffect(ConfuseEffect::new, .4)), Fight::getEnemies)));

    @Levels({2, 3, 4, 5})
    public static final WeaponCreator SHIELD = () -> new Weapon("Shield", "A simple but sturdy metal shield", 1,
            List.of(new SingleTargetAttack("Dash", "Rush towards the enemy with you shield", 4, 0),
                    AutoTargetAttack.create("Protect", "Protect yourself with your shield", 0, 0,
                            List.of(new RandomChanceEffect(() -> new ProtectEffect(1, .5, true), 1)), Fight::getMyself)));

    @Levels({2})
    public static final WeaponCreator LEGO_BAG = () -> new Weapon("Bag of legos", "A plastic bag full of legos", 5,
            List.of(new SingleTargetAttack("Lego punch", "Swing the lego bag at the enemy's face", 3, 0),
                    AutoTargetAttack.create("Empty", "Empty the bag at the enemies' feet", 0, 0,
                            List.of(new RandomChanceEffect(LegoEffect::new, 1)), Fight::getEnemies)));

    private static final HashMap<Integer, ArrayList<WeaponCreator>> WEAPONS = new HashMap<>();

    static {
        Arrays.stream(Weaponry.class.getDeclaredFields()).filter(field -> field.getType().equals(WeaponCreator.class)).forEach(field -> {
            try {
                WeaponCreator weaponCreator = (WeaponCreator) field.get(null);
                if (field.isAnnotationPresent(Levels.class)) {
                    Levels levels = field.getAnnotation(Levels.class);
                    for (int level : levels.value()) {
                        if (!WEAPONS.containsKey(level)) {
                            WEAPONS.put(level, new ArrayList<>());
                        }
                        WEAPONS.get(level).add(weaponCreator);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static Weapon getRandomWeapon(int level) {
        return WEAPONS.get(level).get((int) (Math.random() * WEAPONS.get(level).size())).forge();
    }

    public static List<Weapon> getAll(int level) {
        return WEAPONS.get(level).stream().map(WeaponCreator::forge).toList();
    }

    @FunctionalInterface
    public interface WeaponCreator {
        /**
         * To change call from Weaponry.WEAPON.apply() to Weaponry.WEAPON.forge()
         *
         * @return An instance of the wanted weapon
         */
        Weapon forge();
    }
}
