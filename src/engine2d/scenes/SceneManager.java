package engine2d.scenes;

import engine2d.core.Display;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class SceneManager {

    public static Scene currentScene;
    private static long window;
    private static ArrayList<Scene> allScenes = new ArrayList<>();

    public static void init() {
        window = Display.getWindow();
        enterScene(currentScene);
    }

    public static void addScene(Scene scene) {
        allScenes.add(scene);
    }

    public static void addScene(Scene scene, boolean firstScene) {
        allScenes.add(scene);
        if (firstScene)
            currentScene = scene;
    }

    public static void switchScene(int toSceneId) {
        Scene nextScene = null;

        for (Scene scene : allScenes) {
            if (scene.id == toSceneId) {
                try {
                    nextScene = scene.getClass().getDeclaredConstructor(int.class).newInstance(scene.id);
                    System.out.println(nextScene);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                currentScene = nextScene;
                enterScene(currentScene);
                break;
            }
        }
        assert nextScene != null;
    }

    private static void enterScene(Scene scene) {
        scene.init();
        if (scene.keyCallback != null)
            glfwSetKeyCallback(window, scene.keyCallback);
        else
            glfwSetKeyCallback(window, null);
        if (scene.charCallback != null)
            glfwSetCharCallback(window, scene.charCallback);
        else
            glfwSetCharCallback(window, null);
        if (scene.mouseButtonCallback != null)
            glfwSetMouseButtonCallback(window, scene.mouseButtonCallback);
        else
            glfwSetMouseButtonCallback(window, null);
        if (scene.cursorPosCallback != null)
            glfwSetCursorPosCallback(window, scene.cursorPosCallback);
        else
            glfwSetCursorPosCallback(window, null);
        if (scene.scrollCallback != null)
            glfwSetScrollCallback(window, scene.scrollCallback);
        else
            glfwSetScrollCallback(window, null);
    }

}
