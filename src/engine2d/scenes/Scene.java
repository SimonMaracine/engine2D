package engine2d.scenes;

import org.lwjgl.glfw.*;

public abstract class Scene {

    public int id;
    protected GLFWKeyCallback keyCallback = null;
    protected GLFWCharCallback charCallback = null;
    protected GLFWMouseButtonCallback mouseButtonCallback = null;
    protected GLFWCursorPosCallback cursorPosCallback = null;
    protected GLFWScrollCallback scrollCallback = null;

    public Scene(int id) {
        this.id = id;
    }

    public abstract void init();

    public abstract void update(float dt);

    public abstract void render();

    public abstract void dispose();

}
