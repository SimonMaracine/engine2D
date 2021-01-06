package engine2d.events;

public class KeyPressedEvent extends Event {

    public final int key;
    public final int scancode;
    public final boolean repeated;

    KeyPressedEvent(int key, int scancode, boolean repeated) {
        this.key = key;
        this.scancode = scancode;
        this.repeated = repeated;
    }

    @Override
    public EventType getType() {
        return EventType.KEYPRESSED;
    }

    @Override
    public String toString() {
        return "KEYPRESSED: " + key + " " + repeated;
    }

}
