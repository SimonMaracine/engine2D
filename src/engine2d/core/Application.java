package engine2d.core;

import engine2d.WindowData;
import engine2d.core.assetsManager.AssetsManager;
import engine2d.core.opengl.Errors;
import engine2d.core.renderer.Renderer;
import engine2d.events.*;

import java.util.ArrayList;

public class Application {

    private static final int MAJOR = 0;
    private static final int MINOR = 1;
    private static final int PATCH = 0;
    public static final String ENGINE_VERSION = "Engine Version " + MAJOR + "." + MINOR + "." + PATCH;

    private boolean running = true;
    private final WindowData windowData;
    private final ArrayList<Layer> layers = new ArrayList<>();

    protected Application(int width, int height, String title) {
        windowData = new WindowData(width, height, title, this::onEvent);
        Display.init(windowData);
        Display.createWindow(width, height, title, false, true, true);
        Events.init(windowData);
        try {
            if (System.getProperty("debug").equals("true")) {
                System.out.println("Using debug mode");
                Errors.init();
            }
        } catch (Exception ignored) {}
    }

    protected Application(int width, int height, String title, boolean fullscreen, boolean vsync, boolean resizable) {
        windowData = new WindowData(width, height, title, this::onEvent);
        Display.init(windowData);
        Display.createWindow(width, height, title, fullscreen, vsync, resizable);
        Events.init(windowData);
        try {
            if (System.getProperty("debug").equals("true")) {
                System.out.println("Using debug mode");
                Errors.init();
            }
        } catch (Exception ignored) {}
    }

    public void close() {
        running = false;
    }

    protected void run() {
        float dt = 0.0f;

        while (running) {
            for (int i = 0; i < layers.size(); i++)
                layers.get(i).update(dt);

            Display.update();
            dt = Time.tick();
        }

        quit();
    }

    protected void pushLayer(Layer layer) {
        layer.attach();
        layers.add(layer);
    }

    private void onEvent(Event event) {
        Dispatcher dispatcher = new Dispatcher(event);
        dispatcher.dispatch(EventType.WINDOWCLOSED, this::onWindowClosed);
        dispatcher.dispatch(EventType.WINDOWRESIZED, this::onWindowResized);

        // Propagate the event
        for (int i = layers.size() - 1; i >= 0; i--) {
            if (event.handled)
                break;
            layers.get(i).onEvent(event);
        }
    }

    private void quit() {
        for (int i = 0; i < layers.size(); i++)
            layers.get(i).detach();

        AssetsManager.collect();
        Events.quit();
        Display.quit();
    }

    private boolean onWindowClosed(WindowClosedEvent event) {
        running = false;
        return true;
    }

    private boolean onWindowResized(WindowResizedEvent event) {
        Renderer.setViewport(event.width, event.height);
        return false;
    }

}
