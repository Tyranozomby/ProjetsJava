import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class TextReader implements Reader {

    /**
     * Path of the file
     */
    private final String path;

    public TextReader(String path) {
        this.path = path;
    }

    /**
     * Read the file and process it
     *
     * @return The processed content of the file
     * @throws IOException If the file doesn't exist
     */
    @Override
    public String read() throws IOException {
        // Get the content of the file
        String content = new String(Files.readAllBytes(Paths.get(path)));
        System.out.println("Lecture du fichier " + content);
        return process(content);
    }

    /**
     * Process the content of the file
     *
     * @param content The content of the file
     * @return The processed content of the file
     * @throws IOException If the file doesn't exist
     */
    protected abstract String process(String content) throws IOException;
}
