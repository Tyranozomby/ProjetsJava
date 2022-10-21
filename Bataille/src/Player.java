import java.util.ArrayList;
import java.util.Collections;

public class Player {

    private final String name;

    private final String color;

    private final ArrayList<Card> hand = new ArrayList<>();

    private final ArrayList<Card> sideStack = new ArrayList<>();

    public Player(String name, String color) {
        // Capitalize the player's name
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        this.color = color;
    }

    /**
     * @return the player's side cards list
     */
    public ArrayList<Card> getSideStack() {
        return sideStack;
    }

    /**
     * Remove the first card of the hand and return it
     *
     * @return the first card
     */
    public Card draw() {
        return hand.remove(0);
    }

    /**
     * @return name in purple
     */
    public String getColoredName() {
        return color + name + ConsoleColors.RESET;
    }

    /**
     * @return the hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Put all side cards in the hand and shuffle it
     */
    public void reloadHand() {
        hand.addAll(sideStack);
        sideStack.clear();
        Collections.shuffle(hand);
    }
}