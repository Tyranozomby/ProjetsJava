package game.world;

import game.Action;
import game.engine.Game;
import game.engine.items.Item;
import game.world.tile.EmptyTile;
import game.world.tile.Tile;
import game.world.tile.WallTile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Map {

    private final Level[] levels;

    private Position position;

    private int currentLevel = 1;

    public Map(String path) throws IOException {
        File[] mapFiles = new File(path).listFiles();
        if (mapFiles == null) {
            throw new IOException("No map files found");
        }

        // Filter to keep only files with .map extension
        mapFiles = Arrays.stream(mapFiles)
                .filter(file -> file.getName().endsWith(".map"))
                .toArray(File[]::new);

        int levelCount = mapFiles.length;

        levels = new Level[levelCount];

        // Read each file and create a level
        for (int i = 0; i < levelCount; i++) {
            levels[i] = new Level(i + 1, Files.readString(Paths.get(mapFiles[i].getPath())).split("\r?\n"));
        }

        // Set player position to the start tile of the first level
        position = levels[0].getStartTile();
    }

    public int getLevelValue() {
        return currentLevel;
    }

    public Level getCurrentLevel() {
        return levels[currentLevel - 1];
    }

    public ArrayList<Item> getShopItems() {
        return levels[currentLevel - 1].getShopItems();
    }

    public boolean goNextLevel() {
        if (currentLevel < levels.length) {
            currentLevel++;
            position = levels[currentLevel - 1].getStartTile();
            Game.UI_CALLBACKS.onNextLevel(currentLevel);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        for (int i = 0; i < levels.length; i++) {
            Level level = levels[i];
            toString.append("Level ").append(i + 1).append("\n\n");
            toString.append(level.toString());
        }
        return toString.toString();
    }

    public void removeCurrentTile() {
        ArrayList<Tile> row = getCurrentLevel().getTiles().get(position.getY());
        row.set(position.getX(), new EmptyTile(row.get(position.getX()).getX(), row.get(position.getX()).getY()));
    }

    public Position getPosition() {
        return position;
    }

    public Tile move(Action.Move direction) {
        Level currentLevel = getCurrentLevel();
        if (direction == Action.Move.UP && position.getY() > 0 && !(currentLevel.getTiles().get(position.getY() - 1).get(position.getX()) instanceof WallTile)) {
            position.moveUp();
        } else if (direction == Action.Move.DOWN && position.getY() < currentLevel.getTiles().size() - 1 && !(currentLevel.getTiles().get(position.getY() + 1).get(position.getX()) instanceof WallTile)) {
            position.moveDown();
        } else if (direction == Action.Move.LEFT && position.getX() > 0 && !(currentLevel.getTiles().get(position.getY()).get(position.getX() - 1) instanceof WallTile)) {
            position.moveLeft();
        } else if (direction == Action.Move.RIGHT && position.getX() < currentLevel.getTiles().get(position.getY()).size() - 1 && !(currentLevel.getTiles().get(position.getY()).get(position.getX() + 1) instanceof WallTile)) {
            position.moveRight();
        }

        return getCurrentLevel().getTiles().get(position.getY()).get(position.getX());
    }
}
