package Bataille;

/**
 * Récupéré sur StackOverflow
 *
 * @see <a href="https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println">Lien StackOverflow</a>
 */
public class ConsoleColors {

    public static final String RESET = "\033[0m";  // Text Reset

    // Effects
    public static final String BOLD = "\033[1m";
    public static final String ITALIC = "\033[3m";
    public static final String UNDERLINE = "\033[4m";

    // Regular Colors
    public static final String RED = "\033[0;31m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String GREEN_UNDERLINED = "\033[4;32m";
}