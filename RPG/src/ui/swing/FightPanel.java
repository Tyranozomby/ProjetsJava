package ui.swing;

import game.Stats;
import game.engine.effects.Effect;
import game.engine.effects.RandomChanceEffect;
import game.engine.entities.Entity;
import game.engine.entities.Monster;
import game.engine.entities.Player;
import game.engine.fight.Fight;
import game.engine.fight.actions.FightAction;
import game.engine.fight.actions.attacks.AutoTargetAttackFightAction;
import game.engine.fight.actions.attacks.SingleTargetAttackFightAction;
import game.engine.fight.attacks.Attack;
import game.engine.fight.attacks.AutoTargetAttack;
import game.engine.fight.attacks.SingleTargetAttack;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class FightPanel extends JPanel {

    private final GamePanel gamePanel;

    private final HashMap<Effect, JLabel> waitingEffects = new HashMap<>();

    private Fight fight = null;

    private Monster monster1;

    private Monster monster2;

    private JPanel centralPanel;

    public FightPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    private void initComponents() {
        JLabel monsterLabel1 = new JLabel();
        JLabel monsterLabel2 = new JLabel();

        JProgressBar monsterLife1 = new JProgressBar();
        JProgressBar monsterLife2 = new JProgressBar();

        JPanel effectsPanel1 = new JPanel();
        JPanel effectsPanel2 = new JPanel();

        JLabel turnIndicator = new JLabel();

        centralPanel = new JPanel();

        JLabel turnLabel = new JLabel();

        JPanel playerEffectsPanel = new JPanel();
        JProgressBar playerLife = new JProgressBar();

        boolean twoMonsters = fight.getMonsters().size() > 1;
        Player player = fight.getPlayer();
        monster1 = fight.getMonsters().get(0);
        monster2 = twoMonsters ? fight.getMonsters().get(1) : null;

        //======== this ========
        setLayout(new GridBagLayout());

        if (!twoMonsters) {
            ((GridBagLayout) getLayout()).columnWidths = new int[]{200, 300, 200};
            ((GridBagLayout) getLayout()).columnWeights = new double[]{1.0, 1.0, 1.0};

        } else {
            ((GridBagLayout) getLayout()).columnWidths = new int[]{200, 150, 150, 200};
            ((GridBagLayout) getLayout()).columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
        }

        ((GridBagLayout) getLayout()).rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0};

        //---- monsterLabel1 ----
        monsterLabel1.setText(monster1.getName() + " (lvl " + monster1.getLevel() + ")");
        monsterLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        monsterLabel1.setFont(new Font("Segoe UI", Font.PLAIN, 30));
//        monsterLabel1.setToolTipText(monster1.getDescription());
        add(monsterLabel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- monsterLabel2 ----
        if (twoMonsters) {
            monsterLabel2.setText(monster2.getName() + " (lvl " + monster2.getLevel() + ")");
            monsterLabel2.setHorizontalAlignment(SwingConstants.CENTER);
            monsterLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 30));
//            monsterLabel2.setToolTipText(monster2.getDescription());
            add(monsterLabel2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
        }

        //---- monsterLife1 ----
        monsterLife1.setValue(monster1.getHealth());
        monsterLife1.setForeground(new Color(0x00e461));
        monsterLife1.setStringPainted(true);
        monsterLife1.setMaximum(monster1.getStat(Stats.MAX_HEALTH));
        add(monsterLife1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- monsterLife2 ----
        if (twoMonsters) {
            monsterLife2.setValue(monster2.getHealth());
            monsterLife2.setForeground(new Color(0x00e461));
            monsterLife2.setStringPainted(true);
            monsterLife2.setMaximum(monster2.getStat(Stats.MAX_HEALTH));
            add(monsterLife2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
        }

        //======== effectsPanel1 ========
        {
            effectsPanel1.setLayout(new FlowLayout());

            //---- effect ----
            for (Effect effect : monster1.getEffects()) {
                JLabel effectLabel = new JLabel();
                effectLabel.setText(effect.getName());
                effectLabel.setToolTipText(effect.getDescription());
                effectsPanel1.add(effectLabel);
            }
        }
        add(effectsPanel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== effectsPanel2 ========
        if (twoMonsters) {
            effectsPanel2.setLayout(new FlowLayout());

            //---- effect ----
            for (Effect effect : monster2.getEffects()) {
                JLabel effectLabel = new JLabel();
                effectLabel.setText(effect.getName());
                effectLabel.setToolTipText(effect.getDescription());
                effectsPanel2.add(effectLabel);
            }

            add(effectsPanel2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
        }

        //---- turnIndicator ----
        turnIndicator.setText(fight.getPlaying().getName() + "'s turn");
        turnIndicator.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        turnIndicator.setHorizontalAlignment(SwingConstants.CENTER);
        add(turnIndicator, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //======== centralPanel ========
        centralPanel.setLayout(new GridBagLayout());

        if (player == fight.getPlaying()) {
            List<Attack> attacks = player.getWeapon().getAttacks();
            for (int i = 0; i < attacks.size(); i++) {
                Attack attack = attacks.get(i);
                JButton attackButton = new JButton();
                attackButton.setText(attack.getName());
                attackButton.setFont(new Font("Segoe UI", Font.PLAIN, 24));
                attackButton.setToolTipText(attack.getDescription() + " (" + attack.getPhysicalDamage() + " : " + attack.getMagicalDamage() + ")");

                attackButton.addActionListener(e -> {
                    FightAction action;

                    if (attack instanceof SingleTargetAttack singleTargetAttack) {
                        action = new SingleTargetAttackFightAction(singleTargetAttack, getTarget());
                    } else {
                        action = new AutoTargetAttackFightAction(((AutoTargetAttack) attack));
                    }

                    gamePanel.setFightAction(action);
                });
                centralPanel.add(attackButton, new GridBagConstraints(0, i, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
        }

        add(centralPanel, new GridBagConstraints(1, 3, fight.getMonsters().size(), 1, 0.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- turnLabel ----
        turnLabel.setText("Turn " + fight.getTurn());
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        add(turnLabel, new GridBagConstraints(3 - (twoMonsters ? 0 : 1), 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        //======== playerEffectsPanel ========
        {
            playerEffectsPanel.setLayout(new FlowLayout());

            //---- effect ----
            for (Effect effect : player.getEffects()) {
                JLabel effectLabel = new JLabel();
                effectLabel.setText(effect.getName());
                effectLabel.setToolTipText(effect.getDescription());
                playerEffectsPanel.add(effectLabel);
            }
        }
        add(playerEffectsPanel, new GridBagConstraints(1, 4, fight.getMonsters().size(), 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        //---- playerLife ----
        playerLife.setStringPainted(true);
        playerLife.setMaximum(player.getStat(Stats.MAX_HEALTH));
        playerLife.setValue(player.getHealth());
        add(playerLife, new GridBagConstraints(1, 5, fight.getMonsters().size(), 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 12, 5), 0, 20));
    }

    public void reload() {
        if (fight != null) {
            removeAll();
            initComponents();
            revalidate();
            repaint();
        }
    }

    public void setFight(Fight fight) {
        this.fight = fight;

        if (fight != null) {
            initComponents();
        }
    }

    public Entity getTarget() {
        if (monster2 == null) {
            return monster1;
        } else {
            AtomicReference<Monster> target = new AtomicReference<>();

            // Open a dialog to choose the target
            JDialog dialog = new JDialog();
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setTitle("Choose a target");
            dialog.setLayout(new GridBagLayout());

            JButton monster1Button = new JButton();
            monster1Button.setText(monster1.getName());
            monster1Button.addActionListener(e -> {
                dialog.dispose();
                target.set(monster1);
            });
            dialog.add(monster1Button, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(5, 5, 5, 5), 0, 0));

            JButton monster2Button = new JButton();
            monster2Button.setText(monster2.getName());
            monster2Button.addActionListener(e -> {
                dialog.dispose();
                target.set(monster2);
            });
            dialog.add(monster2Button, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(5, 5, 5, 5), 0, 0));

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            return target.get();
        }
    }

    public void setAttack(Attack attack, Entity attacker, Entity receiver) {
        centralPanel.removeAll();
        waitingEffects.clear();

        JLabel attackLabel = new JLabel(attack.getName());
        attackLabel.setToolTipText(attack.getDescription());
        attackLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        attackLabel.setHorizontalAlignment(SwingConstants.CENTER);

        centralPanel.add(attackLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        int physicalDamage = Math.max(0, attack.getPhysicalDamage() + attacker.getStat(Stats.PHYSICAL_DAMAGE) - receiver.getStat(Stats.PHYSICAL_DEFENSE));
        int magicalDamage = Math.max(0, attack.getMagicalDamage() + attacker.getStat(Stats.MAGICAL_DAMAGE) - receiver.getStat(Stats.MAGICAL_DEFENSE));

        JLabel damageLabel;
        if (physicalDamage == 0 && magicalDamage == 0) {
            damageLabel = new JLabel("No damage");
        } else if (physicalDamage == 0) {
            damageLabel = new JLabel("Magical: " + magicalDamage + " = Total: " + magicalDamage);
        } else if (magicalDamage == 0) {
            damageLabel = new JLabel("Physical: " + physicalDamage + " = Total: " + physicalDamage);
        } else {
            damageLabel = new JLabel("Physical: " + physicalDamage + " + Magical: " + magicalDamage + " = Total: " + (physicalDamage + magicalDamage));
        }

        damageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        damageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        centralPanel.add(damageLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        List<RandomChanceEffect> effects = attack.getEffects();
        for (int i = 0; i < effects.size(); i++) {
            RandomChanceEffect effect = effects.get(i);
            JLabel effectLabel = new JLabel(effect.getEffect().getName() + " (" + effect.getChance() * 100 + "%)");
            effectLabel.setToolTipText(effect.getEffect().getDescription());
            effectLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            effectLabel.setHorizontalAlignment(SwingConstants.CENTER);

            waitingEffects.put(effect.getEffect(), effectLabel);

            centralPanel.add(effectLabel, new GridBagConstraints(0, 2 + i, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }

        revalidate();
    }

    public void setEffectSuccess(Effect effect) {
        JLabel effectLabel = waitingEffects.get(effect);
        effectLabel.setText(effectLabel.getText() + " - Success");
        effectLabel.setForeground(Color.GREEN);

        revalidate();
    }
}
