package engine2d.events;

public class WindowFocusedEvent extends Event {

    public final boolean focus;

    WindowFocusedEvent(boolean focus) {
        this.focus = focus;
    }

    @Override
    public EventType getType() {
        return EventType.WINDOWFOCUSED;
    }

    @Override
    public String toString() {
        return "WINDOWFOCUSED: " + focus;
    }

}
