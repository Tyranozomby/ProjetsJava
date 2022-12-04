package game.world.tile;

import game.Weaponry;
import game.engine.Game;
import game.engine.entities.Player;
import game.engine.items.Weapon;
import utils.Random;

public class ChestTile extends Tile {

    private boolean visited = false;

    public ChestTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void onStep(Game game, Player player) {
        if (!visited) {
            visited = true;
            player.addGold(Random.getRandomInt(3, 8));

            Weapon randomWeapon = Weaponry.getRandomWeapon(game.getLevelValue());
            player.giveItem(randomWeapon);
        }
        game.getMap().removeCurrentTile();
    }
}
