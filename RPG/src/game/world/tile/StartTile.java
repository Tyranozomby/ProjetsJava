package game.world.tile;

import game.engine.Game;
import game.engine.entities.Player;

public class StartTile extends Tile {

    public StartTile(int x, int y) {
        super(x, y);
    }

    @Override
    public void onStep(Game game, Player player) {
        // TODO Peut-être proposer de sauvegarder la partie ici ?
        // Sinon rien à faire ici
    }
}
