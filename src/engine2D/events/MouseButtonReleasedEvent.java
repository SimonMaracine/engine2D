package engine2D.events;

public class MouseButtonReleasedEvent extends Event {

    public final int button;

    MouseButtonReleasedEvent(int button) {
        this.button = button;
    }

    @Override
    public EventType getType() {
        return EventType.MOUSEBUTTONRELEASED;
    }

    @Override
    public String toString() {
        return "MOUSEBUTTONRELEASED: " + button;
    }

}
