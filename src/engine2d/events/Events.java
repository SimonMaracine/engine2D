package engine2d.events;

import engine2d.WindowData;
import engine2d.core.Display;
import org.lwjgl.glfw.Callbacks;

import static org.lwjgl.glfw.GLFW.*;

public class Events {

    private static long window;

    public static void init(WindowData windowData) {
        window = Display.getWindow();

        glfwSetWindowCloseCallback(window, (window) -> {
            WindowClosedEvent event = new WindowClosedEvent();
            windowData.eventFunction.invoke(event);
        });

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_PRESS:
                    KeyPressedEvent event = new KeyPressedEvent(key, scancode, false);
                    windowData.eventFunction.invoke(event);
                    break;
                case GLFW_RELEASE:
                    KeyReleasedEvent event1 = new KeyReleasedEvent(key);
                    windowData.eventFunction.invoke(event1);
                    break;
                case GLFW_REPEAT:
                    KeyPressedEvent event2 = new KeyPressedEvent(key, scancode, true);
                    windowData.eventFunction.invoke(event2);
                    break;
            }
        });

        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
             MouseMovedEvent event = new MouseMovedEvent((float) xpos, (float) ypos);
             windowData.eventFunction.invoke(event);
        });

        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (action == GLFW_PRESS) {
                MouseButtonPressedEvent event = new MouseButtonPressedEvent(button);
                windowData.eventFunction.invoke(event);
            } else {
                MouseButtonReleasedEvent event = new MouseButtonReleasedEvent(button);
                windowData.eventFunction.invoke(event);
            }
        });

        glfwSetScrollCallback(window, (window, xoffset, yoffset) -> {
            MouseScrolledEvent event = new MouseScrolledEvent((float) yoffset);
            windowData.eventFunction.invoke(event);
        });

        glfwSetWindowFocusCallback(window, (window, focused) -> {
            WindowFocusedEvent event = new WindowFocusedEvent(focused);
            windowData.eventFunction.invoke(event);
        });

        glfwSetWindowSizeCallback(window, (window, width, height) -> {
            windowData.width = width;
            windowData.height = height;
            WindowResizedEvent event = new WindowResizedEvent(width, height);
            windowData.eventFunction.invoke(event);
        });

        glfwSetWindowPosCallback(window, (window, xpos, ypos) -> {
            WindowMovedEvent event = new WindowMovedEvent(xpos, ypos);
            windowData.eventFunction.invoke(event);
        });
    }

    public static void quit() {
        Callbacks.glfwFreeCallbacks(window);
    }

}
