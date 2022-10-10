package FileReader.Readers;

import FileReader.TextReader;

public class ReverseReader extends TextReader {

    public ReverseReader(String path) {
        super(path);
    }

    public static void main(String[] args) {
        String path = "reverse.txt";

        if (args.length >= 1) {
            path = args[0];
        } else {
            System.out.println("Aucun chemin donné, utilisation du chemin par défaut: " + path + "\n");
        }

        ReverseReader reverseReader = new ReverseReader(DIRECTORY + path);

        try {
            System.out.println(reverseReader.read());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reverse the content of the file
     *
     * @param content The content of the file
     * @return The reversed content
     */
    @Override
    protected String process(String content) {
        String[] lines = content.split("\r?\n");
        for (int i = 0; i < lines.length / 2; i++) {
            String temp = lines[i];
            lines[i] = lines[lines.length - i - 1];
            lines[lines.length - i - 1] = temp;
        }
        return String.join("\n", lines);
    }
}
