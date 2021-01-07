package engine2D.events;

public class MouseButtonPressedEvent extends Event {

    public final int button;

    MouseButtonPressedEvent(int button) {
        this.button = button;
    }

    @Override
    public EventType getType() {
        return EventType.MOUSEBUTTONPRESSED;
    }

    @Override
    public String toString() {
        return "MOUSEBUTTONPRESSED: " + button;
    }

}
