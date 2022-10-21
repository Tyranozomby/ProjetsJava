public class PalindromeReader extends TextReader {

    public PalindromeReader(String path) {
        super(path);
    }

    public static void main(String[] args) {
        String path = DIRECTORY + "palindrome.txt";

        if (args.length >= 1) {
            path = args[0];
        } else {
            System.out.println("Aucun chemin donné, utilisation du chemin par défaut: " + path + "\n");
        }

        PalindromeReader palindromeReader = new PalindromeReader(path);

        try {
            System.out.println(palindromeReader.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Invert each line of the file
     *
     * @param content The content of the file
     * @return The content of the file with each line inverted
     */
    @Override
    protected String process(String content) {
        String[] lines = content.split("\r?\n");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new StringBuilder(lines[i]).reverse().toString();
        }
        return String.join("\n", lines);
    }
}