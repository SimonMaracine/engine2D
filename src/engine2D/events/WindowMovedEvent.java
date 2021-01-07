package engine2D.events;

public class WindowMovedEvent extends Event {

    public final int positionX, positionY;

    WindowMovedEvent(int xpos, int ypos) {
        positionX = xpos;
        positionY = ypos;
    }

    @Override
    public EventType getType() {
        return EventType.WINDOWMOVED;
    }

    @Override
    public String toString() {
        return "WINDOWMOVED: " + positionX + ", " + positionY;
    }

}
