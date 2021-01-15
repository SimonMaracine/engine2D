package engine2D.core;

import engine2D.core.renderer.OrthographicCamera;
import engine2D.core.renderer.Renderer;
import engine2D.events.Event;

public abstract class Layer {

    protected final Renderer renderer;
    protected final OrthographicCamera mainCamera;  // Just references to the renderer and the main camera

    private final Application application;
    private final String name;
    private static OrthographicCamera activeCamera;

    protected Layer(String name, Application application) {
        this.name = name;
        this.application = application;
        this.renderer = application.getRenderer();
        this.mainCamera = application.getMainCamera();
        Layer.activeCamera = mainCamera;  // Active camera is by default the main camera

        application.getAllLayers().put(name, this);
    }

    protected abstract void attach();
    protected abstract void detach();
    protected abstract void update(float dt);
    protected abstract void onEvent(Event event);

    protected String getName() {
        return name;
    }

    protected OrthographicCamera getActiveCamera() {
        return Layer.activeCamera;
    }

    protected void closeApplication() {
        application.close();
    }

    protected void setActiveCamera(OrthographicCamera camera) {
        Layer.activeCamera = camera;

        CurrentCameraChangedEvent event = new CurrentCameraChangedEvent(camera);
        application.getWindowData().eventFunction.invoke(event);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Layer> T getLayerRef(String name) {
        Layer layer = application.getAllLayers().get(name);

        if (layer == null)
            throw new RuntimeException("No layer named '" + name + "' exists");

        return (T) layer;
    }

}
