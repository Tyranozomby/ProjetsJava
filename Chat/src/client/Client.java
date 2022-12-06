package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {

    public static final int DEFAULT_PORT = 5000;

    private final int port;

    private final MessageHandler messageHandler;

    public Client(int port, MessageHandler messageHandler) {
        this.port = port;
        this.messageHandler = messageHandler;
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

        new Client(port, new ConsoleMessageHandler()).connect();
    }

    private void connect() {
        try (Socket socket = new Socket("localhost", port)) {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            Thread receivingHandler = new ClientReceiveThread(socket, inputStream, messageHandler);
            Thread sendingHandler = new ClientSendThread(socket, outputStream);

            receivingHandler.start();
            sendingHandler.start();

            // Keep the main thread alive until the connection is closed
            receivingHandler.join();

        } catch (IOException e) {
            System.err.println("An error occurred while connecting to the server: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException ignored) {
        }
        System.err.println("Bye bye!");
        System.exit(1);
    }
}
