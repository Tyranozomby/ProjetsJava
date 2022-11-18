package game.world.tile;

import game.Weaponry;
import game.engine.Game;
import game.engine.entities.Player;
import game.engine.items.Weapon;
import utils.Random;

public class ChestTile implements Tile {

    private boolean visited = false;

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
