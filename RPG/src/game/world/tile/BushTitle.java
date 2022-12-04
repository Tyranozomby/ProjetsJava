package game.world.tile;

import game.Bestiary;
import game.Brewery;
import game.Weaponry;
import game.engine.Game;
import game.engine.entities.Player;
import game.engine.items.Item;
import utils.Random;

public class BushTitle extends Tile {

    private final double actionProbability;

    private final double monsterProbability;

    public BushTitle(int x, int y, double actionProbability, double monsterProbability) {
        super(x, y);
        this.actionProbability = actionProbability;
        this.monsterProbability = monsterProbability;
    }

    @Override
    public void onStep(Game game, Player player) {
        if (Random.tryChance(actionProbability)) {
            if (Random.tryChance(monsterProbability)) {
                if (game.getLevelValue() == 1 || Random.getRandomInt(1, 2) == 1) {
                    game.battle(Bestiary.getRandomMonster(game.getLevelValue()));
                } else {
                    game.battle(Bestiary.getRandomMonster(game.getLevelValue()), Bestiary.getRandomMonster(game.getLevelValue()));
                }
            } else {
                Item item;
                if (Random.tryChance(0.4)) {
                    item = Weaponry.getRandomWeapon(game.getLevelValue());
                } else {
                    item = Brewery.getRandomConsumable(game.getLevelValue());
                }
                player.giveItem(item);
            }
        }
    }
}
