package server;

import both.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.logging.Logger;

public class Server extends LogFormatter {

    public static final int DEFAULT_PORT = 5000;

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final HashMap<Integer, Connection> clients = new HashMap<>();

    private final int port;

    private int clientCount = 0;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        LogFormatter.initLogging();

        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid port number, using default port " + DEFAULT_PORT);
            }
        } else {
            LOGGER.warning("No port number specified, using default port " + DEFAULT_PORT);
        }

        Server server = new Server(port);
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            LOGGER.info("Server is listening on port " + port + " and waiting for clients to connect...");

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);

                clientCount++;
                clients.put(clientCount, connection);

                // Create a thread for each client that connects to the server
                connection.start();

                LOGGER.info("Client #" + clientCount + " connected to server (total: " + clients.size() + ")");

                // Send welcome message to the client and inform the other clients
                sendTo(connection, null, Message.Type.WELCOME);
                broadcast(Integer.toString(clientCount), connection, Message.Type.JOIN);
            }
        } catch (IOException e) {
//            System.err.println("server.Server exception: " + e.getMessage());
            LOGGER.severe("Server exception: " + e.getMessage());
        }
    }

    private int getClientId(Connection connection) {
        for (int id : clients.keySet()) {
            if (clients.get(id) == connection) {
                return id;
            }
        }
        return -1;
    }

    public void disconnect(Connection connection) {
        try {
            int id = getClientId(connection);
            Socket socket = connection.getSocket();
            LOGGER.info("Client #" + id + " disconnected: " + socket.getLocalSocketAddress());
            broadcast(Integer.toString(id), connection, Message.Type.LEAVE);

            synchronized (clients) {
                clients.remove(id);
            }

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadcast(String info, Connection excludedConnection, Message.Type type) {
        for (Connection connection : clients.values()) {
            if (connection != excludedConnection) {
                sendTo(connection, info, type);
            }
        }
    }

    private void sendTo(Connection connection, String info, Message.Type type) {
        try {
            int id = getClientId(connection);
            if (type == Message.Type.JOIN || type == Message.Type.LEAVE) {
                id = Integer.parseInt(info);
            }
            Message msg = new Message(info, type, id, LocalTime.now().toSecondOfDay());
            // send serialized message
            ObjectOutputStream output = connection.getOutputStream();
            output.writeObject(msg);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
