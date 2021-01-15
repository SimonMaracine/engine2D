package engine2D.core.renderer;

import engine2D.core.Display;
import engine2D.core.renderer.font.Text;
import engine2D.core.renderer.texture.Texture;
import org.joml.*;

import static engine2D.utils.Utils.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private final Shader basicShader = new Shader("shaders/basic.vert", "shaders/basic.frag");
    private final Shader textShader = new Shader("shaders/text.vert", "shaders/text.frag");
    private final RendererModels rendererModels = new RendererModels();

    private Matrix4f viewProjectionMatrix = new Matrix4f();

    public Renderer() {
        assert Display.getWidth() != 0 || Display.getHeight() != 0;

        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);
    }

    public void clear() {  // TODO also clear the depth buffer?
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void setViewport(int width, int height) {
        glViewport(0, 0, width, height);
    }

    public void begin(OrthographicCamera camera) {
        viewProjectionMatrix = camera.getViewProjectionMatrix();
    }

    public void end() {

    }

    public void drawText(int x, int y, float size, Text text) {
        textShader.use();  // Start using textShader
        textShader.loadUniformInt1("bitmap", 0);
        textShader.loadUniformFloat3("color", text.color);
        textShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(size, size)));
        textShader.loadUniformFloat16("viewProjectionMatrix", viewProjectionMatrix);

        text.textModel.updateData(text.positions, text.textureCoordinates);  // Update the buffer every draw
        text.fontAtlas.bind(0);
        text.textModel.bindVAO();
        glDrawArrays(GL_TRIANGLES, 0, text.textModel.getVertexCount());
    }

    public void drawRect(int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        if (width < 0 || height < 0)
            throw new RuntimeException("Width and height must be greater than 0");

        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);
        float a = byteToFloat(alpha);

        basicShader.use();
        basicShader.loadUniformFloat4("color", new Vector4f(r, g, b, a));
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(width, height)));
        basicShader.loadUniformFloat16("viewProjectionMatrix", viewProjectionMatrix);
        basicShader.loadUniformInt1("textureSampler", 0);

        rendererModels.whiteTexture.bind(0);
        rendererModels.rectangle.bindVAO();
        glDrawArrays(GL_TRIANGLES, 0, rendererModels.rectangle.getVertexCount());
    }

    public void drawPoint(int x, int y, int red, int green, int blue) {
        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);

        basicShader.use();
        basicShader.loadUniformFloat4("color", new Vector4f(r, g, b, 1.0f));
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(1, 1)));
        basicShader.loadUniformFloat16("viewProjectionMatrix", viewProjectionMatrix);

        rendererModels.whiteTexture.bind(0);
        rendererModels.point.bindVAO();
        glDrawArrays(GL_POINTS, 0, rendererModels.point.getVertexCount());
    }

    public void drawLine(int xa, int ya, int xb, int yb, int red, int green, int blue) {
        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);

        basicShader.use();
        basicShader.loadUniformFloat4("color", new Vector4f(r, g, b, 1.0f));
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(xa, ya), new Vector2f(xb - xa, yb - ya)));
        basicShader.loadUniformFloat16("viewProjectionMatrix", viewProjectionMatrix);

        rendererModels.whiteTexture.bind(0);
        rendererModels.line.bindVAO();
        glDrawArrays(GL_LINES, 0, rendererModels.line.getVertexCount());
    }

    public void drawImage(int x, int y, Texture image) {
        basicShader.use();
        basicShader.loadUniformInt1("textureSampler", 0);
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(image.getWidth(), image.getHeight())));
        basicShader.loadUniformFloat16("viewProjectionMatrix", viewProjectionMatrix);
        basicShader.loadUniformFloat4("color", new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));

        image.bind(0);
        rendererModels.image.bindVAO();
        glDrawArrays(GL_TRIANGLES, 0, rendererModels.image.getVertexCount());
    }

    public void drawImage(int x, int y, int width, int height, Texture image) {
        if (width < 0 || height < 0)
            throw new RuntimeException("Width and height must be greater than 0");

        basicShader.use();
        basicShader.loadUniformInt1("textureSampler", 0);
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(width, height)));
        basicShader.loadUniformFloat16("viewProjectionMatrix", viewProjectionMatrix);
        basicShader.loadUniformFloat4("color", new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));

        image.bind(0);
        rendererModels.image.bindVAO();
        glDrawArrays(GL_TRIANGLES, 0, rendererModels.image.getVertexCount());
    }

    public void setClearColor(int red, int green, int blue) {
        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);

        glClearColor(r, g, b, 1.0f);
    }

}
