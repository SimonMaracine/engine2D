package test1;

import engine2D.core.Application;
import engine2D.core.Display;
import engine2D.core.Layer;
import engine2D.core.Time;
import engine2D.core.renderer.OrthographicCamera;
import engine2D.core.renderer.font.FontType;
import engine2D.core.renderer.font.Text;
import engine2D.core.renderer.texture.TexParams;
import engine2D.core.renderer.texture.Texture;
import engine2D.events.*;
import engine2D.input.Input;
import org.joml.Vector3f;

public class ExampleLayer extends Layer {

    SecondLayer secondLayer = getLayerRef("SecondLayer");

    Texture image;
    FontType font;
    Text sentence;
    Text sentence2;
    Text smallText;
    Text largeText;

    Vector3f cameraPosition = new Vector3f();
    float cameraRotation = 0.0f;
    final float cameraSpeed = 250.0f;

    OrthographicCamera camera2 = new OrthographicCamera(0, Display.getWidth(), 0, Display.getHeight());

    public ExampleLayer(String name, Application app) {
        super(name, app);
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
    protected void attach() {
        System.out.println(Application.ENGINE_VERSION);
        camera2.setPosition(new Vector3f(100.0f, 0.0f, 0.0f));
    }

    @Override
    protected void detach() {
        System.out.println("ExampleLayer detached");
    }

    @Override
    protected void update(float dt) {
        if (Input.getKeyDown(Input.KEY_LEFT))
            cameraPosition.x -= cameraSpeed * dt;
        else if (Input.getKeyDown(Input.KEY_RIGHT))
            cameraPosition.x += cameraSpeed * dt;
        if (Input.getKeyDown(Input.KEY_UP))
            cameraPosition.y += cameraSpeed * dt;
        else if (Input.getKeyDown(Input.KEY_DOWN))
            cameraPosition.y -= cameraSpeed * dt;

        if (Input.getKeyDown(Input.KEY_A))
            cameraRotation += 120.0f * dt;
        if (Input.getKeyDown(Input.KEY_D))
            cameraRotation -= 120.0f * dt;

        mainCamera.setPosition(cameraPosition);
        mainCamera.setRotation(cameraRotation);

        renderer.begin(getActiveCamera());
        renderer.drawRect(300, 400, 300, 120, 255, 0, 128, 255);
        renderer.drawLine(100, 100, 500, 300, 128, 0, 255);

        renderer.drawPoint(Input.getMousePositionX() + 5, Input.getMousePositionY() + 5, 0, 255, 0);

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
        renderer.end();

//        System.out.println(secondLayer.count);
    }

    @Override
    protected void onEvent(Event event) {
        Dispatcher dispatcher = new Dispatcher(event);
        dispatcher.dispatch(EventType.KEYRELEASED, this::onKeyReleased);
        dispatcher.dispatch(EventType.KEYPRESSED, this::onKeyPressed);
        dispatcher.dispatch(EventType.MOUSEBUTTONPRESSED, this::onMouseButtonPressed);
    }

    private boolean onKeyPressed(KeyPressedEvent event) {
        if (!event.repeated)
            if (event.key == Input.KEY_UP)
                System.out.println("Pressed up key!");
            else if (event.key == Input.KEY_C) {
                if (getActiveCamera() == mainCamera)
                    setActiveCamera(camera2);
                else
                    setActiveCamera(mainCamera);
            }

        return true;
    }

    private boolean onKeyReleased(KeyReleasedEvent event) {
        if (event.key == Input.KEY_SPACE)
            System.out.println("Released space key!");
        else if (event.key == Input.KEY_S)
            System.out.println("Ssss");
        else if (event.key == Input.KEY_ESCAPE)
            closeApplication();

        return false;
    }

    private boolean onMouseButtonPressed(MouseButtonPressedEvent event) {
        if (event.button == Input.MOUSE_BUTTON_LEFT)
            System.out.println("Left click");

        return false;
    }

}
