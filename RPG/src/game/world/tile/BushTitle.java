package game.world.tile;

import game.Bestiary;
import game.Brewery;
import game.Weaponry;
import game.engine.Game;
import game.engine.entities.Player;
import game.engine.items.Item;
import utils.Random;

public class BushTitle implements Tile {

    private final double actionProbability;

    private final double monsterProbability;

    public BushTitle(double actionProbability, double monsterProbability) {
        this.actionProbability = actionProbability;
        this.monsterProbability = monsterProbability;
    }

    @Override
    public void onStep(Game game, Player player) {
        if (Random.tryChance(actionProbability)) {
            if (Random.tryChance(monsterProbability)) {
                if (game.getCurrentLevel() == 1 || Random.getRandomInt(1, 2) == 1) {
                    game.battle(Bestiary.getRandomMonster(game.getCurrentLevel()));
                } else {
                    game.battle(Bestiary.getRandomMonster(game.getCurrentLevel()), Bestiary.getRandomMonster(game.getCurrentLevel()));
                }
            }
        } else {
            Item item;
            if (Random.tryChance(0.4)) {
                item = Weaponry.getRandomWeapon(game.getCurrentLevel());
            } else {
                item = Brewery.getRandomConsumable(game.getCurrentLevel());
            }
            player.giveItem(item);
        }
    }
}