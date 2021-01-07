package engine2D.events;

public class MouseMovedEvent extends Event {

    public final float mouseX, mouseY;

    MouseMovedEvent(float mouseX, float mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public EventType getType() {
        return EventType.MOUSEMOVED;
    }

    @Override
    public String toString() {
        return "MOUSEMOVED: " + mouseX + ", " + mouseY;
    }

}
