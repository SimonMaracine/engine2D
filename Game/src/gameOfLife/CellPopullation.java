package gameOfLife;

import engine2D.core.Application;
import engine2D.core.Layer;
import engine2D.events.*;
import engine2D.input.Input;
import org.joml.Vector2i;

import java.util.Random;

public class CellPopullation extends Layer {

    final int CELL_WIDTH = 5;
    final int NO_CELLS_WIDTH = Options.WIDTH / CELL_WIDTH;
    final int NO_CELLS_HEIGHT = Options.HEIGHT / CELL_WIDTH;

    boolean[][] cells = new boolean[NO_CELLS_WIDTH][NO_CELLS_HEIGHT];

    Random rand = new Random();

    public CellPopullation(String name, Application application) {
        super(name, application);
    }

    @Override
    protected void attach() {
        initCells();
    }

    @Override
    protected void detach() {

    }

    @Override
    protected void update(float dt) {
        boolean[][] generation = new boolean[NO_CELLS_WIDTH][NO_CELLS_HEIGHT];
        for (int i = 0; i < NO_CELLS_WIDTH; i++) {
            for (int j = 0; j < NO_CELLS_HEIGHT; j++) {
                generation[i][j] = cells[i][j];
            }
        }

        for (int i = 0; i < NO_CELLS_WIDTH; i++) {
            for (int j = 0; j < NO_CELLS_HEIGHT; j++) {
                int neighbors = 0;

                for (int n = -1; n < 2; n++) {
                    for (int m = -1; m < 2; m++) {
                        if (n == 0 && m == 0)
                            continue;

                        int cell_i = i + n;
                        int cell_j = j + m;
                        if (cell_i < 0 || cell_i > NO_CELLS_WIDTH - 1 || cell_j < 0 || cell_j > NO_CELLS_HEIGHT - 1)
                            continue;

                        if (cells[cell_i][cell_j])
                            neighbors++;
                    }
                }

                if (cells[i][j]) {
                    if (!(neighbors == 2 || neighbors == 3))
                        generation[i][j] = false;
                } else {
                    if (neighbors == 3)
                        generation[i][j] = true;
                }
            }
        }

        cells = generation;

//        renderer.clear();

        renderer.begin(getActiveCamera());

        for (int i = 0; i < NO_CELLS_WIDTH; i++) {
            for (int j = 0; j < NO_CELLS_HEIGHT; j++) {
                if (cells[i][j])
                    renderer.drawRect(i * CELL_WIDTH, j * CELL_WIDTH, CELL_WIDTH - 1, CELL_WIDTH - 1, 255, 255, 255, 255);
            }
        }

        renderer.end();
    }

    @Override
    protected void onEvent(Event event) {
        Dispatcher dispatcher = new Dispatcher(event);
        dispatcher.dispatch(EventType.KEYPRESSED, this::onKeyPressed);
        dispatcher.dispatch(EventType.MOUSEBUTTONRELEASED, this::onMouseButtonReleased);
    }

    void initCells() {
        for (int i = 0; i < NO_CELLS_WIDTH; i++) {
            for (int j = 0; j < NO_CELLS_HEIGHT; j++) {
                cells[i][j] = rand.nextBoolean();
            }
        }
    }

    private boolean onKeyPressed(KeyPressedEvent event) {
        if (event.key == Input.KEY_SPACE)
            initCells();

        return false;
    }

    private boolean onMouseButtonReleased(MouseButtonReleasedEvent event) {
        if (event.button == Input.MOUSE_BUTTON_LEFT) {
            try {
                Vector2i mousePosition = Input.getMousePositionTransformedBy(mainCamera.getViewMatrix());
                cells[mousePosition.x / CELL_WIDTH][mousePosition.y / CELL_WIDTH] = true;
            } catch (ArrayIndexOutOfBoundsException ignored) {}  // Clicked outside of the original window area
        }

        return true;
    }

}
