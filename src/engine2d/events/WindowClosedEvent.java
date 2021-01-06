package engine2d.events;

public class WindowClosedEvent extends Event {

    WindowClosedEvent() {}

    @Override
    public EventType getType() {
        return EventType.WINDOWCLOSED;
    }

    @Override
    public String toString() {
        return "WINDOWCLOSED";
    }

}
