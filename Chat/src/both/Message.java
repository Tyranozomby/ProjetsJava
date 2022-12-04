package both;

import java.io.Serializable;

public record Message(String content, Type type, int id, long timestamp) implements Serializable {

    public enum Type implements Serializable {
        MESSAGE, JOIN, LEAVE, WELCOME
    }
}
