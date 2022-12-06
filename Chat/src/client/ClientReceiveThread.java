package client;

import util.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiveThread extends Thread {

    private final Socket socket;

    private final ObjectInputStream inputStream;

    private final MessageHandler messageHandler;

    public ClientReceiveThread(Socket socket, ObjectInputStream inputStream, MessageHandler messageHandler) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.messageHandler = messageHandler;
    }

    @Override
    public void run() {
        // Read from the socket and print the message
        try {
            while (!socket.isClosed()) {
                Message message = (Message) inputStream.readObject();
                messageHandler.handle(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Server closed the connection");
        } finally {
            this.interrupt();
        }
    }
}
