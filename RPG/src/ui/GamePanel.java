package ui;

import game.engine.Game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final SwingUI swingUI;

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel mapGrid;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


    public GamePanel(SwingUI swingUI, Game game) {
        this.swingUI = swingUI;
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        mapGrid = new JPanel();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 1.0};

        //======== mapGrid ========
        {
            mapGrid.setBorder(null);
            mapGrid.setLayout(new GridLayout(10, 10, 2, 2));
        }
        add(mapGrid, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 5), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
}
