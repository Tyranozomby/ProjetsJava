package Bataille;

public class Card {
    public static final String[] COLORS_NAME = {"Pique", "Coeur", "Carreau", "Trèfle"};

    public static final String[] VALUES_NAME = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi", "As"};

    /**
     * The card's value (2 to 14)
     */
    private final int value;

    /**
     * The card's color (1 to 4)
     * Useless when it comes to the game
     * Only for printing
     */
    private final int color;


    /**
     * @param value the card's value
     * @param color the card's color
     */
    public Card(int value, int color) {
        this.value = value;
        this.color = color;
    }


    /**
     * @param c the card to compare
     * @return 1 if the card is greater than the given card, -1 if it's lower, 0 if they are equal
     */
    public int equals(Card c) {
        if (c == null) {
            return 1;
        }
        return Integer.compare(this.value, c.value);
    }

    @Override
    public String toString() {
        return VALUES_NAME[value - 2] + " de " + COLORS_NAME[color - 1];
    }
}
