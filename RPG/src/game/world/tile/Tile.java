package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public abstract class Tile {

    public final int x;

    public final int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void onStep(Game game, Player player);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
