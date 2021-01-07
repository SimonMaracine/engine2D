package engine2D.events;

public abstract class Event {

    public boolean handled = false;

    public abstract EventType getType();

    public abstract String toString();

}
