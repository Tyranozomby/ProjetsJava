package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public class WallTile extends Tile {

    public WallTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void onStep(Game game, Player player) {
        // Impossible to step on a wall
    }
}
