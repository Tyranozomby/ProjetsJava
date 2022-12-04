package game.world;

import game.Brewery;
import game.Weaponry;
import game.engine.items.Item;
import game.world.tile.StartTile;
import game.world.tile.Tile;
import game.world.tile.TileManager;

import java.util.ArrayList;
import java.util.Comparator;

public class Level {

    private final ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();

    private final String name;

    private final String description;

    private final ArrayList<Item> shopItems;

    public Level(int level, String[] lines) {
        shopItems = new ArrayList<>(Weaponry.getAll(level).stream().sorted(Comparator.comparingInt(Item::getPrice)).toList());
        shopItems.addAll(Brewery.getAll(level).stream().sorted(Comparator.comparingInt(Item::getPrice)).toList());

        String[] order = {"Name", "Description", "Map"};
        int index = 0;

        int mapY = 0;

        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder descriptionBuilder = new StringBuilder();

        for (String line : lines) {
            if (index < order.length && line.equals(order[index] + ":")) {
                index++;
            } else if (index > 0) {
                switch (order[index - 1]) {
                    case "Name" -> nameBuilder.append(line);
                    case "Description" -> descriptionBuilder.append(line).append("\n");
                    case "Map" -> {
                        tiles.add(new ArrayList<>());
                        for (int x = 0; x < line.split("").length; x++) {
                            char c = line.charAt(x);
                            tiles.get(mapY).add(TileManager.makeTile(c, x, mapY));
                        }
                        mapY++;
                    }
                }
            }
        }
        this.name = nameBuilder.toString().trim();
        this.description = descriptionBuilder.toString().trim();

        if (getStartTile() == null)
            throw new RuntimeException("No start tile found in level " + level);
    }

    public ArrayList<Item> getShopItems() {
        return shopItems;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public Position getStartTile() {
        for (int y = 0; y < tiles.size(); y++) {
            for (int x = 0; x < tiles.get(y).size(); x++) {
                if (getTile(x, y) instanceof StartTile) {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("Name: ").append(name).append("\n\n");
        toString.append("Description: ").append(description).append("\n\n");
        toString.append("Map: ").append("\n");
        for (ArrayList<Tile> tileRow : tiles) {
            for (Tile tile : tileRow) {
                toString.append(TileManager.getTileChar(tile));
            }
            toString.append("\n");
        }
        return toString.toString();
    }

    public Tile getTile(int x, int y) {
        return tiles.get(y).get(x);
    }

    public Tile getTile(Position playerPosition) {
        return getTile(playerPosition.getX(), playerPosition.getY());
    }

    public int getHeight() {
        return tiles.size();
    }

    public int getWidth() {
        return tiles.stream().map(ArrayList::size).max(Integer::compareTo).orElse(0);
    }
}
