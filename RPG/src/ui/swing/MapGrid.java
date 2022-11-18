package ui.swing;

import game.world.Level;
import game.world.Map;
import game.world.tile.Tile;
import game.world.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MapGrid extends JPanel {

    public static final int TILE_SIZE = 50;

    private final Map map;

    public MapGrid(Map map) {
        this.map = map;
        initComponent();
    }

    private void initComponent() {
        Level level = map.getCurrentLevel();
        int height = level.getTiles().size();
        int width = level.getTiles().stream().mapToInt(ArrayList::size).max().orElse(0);

        setLayout(new GridBagLayout());
        int y = 0;
        for (ArrayList<Tile> rows : level.getTiles()) {
            int x = 0;
            for (Tile tile : rows) {
                JLabel label = new JLabel();
                label.setOpaque(true);
//                label.setText(String.valueOf(TileManager.getTileChar(tile)));
                switch (TileManager.getTileChar(tile)) {
                    case ' ' -> label.setBackground(new Color(0, 125, 0));
                    case 'B' -> label.setBackground(Color.GREEN);
                    case '*' -> label.setBackground(Color.BLACK);
                    case 'S' -> label.setBackground(Color.RED);
                    case 'C' -> label.setBackground(Color.BLUE);
                    case 'E' -> label.setBackground(Color.WHITE);
                }

                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                add(label, new GridBagConstraints(x, y, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                x++;
            }
            y++;
        }

        setPreferredSize(new Dimension(width * TILE_SIZE, height * TILE_SIZE));
    }
}
