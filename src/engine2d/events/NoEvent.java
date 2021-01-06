package engine2d.events;

public class NoEvent extends Event {

    NoEvent() {}

    @Override
    public EventType getType() {
        return EventType.NOEVENT;
    }

    @Override
    public String toString() {
        return "NOEVENT";
    }

}
