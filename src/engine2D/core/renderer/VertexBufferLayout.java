package engine2D.core.renderer;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

class VertexElement {

    int index;
    int type;
    int count;

    VertexElement(int index, int type, int count) {
        this.index = index;
        this.type = type;
        this.count = count;
    }

    static int getSize(int type) {
        int size;

        switch (type) {
            case GL_FLOAT:
            case GL_INT:
                size = 4;
                break;
            default:
                throw new RuntimeException("This type is not supported");
        }

        return size;
    }

}

class VertexBufferLayout {

    ArrayList<VertexElement> elements = new ArrayList<>();
    int stride;

    void add(int index, int type, int count) {
        elements.add(new VertexElement(index, type, count));
        stride += count * VertexElement.getSize(type);
    }

}
