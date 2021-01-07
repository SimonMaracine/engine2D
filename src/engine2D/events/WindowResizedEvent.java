package engine2D.events;

public class WindowResizedEvent extends Event {

    public final int width, height;

    WindowResizedEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public EventType getType() {
        return EventType.WINDOWRESIZED;
    }

    @Override
    public String toString() {
        return "WINDOWRESIZED: " + width + ", " + height;
    }

}
