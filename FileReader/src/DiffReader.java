import java.io.IOException;

public class DiffReader extends TextReader {

    public static final String PLUS = "+";

    public static final String MINUS = "-";

    private final NormalReader normalReader;

    public DiffReader(String path, String path2) {
        super(path);
        this.normalReader = new NormalReader(path2);
    }

    public static void main(String[] args) {
        // TODO Faire fonctionner à nouveau mais mieux si possible
        System.out.println("March pô, déso pas déso");

//        String path = "diff1.txt";
//        String path2 = "diff2.txt";
//
//        if (args.length >= 2) {
//            path = args[0];
//            path2 = args[1];
//        } else {
//            System.out.println("Aucun chemin donné, utilisation des chemins par défaut: " + path + " et " + path2 + "\n");
//        }
//
//        DiffReader diffReader = new DiffReader(DIRECTORY + path, DIRECTORY + path2);
//
//        try {
//            System.out.println(diffReader.read());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

    @Override
    protected String process(String content) throws IOException {
        // Line we're at in each file
        int index1 = 0;
        int index2 = 0;

        String[] lines1 = content.split("\r?\n");
        String[] lines2 = normalReader.read().split("\r?\n");

        StringBuilder result = new StringBuilder();

        while (index1 < lines1.length && index2 < lines2.length) {
            String line1 = lines1[index1];
            String line2 = lines2[index2];

            if (line1.equals(line2)) {
                result.append(index1).append("=").append(index2).append(" ").append(line1).append("\n");
            } else {
                int indexOf;

                if ((indexOf = indexOf(lines2, line1, index2)) != -1) {
                    addChange(result, PLUS, lines2[indexOf], index1, index2);
                    index2 = indexOf;
//                } else if ((indexOf = indexOf(lines1, line2, index1)) != -1) {
//                    addChange(result, MINUS, lines1[indexOf], index1, index2);
//                    index1 = indexOf;
                } else {
                    addChange(result, MINUS, line1, index1, index2);
                    addChange(result, PLUS, line2, index1, index2);
                }
            }
            index1++;
            index2++;
        }

        if (index1 < lines1.length) {
            for (int i = index1; i < lines1.length; i++)
                addChange(result, MINUS, lines1[i], i, index2);
        } else if (index2 < lines2.length) {
            for (int i = index2; i < lines2.length; i++)
                addChange(result, PLUS, lines2[i], index1, i);
        }

        return result.toString();
    }

    private int indexOf(String[] lines, String line, int startsAt) {
        for (int i = startsAt; i < lines.length; i++) {
            if (lines[i].equals(line)) {
                return i;
            }
        }
        return -1;
    }

    private void addChange(StringBuilder builder, String str, String line, int index1, int index2) {
        builder.append(index1).append(str).append(index2).append(" ").append(line).append("\n");
    }
}
