package game.world.tile;

public class TileManager {

    public static Tile makeTile(char c, int x, int y) {
        return switch (c) {
            case ' ' -> new EmptyTile(x, y);
            case '*' -> new WallTile(x, y);
            case 'B' -> new BushTitle(x, y, .4, .9);
            case 'C' -> new ChestTile(x, y);
            case 'S' -> new StartTile(x, y);
            case 'E' -> new EndTile(x, y);
            default -> throw new IllegalArgumentException("Unknown tile type: " + c);
        };
    }


    /**
     * @param tile the tile to get the character for
     * @return one of the following ' ', '*', 'B', 'C', 'S', 'E'
     */
    public static char getTileChar(Tile tile) {
        Class<? extends Tile> tileClass = tile.getClass();
        return switch (tileClass.getSimpleName()) {
            case "EmptyTile" -> ' ';
            case "WallTile" -> '*';
            case "BushTitle" -> 'B';
            case "ChestTile" -> 'C';
            case "StartTile" -> 'S';
            case "EndTile" -> 'E';
            default -> throw new IllegalArgumentException("Unknown tileClass type: " + tileClass.getSimpleName());
        };
    }
}
