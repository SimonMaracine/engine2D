import engine2d.core.Application;
import engine2d.core.Layer;
import engine2d.core.Time;
import engine2d.core.renderer.font.FontType;
import engine2d.core.renderer.font.Text;
import engine2d.core.renderer.texture.TexParams;
import engine2d.core.renderer.texture.Texture;
import engine2d.events.*;
import engine2d.input.Input;

public class ExampleLayer extends Layer {

    Texture image;
    FontType font;
    Text sentence;
    Text sentence2;
    Text smallText;
    Text largeText;

    ExampleLayer(Application app) {
        super(app);
        renderer.setClearColor(170, 17, 170);

        TexParams params = new TexParams(
                TexParams.NEAREST, TexParams.NEAREST,
                TexParams.CLAMP_TO_BORDER, TexParams.CLAMP_TO_BORDER
        );
        image = new Texture("res/images/my_logo.png", params);

        font = new FontType("res/fonts/arial.fnt", "res/fonts/arial.png");

        sentence = font.render("Hallo, ich bin Simon.", 255, 0, 0);
        sentence2 = font.render("I will include non-ascii characters: cămară.", 0, 0, 128);

        smallText = font.render("Small text", 0, 0, 255);
        largeText = font.render("Large text", 0, 0, 255);
    }

    @Override
    public void attach() {
        System.out.println(Application.ENGINE_VERSION);
    }

    @Override
    public void detach() {
        System.out.println("Detached");
    }

    @Override
    public void update(float dt) {
        renderer.clear();

        renderer.drawRect(300, 400, 300, 120, 255, 0, 128);
        renderer.drawLine(100, 100, 500, 300, 128, 0, 255);

        renderer.drawPoint(Input.mousePositionX() + 5, Input.mousePositionY() + 5, 0, 255, 0);

        renderer.drawImage(400, 300, 200, 150, image);

        renderer.drawText(50, 50, 0.5f, sentence);

        Text fps = font.render(Integer.toString(Time.getFPS()), 255, 255, 0);
        renderer.drawText(1, 1, 0.2f, fps);

        Text frameTime = font.render(Float.toString(dt), 255, 255, 0);
        renderer.drawText(50, 1, 0.2f, frameTime);

        renderer.drawText(100, 600, 0.45f, sentence2);
        renderer.drawPoint(100, 600, 255, 255, 255);

        renderer.drawText(600, 100, 0.4f, smallText);
        renderer.drawText(600, 200, 1.5f, largeText);
    }

    @Override
    public void onEvent(Event event) {
        Dispatcher dispatcher = new Dispatcher(event);
        dispatcher.dispatch(EventType.KEYRELEASED, this::onKeyReleased);
        dispatcher.dispatch(EventType.KEYPRESSED, this::onKeyPressed);
    }

    boolean onKeyPressed(KeyPressedEvent event) {
        if (!event.repeated)
            if (event.key == Input.KEY_UP)
                System.out.println("Pressed up key!");

        return true;
    }

    boolean onKeyReleased(KeyReleasedEvent event) {
        if (event.key == Input.KEY_SPACE)
            System.out.println("Released space key!");
        else if (event.key == Input.KEY_S)
            System.out.println("Ssss");
        else if (event.key == Input.KEY_ESCAPE)
            application.close();

        return true;
    }

    boolean onMouseButtonPressed(MouseButtonPressedEvent event) {
        if (event.button == Input.MOUSE_BUTTON_LEFT)
            System.out.println("Left click");

        return true;
    }

}
