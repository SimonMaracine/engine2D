package engine2D.core;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {

    private static long window = NULL;

    private static boolean vsync;
    private static boolean fullscreen;
    private static WindowData windowData;

    static void init(WindowData windowData) {
        Display.windowData = windowData;

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new RuntimeException("Could not initialize GLFW");
    }

    static void quit() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    static void createWindow(int width, int height, String title, boolean fullscreen, boolean vsync, boolean resizable) {
        Display.windowData.width = width;
        Display.windowData.height = height;
        Display.fullscreen = fullscreen;

        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        if (resizable)
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        else
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        if (!fullscreen)
            window = glfwCreateWindow(width, height, title, NULL, NULL);
        else
            window = glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(), NULL);

        if (window == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities(true);

        setTitle(title);
        setVSync(vsync);
    }

    static void update() {
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public static void setVSync(boolean on) {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        Display.vsync = on;
        glfwSwapInterval(on ? 1 : 0);
    }

    public static void setTitle(String title) {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        Display.windowData.title = title;
        glfwSetWindowTitle(window, title);
    }

    public static void setIcon() {}  // TODO implement this

    public static void iconify() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        glfwIconifyWindow(window);
    }

    public static void restore() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        glfwRestoreWindow(window);
    }

    public static void hide() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        glfwHideWindow(window);
    }

    public static void show() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        glfwShowWindow(window);
    }

    public static int getWidth() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        return windowData.width;
    }

    public static int getHeight() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        return windowData.height;
    }

    public static String getTitle() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        return windowData.title;
    }

    public static boolean getVSync() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        return vsync;
    }

    public static boolean getFullscreen() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        return fullscreen;
    }

    public static long getWindow() {
        if (window == NULL)
            throw new RuntimeException("Window not uninitialized");

        return window;
    }

    public static String getContextVersion() {
        if (window == NULL)
            throw new RuntimeException("Window not initilized");

        int major = glfwGetWindowAttrib(window, GLFW_CONTEXT_VERSION_MAJOR);
        int minor = glfwGetWindowAttrib(window, GLFW_CONTEXT_VERSION_MINOR);
        return "OpenGL " + major + "." + minor;
    }

    public static String getGLFWVersion() {
        return glfwGetVersionString();
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

}
