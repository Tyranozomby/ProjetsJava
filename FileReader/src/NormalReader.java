public class NormalReader extends TextReader {

    public NormalReader(String path) {
        super(path);
    }

    public static void main(String[] args) {
        String path = DIRECTORY + "normal.txt";

        if (args.length >= 1) {
            path = args[0];
        } else {
            System.out.println("Aucun chemin donné, utilisation du chemin par défaut: " + path + "\n");
        }

        NormalReader normalReader = new NormalReader(path);

        try {
            System.out.println(normalReader.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Just return the content of the file
     *
     * @param content The content of the file
     * @return The content of the file
     */
    @Override
    protected String process(String content) {
        return content;
    }
}
