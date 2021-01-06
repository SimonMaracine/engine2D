package engine2d.events;

public class Dispatcher {

    private final Event event;

    public Dispatcher(Event event) {
        this.event = event;
    }

    public <E> void dispatch(EventType type, EventCallback<E> callback) {
        if (event.getType() == type) {
            event.handled = callback.invoke((E) event);
        }
    }

}
