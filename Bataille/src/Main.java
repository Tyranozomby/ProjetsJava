import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Ask for a string in the console to name the players
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le nom du premier joueur :");
        String name1 = scanner.nextLine();
        System.out.println("Entrez le nom du second joueur :");
        String name2 = scanner.nextLine();

        Player player1 = new Player(name1, ConsoleColors.YELLOW);
        Player player2 = new Player(name2, ConsoleColors.BLUE);
        init(player1, player2);

        // Stack of cards used to store cards in case of a draw
        ArrayList<Card> centerStack = new ArrayList<>();

        System.out.println("Début de la partie");
        System.out.println(player1.getColoredName() + " vs " + player2.getColoredName());

        int round = 1;
        boolean end = false;
        Player winner = null;
        while (!end) {
            System.out.println("\n" + ConsoleColors.BOLD + ConsoleColors.ITALIC + "————— Round " + round + " —————" + ConsoleColors.RESET);

            // Draw a card for each player
            Card card1 = player1.draw();
            Card card2 = player2.draw();

            // Print the cards of each player
            System.out.println(player1.getColoredName() + " joue " + card1);
            System.out.println(player2.getColoredName() + " joue " + card2);

            // Compare the cards. The player with the highest card wins the round.
            int compare = card1.equals(card2);
            if (compare == 0) {
                // If the cards are equal, it's a draw. The cards are put in the middle.
                System.out.println("\033[1mÉgalité\033[0m");
                centerStack.addAll(Arrays.asList(card1, card2));
            } else if (compare > 0) {
                // If the first card is higher, the first player wins the round.
                System.out.println(ConsoleColors.BOLD + ConsoleColors.UNDERLINE + player1.getColoredName() + " " + ConsoleColors.UNDERLINE + "gagne le round" + ConsoleColors.RESET);
                player1.getSideStack().addAll(Arrays.asList(card1, card2));
                player1.getSideStack().addAll(centerStack);
                centerStack.clear();
            } else {
                // If the second card is higher, the second player wins the round.
                System.out.println(ConsoleColors.BOLD + ConsoleColors.UNDERLINE + player2.getColoredName() + " " + ConsoleColors.UNDERLINE + "gagne le round" + ConsoleColors.RESET);
                player2.getSideStack().addAll(Arrays.asList(card1, card2));
                player2.getSideStack().addAll(centerStack);
                centerStack.clear();
            }

            // Show the number of cards in the hand and side cards of each player
            System.out.println(player1.getColoredName() + " a " + player1.getHand().size() + " cartes en main et " + player1.getSideStack().size() + " cartes de côté");
            System.out.println(player2.getColoredName() + " a " + player2.getHand().size() + " cartes en main et " + player2.getSideStack().size() + " cartes de côté");

            // Check if any player has no more cards in hand
            if (player1.getHand().isEmpty() || player2.getHand().isEmpty()) {

                // Reload the cards in the hand of player 1 or end the game if there are no more cards in the side cards
                if (player1.getHand().isEmpty()) {
                    if (player1.getSideStack().size() != 0) {
                        player1.reloadHand();
                    } else if (player1.getSideStack().size() == 0) {
                        // If the player 1 has no more cards in the side cards, the player 2 wins the game
                        end = true;
                        winner = player2;
                    }
                }

                // Reload the cards in the hand of player 2 or end the game if there are no more cards in the side cards
                if (player2.getHand().isEmpty()) {
                    if (player2.getSideStack().size() != 0) {
                        player2.reloadHand();
                    } else if (player2.getSideStack().size() == 0) {
                        // If the player 2 has no more cards in the side cards, the player 1 wins the game
                        end = true;
                        winner = player1;
                    }
                }
            }

            round++;
        }

        // Print the winner of the game and the number of rounds
        System.out.println("\n" + ConsoleColors.GREEN_UNDERLINED + "Victoire" + ConsoleColors.RESET + " de " + winner.getColoredName() + " en " + ConsoleColors.RED + --round + " rounds" + ConsoleColors.RESET);
    }

    /**
     * Initialize the game by equally distributing the cards to the players
     *
     * @param player1 the first player
     * @param player2 the second player
     */
    private static void init(Player player1, Player player2) {
        ArrayList<Card> cards = createDeck();

        for (int i = 0; i < cards.size() - 1; i += 2) {
            player1.getHand().add(cards.get(i));
            player2.getHand().add(cards.get(i + 1));
        }
    }

    /**
     * Create a deck of cards
     *
     * @return a shuffled deck of cards
     */
    private static ArrayList<Card> createDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 2; i <= 14; i++) {
            for (int j = 1; j <= 4; j++) {
                cards.add(new Card(i, j));
            }
        }

        Collections.shuffle(cards);
        return cards;
    }
}
