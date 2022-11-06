package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public interface Tile {
    void onStep(Game game, Player player);
}
