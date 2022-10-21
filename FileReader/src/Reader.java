import java.io.IOException;

public interface Reader {

    /**
     * Directory where the files are located
     */
    String DIRECTORY = "files/";

    /**
     * Read the file
     *
     * @return The content of the file depending on the reader
     * @throws IOException If the file doesn't exist
     */
    String read() throws IOException;
}
