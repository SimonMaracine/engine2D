package engine2d.events;

public interface EventCallback<E> {

    boolean invoke(E event);

}
