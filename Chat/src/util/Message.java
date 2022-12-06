package util;

import java.io.Serializable;

/**
 * A message sent between the server and the clients.
 *
 * @param content   The content of the message when the type is MESSAGE
 * @param type      The type of the message
 * @param id        The id of the client that sent the message or of the client that joined/left the chat
 * @param timestamp The timestamp of the message in nanoseconds
 */
public record Message(String content, Type type, int id, long timestamp) implements Serializable {

    public enum Type implements Serializable {
        /**
         * A message sent by a client
         */
        MESSAGE,
        /**
         * A message sent to inform that a client joined the chat
         */
        JOIN,
        /**
         * A message sent to inform that a client left the chat
         */
        LEAVE,
        /**
         * A message sent to welcome a client to the chat
         */
        WELCOME
    }
}
