package engine2d.core;

import engine2d.events.Event;

public abstract class Layer {

    protected final Application application;

    protected Layer(Application application) {
        this.application = application;
    }

    abstract protected void attach();
    abstract protected void detach();
    abstract protected void update(float dt);
    abstract protected void onEvent(Event event);

}
