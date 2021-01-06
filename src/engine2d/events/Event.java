package engine2d.events;

public abstract class Event {

    public boolean handled = false;

    public abstract EventType getType();

    public abstract String toString();

}
