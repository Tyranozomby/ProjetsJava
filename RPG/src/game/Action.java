package game;

public record Action(ActionType type, Object data) {

    public enum Move implements ActionType {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public enum Inventory implements ActionType {
        EQUIP,
        CONSUME
    }

    public enum Shop implements ActionType {
        BUY
    }

    private interface ActionType {
    }
}
