package engine2d.core;

import engine2d.core.renderer.Renderer;
import engine2d.events.Event;

public abstract class Layer {

    protected final Application application;
    protected final Renderer renderer;

    protected Layer(Application application) {
        this.application = application;
        this.renderer = application.getRenderer();
    }

    abstract protected void attach();
    abstract protected void detach();
    abstract protected void update(float dt);
    abstract protected void onEvent(Event event);

}
