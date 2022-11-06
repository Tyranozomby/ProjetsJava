package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public class WallTile implements Tile {

    @Override
    public void onStep(Game game, Player player) {
        // Impossible to step on a wall
    }
}
