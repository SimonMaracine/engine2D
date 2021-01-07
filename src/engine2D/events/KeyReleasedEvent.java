package engine2D.events;

public class KeyReleasedEvent extends Event {

    public final int key;

    KeyReleasedEvent(int key) {
        this.key = key;
    }

    @Override
    public EventType getType() {
        return EventType.KEYRELEASED;
    }

    @Override
    public String toString() {
        return "KEYRELEASED: " + key;
    }

}
