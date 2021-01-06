package engine2d.core.renderer;

import static org.lwjgl.opengl.GL30.*;

public class Model {

    private final VertexArray vertexArray = new VertexArray();
    private int vertexCount;

    public Model() {
        VertexBuffer buffer = new VertexBuffer();
        VertexBufferLayout layout = new VertexBufferLayout();

        VertexBuffer buffer2 = new VertexBuffer();
        VertexBufferLayout layout2 = new VertexBufferLayout();

        layout.add(0, GL_FLOAT, 2);
        layout2.add(1, GL_FLOAT, 2);
        vertexArray.addBuffer(buffer, layout);
        vertexArray.addBuffer(buffer2, layout2);
    }

    public Model(float[] positions) {
        vertexCount = positions.length / 2;

        VertexBuffer buffer = new VertexBuffer(positions);
        VertexBufferLayout layout = new VertexBufferLayout();

        layout.add(0, GL_FLOAT, 2);
        vertexArray.addBuffer(buffer, layout);
    }

    public Model(float[] positions, float[] textureCoordinates) {
        vertexCount = positions.length / 2;

        VertexBuffer buffer = new VertexBuffer(positions);
        VertexBufferLayout layout = new VertexBufferLayout();

        VertexBuffer buffer2 = new VertexBuffer(textureCoordinates);
        VertexBufferLayout layout2 = new VertexBufferLayout();

        layout.add(0, GL_FLOAT, 2);
        layout2.add(1, GL_FLOAT, 2);
        vertexArray.addBuffer(buffer, layout);
        vertexArray.addBuffer(buffer2, layout2);
    }

    public void updateData(float[] positions, float[] textureCoordinates) {
        vertexCount = positions.length / 2;

        // This assumes that positions is at index 0 and texture coordinates is at index 1
        vertexArray.buffers.get(0).bind();
        glBufferData(GL_ARRAY_BUFFER, positions, GL_DYNAMIC_DRAW);  // TODO check if GL_DYNAMIC_DRAW is actually right
        vertexArray.buffers.get(1).bind();
        glBufferData(GL_ARRAY_BUFFER, textureCoordinates, GL_DYNAMIC_DRAW);
    }

    void bindVAO() {
        vertexArray.bind();
    }

    void unbindVAO() {
        vertexArray.unbind();
    }

    int getVertexCount() {
        return vertexCount;
    }

}
