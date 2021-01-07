package engine2D.events;

public interface EventCallback<E extends Event> {

    boolean invoke(E event);

}
