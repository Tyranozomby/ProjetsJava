package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public class EmptyTile extends Tile {

    public EmptyTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void onStep(Game game, Player player) {
        // Do nothing
    }
}
