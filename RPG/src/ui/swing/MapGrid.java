package ui.swing;

import game.Action;
import game.world.Level;
import game.world.Map;
import game.world.Position;
import game.world.tile.Tile;
import game.world.tile.TileManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MapGrid extends JPanel {

    public static final int TILE_SIZE = 50;

    private final Map map;

    private final GamePanel gamePanel;

    public MapGrid(Map map, GamePanel gamePanel) {
        this.map = map;
        this.gamePanel = gamePanel;
        initComponent();
    }

    private void initComponent() {
        this.setLayout(new GridBagLayout());

        Level level = map.getCurrentLevel();

        int y = 0;
        for (ArrayList<Tile> rows : level.getTiles()) {
            int x = 0;
            for (Tile tile : rows) {
                JLabel label = new JLabel();
                label.setOpaque(true);

                Position playerPosition = map.getPosition();
                if (x == playerPosition.getX() && y == playerPosition.getY()) {
                    label.setText("•");
                    label.setFont(new Font("Arial", Font.BOLD, 60));
                    label.setForeground(Color.BLACK);
                }

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
                label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                label.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                int finalX = x;
                int finalY = y;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int diffX = finalX - playerPosition.getX();
                        int diffY = finalY - playerPosition.getY();

                        if (Math.abs(diffX) <= 1 && Math.abs(diffY) <= 1) {
                            if (finalX != playerPosition.getX() || finalY != playerPosition.getY()) {
                                Action.Move direction = null;

                                if (diffX == 1 && diffY == 0) {
                                    direction = Action.Move.RIGHT;
                                } else if (diffX == -1 && diffY == 0) {
                                    direction = Action.Move.LEFT;
                                } else if (diffX == 0 && diffY == 1) {
                                    direction = Action.Move.DOWN;
                                } else if (diffX == 0 && diffY == -1) {
                                    direction = Action.Move.UP;
                                }

                                gamePanel.setAction(new game.Action(direction, null));
                            }
                        }
                    }
                });

                this.add(label, new GridBagConstraints(x, y, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
                x++;
            }
            y++;
        }
    }

    public void reload() {
        removeAll();
        initComponent();
        revalidate();
    }
}
