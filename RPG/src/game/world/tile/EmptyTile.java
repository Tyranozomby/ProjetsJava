package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public class EmptyTile implements Tile {

    @Override
    public void onStep(Game game, Player player) {
        // Do nothing
    }
}
