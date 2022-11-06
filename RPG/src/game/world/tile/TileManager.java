package game.world.tile;

public class TileManager {

    public static Tile makeTile(char c) {
        return switch (c) {
            case ' ' -> new EmptyTile();
            case '*' -> new WallTile();
            case 'B' -> new BushTitle(.6, .9);
            case 'C' -> new ChestTile();
            case 'S' -> new StartTile();
            case 'E' -> new EndTile();
            default -> throw new IllegalArgumentException("Unknown tile type: " + c);
        };
    }


    public static char getTileChar(Class<? extends Tile> tileClass) {
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
