package engine2d.core.renderer.texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class TexParams {

    public static final int LINEAR = GL_LINEAR, NEAREST = GL_NEAREST, REPEAT = GL_REPEAT,
            CLAMP_TO_BORDER = GL_CLAMP_TO_BORDER, CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;

    public int minFilter, magFilter, wrapS, wrapT;

    public TexParams(int minFilter, int magFilter, int wrapS, int wrapT) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
    }

}
