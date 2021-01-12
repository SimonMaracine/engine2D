package engine2D.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.*;

class Debug {

    static void init() {
        glEnable(GL_DEBUG_OUTPUT);
        glDebugMessageCallback(Debug::callback, 0);
    }

    private static void callback(int source, int type, int id, int severity, int length, long message, long userParam) {
        String actualMesage = memUTF8Safe(message);
        System.out.println("GL CALLBACK: type=" + type + " severity=" + severity + " message=" + actualMesage + "\n" +
            (type == GL_DEBUG_TYPE_ERROR ? "**GL ERROR**" : ""));
    }

}
