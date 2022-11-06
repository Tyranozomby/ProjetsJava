package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public class EndTile implements Tile {

    @Override
    public void onStep(Game game, Player player) {
        Game.UI_CALLBACKS.onEndTileReached();
    }
}
