/*
 * Created by JFormDesigner on Thu Nov 10 15:39:28 CET 2022
 */

package ui;

import game.PlayerClass;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainMenu extends JPanel {

    private final SwingUI swingUI;

    private ResourceBundle bundle;

    private JLabel titre;

    private JRadioButton radioGuerrier;

    private JRadioButton radioTank;

    private JRadioButton radioVoleur;

    private JRadioButton radioMage;

    private JLabel pseudoLabel;

    private JTextField pseudoField;

    private JButton startButton;

    public MainMenu(SwingUI swingUI) {
        this.swingUI = swingUI;
        initComponents();
    }

    private void startGame() {
        PlayerClass playerClass = null;
        if (radioGuerrier.isSelected()) {
            playerClass = PlayerClass.WARRIOR;
        } else if (radioTank.isSelected()) {
            playerClass = PlayerClass.TANK;
        } else if (radioVoleur.isSelected()) {
            playerClass = PlayerClass.THIEF;
        } else if (radioMage.isSelected()) {
            playerClass = PlayerClass.MAGE;
        }

        String playerName = pseudoField.getText();

        if (playerClass != null && !playerName.isEmpty()) {
            try {
                swingUI.startGame(playerClass, playerName);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez choisir une classe et un pseudo", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startGame(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            startGame();
        }
    }

    private void initComponents() {
        bundle = ResourceBundle.getBundle("strings", Locale.getDefault());
        titre = new JLabel();
        radioGuerrier = new JRadioButton();
        radioTank = new JRadioButton();
        radioVoleur = new JRadioButton();
        radioMage = new JRadioButton();
        pseudoLabel = new JLabel();
        pseudoField = new JTextField();
        startButton = new JButton();

        //======== this ========
        setBorder(new EmptyBorder(50, 50, 50, 50));
        setLayout(new GridBagLayout());

        //---- titre ----
//        titre.setText(bundle.getString("MainMenu.titre.text"));
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        add(titre, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 50, 0), 0, 0));

        //---- radioGuerrier ----
//        radioGuerrier.setText(bundle.getString("MainMenu.radioGuerrier.text"));
        radioGuerrier.setSelected(true);
        add(radioGuerrier, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 50, 30), 0, 0));

        //---- radioTank ----
//        radioTank.setText(bundle.getString("MainMenu.radioTank.text"));
        add(radioTank, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 50, 30), 0, 0));

        //---- radioVoleur ----
//        radioVoleur.setText(bundle.getString("MainMenu.radioVoleur.text"));
        add(radioVoleur, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 50, 30), 0, 0));

        //---- radioMage ----
//        radioMage.setText(bundle.getString("MainMenu.radioMage.text"));
        add(radioMage, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 50, 0), 0, 0));

        //---- pseudoLabel ----
//        pseudoLabel.setText(bundle.getString("MainMenu.pseudoLabel.text"));
        add(pseudoLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 50, 30), 0, 0));

        //---- pseudoField ----
        pseudoField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                startGame(e);
            }
        });
        add(pseudoField, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 50, 0), 0, 0));
        pseudoLabel.setLabelFor(pseudoField);

        //---- startButton ----
//        startButton.setText(bundle.getString("MainMenu.startButton.text"));
        startButton.addActionListener(e -> startGame());
        add(startButton, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 30), 0, 0));

        //---- radioGroup ----
        var radioGroup = new ButtonGroup();
        radioGroup.add(radioGuerrier);
        radioGroup.add(radioTank);
        radioGroup.add(radioVoleur);
        radioGroup.add(radioMage);

        setComponentsTextAndMnemonics();
    }

    private void setComponentsTextAndMnemonics() {
        bundle = ResourceBundle.getBundle("strings", Locale.getDefault());
        titre.setText(bundle.getString("MainMenu.titre.text"));
        radioGuerrier.setText(bundle.getString("MainMenu.radioGuerrier.text"));
        radioTank.setText(bundle.getString("MainMenu.radioTank.text"));
        radioVoleur.setText(bundle.getString("MainMenu.radioVoleur.text"));
        radioMage.setText(bundle.getString("MainMenu.radioMage.text"));
        pseudoLabel.setText(bundle.getString("MainMenu.pseudoLabel.text"));
        startButton.setText(bundle.getString("MainMenu.startButton.text"));

        radioGuerrier.setMnemonic(radioGuerrier.getText().charAt(0));
        radioTank.setMnemonic(radioTank.getText().charAt(0));
        radioVoleur.setMnemonic(radioVoleur.getText().charAt(0));
        radioMage.setMnemonic(radioMage.getText().charAt(0));
        pseudoLabel.setDisplayedMnemonic(pseudoLabel.getText().charAt(0));
        startButton.setMnemonic(startButton.getText().charAt(0));
    }
}
