import engine2D.core.Application;
import engine2D.core.Layer;
import engine2D.core.Time;
import engine2D.core.renderer.font.FontType;
import engine2D.core.renderer.font.Text;
import engine2D.events.Event;

public class UI extends Layer {

    CellPopullation cellPopullation = getLayerRef("CellPopullation");

    Button resetButton;
    FontType font;

    protected UI(String name, Application application) {
        super(name, application);
    }

    @Override
    protected void attach() {
        font = new FontType("res/fonts/arial.fnt", "res/fonts/arial.png");
        resetButton = new Button(10, 10, 120, 40, font, () -> {
            cellPopullation.initCells();
        });
    }

    @Override
    protected void detach() {

    }

    @Override
    protected void update(float dt) {
        resetButton.update(dt);
        resetButton.render(renderer);

        Text fps = font.render(Integer.toString(Time.getFPS()), 0, 0, 255);
        renderer.drawText(1, Main.height - 20, 0.3f, fps);
    }

    @Override
    protected void onEvent(Event event) {
        resetButton.onEvent(event);
    }

}
