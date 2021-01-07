package engine2D.core;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.*;

class Debug {

    static void init() {
        glEnable(GL_DEBUG_OUTPUT);
        glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
            System.out.println("GL CALLBACK: type=" + type + " severity=" + severity + " message=" + message + "\n" +
                    (type == GL_DEBUG_TYPE_ERROR ? "**GL ERROR**" : ""));
        }, 0);
    }

}
