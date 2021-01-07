package engine2d.core;

import engine2d.core.renderer.Renderer;
import engine2d.events.Event;

public abstract class Layer {

    protected final Renderer renderer;
    private final Application application;
    private final String name;

    protected Layer(String name, Application application) {
        this.name = name;
        this.application = application;
        this.renderer = application.getRenderer();

        application.getAllLayers().put(name, this);
    }

    protected abstract void attach();
    protected abstract void detach();
    protected abstract void update(float dt);
    protected abstract void onEvent(Event event);

    protected String getName() {
        return name;
    }

    protected void closeApplication() {
        application.close();
    }

    @SuppressWarnings("unchecked")
    protected <T> T getLayerRef(String name) {
        Layer layer = application.getAllLayers().get(name);

        if (layer == null)
            throw new RuntimeException("No layer named '" + name + "' exists");

        return (T) layer;
    }

}
