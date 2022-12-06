package client;

import util.Message;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsoleMessageHandler implements MessageHandler {

    public void handle(Message message) {
        String time = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.ofNanoOfDay(message.timestamp()));
        switch (message.type()) {
            case MESSAGE -> System.out.println("[" + time + "] - #" + message.id() + ": " + message.content());
            case JOIN -> System.out.println("User #" + message.id() + " just joined the chat");
            case LEAVE -> System.out.println("User #" + message.id() + " just left the chat");
            case WELCOME -> System.out.println("----- Welcome to the server! You are user #" + message.id() + " -----");
        }
    }
}
