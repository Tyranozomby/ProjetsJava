package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public class EndTile extends Tile {

    public EndTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void onStep(Game game, Player player) {
        Game.UI_CALLBACKS.onEndTileReached();
    }
}
