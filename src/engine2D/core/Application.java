package engine2D.core;

import engine2D.core.assetsManager.AssetsManager;
import engine2D.core.renderer.OrthographicCamera;
import engine2D.core.renderer.Renderer;
import engine2D.events.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Application {

    private static final int MAJOR = 0;
    private static final int MINOR = 1;
    private static final int PATCH = 0;
    public static final String ENGINE_VERSION = "Engine Version " + MAJOR + "." + MINOR + "." + PATCH;

    private boolean running = true;
    private final WindowData windowData;

    private final ArrayList<Layer> layerStack = new ArrayList<>();
    private final HashMap<String, Layer> allLayers = new HashMap<>();

    private final Renderer renderer;
    private final OrthographicCamera mainCamera;

    protected Application(int width, int height, String title) {
        windowData = new WindowData(width, height, title, this::onEvent);
        Display.init(windowData);
        Display.createWindow(width, height, title, false, true, true);
        Events.init(windowData);
        try {
            if (System.getProperty("debug").equals("true")) {
                System.out.println("Using debug mode");
                Debug.init();
            }
        } catch (Exception ignored) {}

        renderer = new Renderer();
        mainCamera = new OrthographicCamera(0.0f, width, 0.0f, height);
    }

    protected Application(int width, int height, String title, boolean fullscreen, boolean vsync, boolean resizable) {
        windowData = new WindowData(width, height, title, this::onEvent);
        Display.init(windowData);
        Display.createWindow(width, height, title, fullscreen, vsync, resizable);
        Events.init(windowData);
        try {
            if (System.getProperty("debug").equals("true")) {
                System.out.println("Using debug mode");
                Debug.init();
            }
        } catch (Exception ignored) {}

        renderer = new Renderer();
        mainCamera = new OrthographicCamera(0.0f, width, 0.0f, height);
    }

    void close() {
        running = false;
    }

    Renderer getRenderer() {
        return renderer;
    }

    OrthographicCamera getMainCamera() {
        return mainCamera;
    }

    HashMap<String, Layer> getAllLayers() {
        return allLayers;
    }

    WindowData getWindowData() {
        return windowData;
    }

    protected void run() {
        float dt = 0.0f;

        while (running) {
            for (int i = 0; i < layerStack.size(); i++) {
                layerStack.get(i).update(dt);
            }

            Display.update();
            dt = Time.tick();
        }

        quit();
    }

    protected void pushLayer(Layer layer) {
        layer.attach();
        layerStack.add(layer);
    }

    private void onEvent(Event event) {
        Dispatcher dispatcher = new Dispatcher(event);
        dispatcher.dispatch(EventType.WINDOWCLOSED, this::onWindowClosed);
        dispatcher.dispatch(EventType.WINDOWRESIZED, this::onWindowResized);

        // Propagate the event
        for (int i = layerStack.size() - 1; i >= 0; i--) {
            if (event.handled)
                break;
            layerStack.get(i).onEvent(event);
        }
    }

    private void quit() {
        for (int i = 0; i < layerStack.size(); i++)
            layerStack.get(i).detach();

        AssetsManager.collect();
        Events.quit();
        Display.quit();
    }

    private boolean onWindowClosed(WindowClosedEvent event) {
        running = false;
        return true;
    }

    private boolean onWindowResized(WindowResizedEvent event) {
        renderer.setViewport(event.width, event.height);
        return false;
    }

}
