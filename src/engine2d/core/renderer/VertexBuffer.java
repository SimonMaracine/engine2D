package engine2d.core.renderer;

import engine2d.core.assetsManager.AssetsManager;
import engine2d.core.assetsManager.Disposable;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class VertexBuffer implements Disposable {

    private final int id;

    VertexBuffer() {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, NULL, GL_STATIC_DRAW);

        AssetsManager.addAsset(this);
    }

    VertexBuffer(float[] data) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);

        AssetsManager.addAsset(this);
    }

    VertexBuffer(int[] data) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);

        AssetsManager.addAsset(this);
    }

    @Override
    public void dispose() {
        glDeleteBuffers(id);
    }

    void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

}
