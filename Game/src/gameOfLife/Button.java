package gameOfLife;

import engine2D.core.renderer.OrthographicCamera;
import engine2D.core.renderer.Renderer;
import engine2D.core.renderer.font.FontType;
import engine2D.core.renderer.font.Text;
import engine2D.events.*;
import engine2D.input.Input;
import org.joml.Vector2i;

interface Command {
    void invoke();
}

public class Button {

    int x, y;
    int width, height;
    Text text;
    FontType font;
    OrthographicCamera camera;
    Command command;

    boolean canMove = false;
    int xOffsetGrab = 0;
    int yOffsetGrab = 0;

    Button(int x, int y, int width, int height, FontType font, Command command, OrthographicCamera camera) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        this.command = command;
        this.camera = camera;
        this.text = font.render("Reset", 128, 0, 128);
    }

    void update(float dt) {
        if (Input.getMouseButtonDown(Input.MOUSE_BUTTON_RIGHT)) {
            if (canMove) {
                Vector2i mousePosition = Input.getMousePositionTransformedBy(camera.getViewMatrix());
                x = mousePosition.x - xOffsetGrab;
                y = mousePosition.y - yOffsetGrab;
            }
        }

        if (Input.getMouseButtonDown(Input.MOUSE_BUTTON_LEFT)) {
            if (checkBounds())
                text = font.render("Reset", 210, 0, 210);
        } else {
            text = font.render("Reset", 128, 0, 128);
        }
    }

    void render(Renderer renderer) {
        renderer.drawRect(x, y, width, height, 200, 200, 200, 100);
        renderer.drawText(x + 2, y + 6, 0.5f, text);
    }

    void onEvent(Event event) {
        Dispatcher dispatcher = new Dispatcher(event);
        dispatcher.dispatch(EventType.MOUSEBUTTONRELEASED, this::onMouseButtonReleased);
        dispatcher.dispatch(EventType.MOUSEBUTTONPRESSED, this::onMouseButtonPressed);
    }

    private boolean onMouseButtonReleased(MouseButtonReleasedEvent event) {
        if (event.button == Input.MOUSE_BUTTON_LEFT) {
            if (checkBounds()) {
                click();
                return true;
            }
        }

        return false;
    }

    private boolean onMouseButtonPressed(MouseButtonPressedEvent event) {
        if (event.button == Input.MOUSE_BUTTON_RIGHT) {
            if (checkBounds()) {
                canMove = true;
                Vector2i mousePosition = Input.getMousePositionTransformedBy(camera.getViewMatrix());
                xOffsetGrab = mousePosition.x - x;
                yOffsetGrab = mousePosition.y - y;
            }
            else
                canMove = false;
        }

        return false;  // return true to consume the event and not print "Left click"
    }

    private void click() {
        command.invoke();
    }

    private boolean checkBounds() {
        Vector2i mousePosition = Input.getMousePositionTransformedBy(camera.getViewMatrix());
        int posX = mousePosition.x;
        int posY = mousePosition.y;

        if (posX > x && posX < x + width) {
            if (posY > y && posY < y + height) {
                return true;
            }
        }

        return false;
    }

}
