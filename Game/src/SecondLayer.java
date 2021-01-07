import engine2d.core.Application;
import engine2d.core.Layer;
import engine2d.events.Event;

public class SecondLayer extends Layer {

    int count;

    protected SecondLayer(String name, Application app) {
        super(name, app);
    }

    @Override
    protected void attach() {

    }

    @Override
    protected void detach() {

    }

    @Override
    protected void update(float dt) {
        renderer.clear();  // Only the first layer must clear the screen

        renderer.drawRect(1, 1, 300, 18, 0, 180, 0);
        count++;
    }

    @Override
    protected void onEvent(Event event) {

    }
}
