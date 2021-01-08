package gameOfLife;

import engine2D.core.renderer.Renderer;
import engine2D.core.renderer.font.FontType;
import engine2D.core.renderer.font.Text;
import engine2D.events.*;
import engine2D.input.Input;

interface Command {
    void invoke();
}

public class Button {

    int x, y;
    int width, height;
    Text text;
    FontType font;
    Command command;

    boolean canMove = false;
    int xOffsetGrab = 0;
    int yOffsetGrab = 0;

    Button(int x, int y, int width, int height, FontType font, Command command) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        this.command = command;
        this.text = font.render("Reset", 128, 0, 128);
    }

    void update(float dt) {
        if (Input.mouseButtonDown(Input.MOUSE_BUTTON_RIGHT)) {
            if (canMove) {
                x = Input.mousePositionX() - xOffsetGrab;
                y = Input.mousePositionY() - yOffsetGrab;
            }
        }

        if (Input.mouseButtonDown(Input.MOUSE_BUTTON_LEFT)) {
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
                xOffsetGrab = Input.mousePositionX() - x;
                yOffsetGrab = Input.mousePositionY() - y;
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
        int posX = Input.mousePositionX();
        int posY = Input.mousePositionY();

        if (posX > x && posX < x + width) {
            if (posY > y && posY < y + height) {
                return true;
            }
        }

        return false;
    }

}
