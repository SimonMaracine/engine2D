package engine2d.core.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.*;

public class Errors {

    public static void init() {
        glEnable(GL_DEBUG_OUTPUT);
        glDebugMessageCallback((source, type, id, severity, length, message, userParam) -> {
            System.out.println("GL CALLBACK: type=" + type + "severity=" + severity + "message=" + message + "\n" +
                    (type == GL_DEBUG_TYPE_ERROR ? "** GL ERROR **" : ""));
        }, 0);
    }

}
