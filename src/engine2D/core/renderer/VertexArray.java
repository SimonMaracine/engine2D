package engine2D.core.renderer;

import engine2D.core.assetsManager.AssetsManager;
import engine2D.core.assetsManager.Disposable;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL30.*;

class VertexArray implements Disposable {

    private final int id;
    final ArrayList<VertexBuffer> buffers = new ArrayList<>();

    VertexArray() {
        id = glGenVertexArrays();
        glBindVertexArray(id);

        AssetsManager.addAsset(this);
    }

    @Override
    public void dispose() {
        glDeleteVertexArrays(id);
    }

    void addBuffer(VertexBuffer buffer, VertexBufferLayout layout) {
        glBindVertexArray(id);  // This seems to be not needed
        buffer.bind();
        int offset = 0;

        for (VertexElement element : layout.elements) {
            glVertexAttribPointer(element.index, element.count, element.type, false, layout.stride, offset);
            glEnableVertexAttribArray(element.index);
            offset += element.count * VertexElement.getSize(element.type);
        }
        buffers.add(buffer);
    }

    void bind() {
        glBindVertexArray(id);
    }

    void unbind() {
        glBindVertexArray(0);
    }

}
