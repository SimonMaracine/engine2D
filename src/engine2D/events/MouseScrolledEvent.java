package engine2D.events;

public class MouseScrolledEvent extends Event {

    public final float mouseScroll;

    MouseScrolledEvent(float scrollY) {
        mouseScroll = scrollY;
    }

    @Override
    public EventType getType() {
        return EventType.MOUSESCROLLED;
    }

    @Override
    public String toString() {
        return "MOUSESCROLLED: " + mouseScroll;
    }

}
