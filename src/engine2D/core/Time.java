package engine2D.core;

import static org.lwjgl.glfw.GLFW.*;

public class Time {

    private static double lastLoopTime;
    private static double timeCount;
    private static int frameCount;
    private static int fps;

    public static void pause(float seconds) {
        long milliseconds = (long) seconds * 1000;
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static float getTime() {
        return (float) glfwGetTime();
    }

    public static int getFPS() {
        return fps;
    }

    static float tick() {
        double time = glfwGetTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = time;
        timeCount += delta;
        frameCount++;

        if (timeCount > 1.0f) {
            fps = frameCount;
            frameCount = 0;
            timeCount -= 1.0f;
        }

        return delta;
    }

}
