package ui.swing;

import game.Stats;
import game.engine.entities.Player;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel {

    private final Player player;

    public PlayerPanel(Player player) {
        this.player = player;
        initComponent();
    }

    private void initComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Player"));
        // Health
        this.add(new JLabel("Health: " + player.getHealth()));
        // Physical Damage
        this.add(new JLabel("Physical Damage: " + player.getStat(Stats.PHYSICAL_DAMAGE)));
        // Magical Damage
        this.add(new JLabel("Magical Damage: " + player.getStat(Stats.MAGICAL_DAMAGE)));
        // Physical Defense
        this.add(new JLabel("Physical Defense: " + player.getStat(Stats.PHYSICAL_DEFENSE)));
        // Magical Defense
        this.add(new JLabel("Magical Defense: " + player.getStat(Stats.MAGICAL_DEFENSE)));
        // Speed
        this.add(new JLabel("Speed: " + player.getStat(Stats.SPEED)));
        // Gold
        this.add(new JLabel("Gold: " + player.getGold()));

    }

    public void reload() {
        removeAll();
        initComponent();
        revalidate();
        repaint();
    }
}
