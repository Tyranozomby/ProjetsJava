package client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSendHandler extends Thread {

    private final Socket socket;

    private final DataOutputStream outputStream;

    public ClientSendHandler(Socket socket, DataOutputStream outputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        // Read from the console and send the message
        try {
            Scanner scanner = new Scanner(System.in);

            while (!socket.isClosed()) {
                String message = scanner.nextLine();
                outputStream.writeBytes(message.trim() + "\n");
            }
        } catch (Exception e) {
            System.err.println("Server closed the connection");
            this.interrupt();
        }
    }
}
