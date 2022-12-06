package server;

import util.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class Connection extends Thread {

    private final Logger LOGGER = Logger.getLogger(Connection.class.getName());

    private final Socket socket;

    private final ObjectOutputStream outputStream;

    private final BufferedReader inputStream;

    private final Server server;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String input = inputStream.readLine();
                if (input != null && !input.trim().equals("")) {
                    input = input.trim();
                    LOGGER.info("Received: \"" + input + "\" from " + socket.getLocalSocketAddress());
                    server.broadcast(input, this, Message.Type.MESSAGE);
                }
            }
        } catch (IOException e) {
            LOGGER.warning("Lost connection to client " + socket.getLocalSocketAddress());
        } finally {
            this.interrupt();
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        server.disconnect(this);
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public Socket getSocket() {
        return socket;
    }
}
