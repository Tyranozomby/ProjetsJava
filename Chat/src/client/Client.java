package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {

    public static final int DEFAULT_PORT = 5000;

    private final int port;

    public Client(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number, using default port " + DEFAULT_PORT);
            }
        } else {
            System.out.println("No port number specified, using default port " + DEFAULT_PORT);
        }

        new Client(port).connect();
    }

    private void connect() {
        try (Socket socket = new Socket("localhost", port)) {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            Thread sendingHandler = new ClientSendHandler(socket, outputStream);
            Thread receivingHandler = new ClientReceiveHandler(socket, inputStream);

            sendingHandler.start();
            receivingHandler.start();

            // Keep the main thread alive until the client disconnects
            sendingHandler.join();
            receivingHandler.join();

        } catch (IOException | InterruptedException e) {
            System.err.println("Server closed the connection");
        } finally {
            System.err.println("Bye bye!");
        }
    }
}
