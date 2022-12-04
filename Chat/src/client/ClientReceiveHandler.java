package client;

import both.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiveHandler extends Thread {

    private final Socket socket;

    private final ObjectInputStream inputStream;

    public ClientReceiveHandler(Socket socket, ObjectInputStream inputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        // Read from the socket and print the message
        try {
            while (!socket.isClosed()) {
                Message message = (Message) inputStream.readObject();
                MessagePrinter.print(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Server closed the connection");
            this.interrupt();
        }
    }
}
