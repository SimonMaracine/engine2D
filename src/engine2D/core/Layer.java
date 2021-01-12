package engine2D.core;

import engine2D.core.renderer.OrthographicCamera;
import engine2D.core.renderer.Renderer;
import engine2D.events.Event;

public abstract class Layer {

    protected final Renderer renderer;
    protected final OrthographicCamera mainCamera;

    private final Application application;
    private final String name;

    protected Layer(String name, Application application) {
        this.name = name;
        this.application = application;
        this.renderer = application.getRenderer();
        this.mainCamera = application.getMainCamera();

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
    protected <T extends Layer> T getLayerRef(String name) {
        Layer layer = application.getAllLayers().get(name);

        if (layer == null)
            throw new RuntimeException("No layer named '" + name + "' exists");

        return (T) layer;
    }

}
