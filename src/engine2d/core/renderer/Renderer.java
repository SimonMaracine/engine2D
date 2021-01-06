package engine2d.core.renderer;

import engine2d.core.Display;
import engine2d.core.renderer.font.Text;
import engine2d.core.renderer.texture.Texture;
import org.joml.*;

import static engine2d.utils.Utils.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private static final Shader basicShader = new Shader("shaders/basic.vert", "shaders/basic.frag");
    private static final Shader textShader = new Shader("shaders/text.vert", "shaders/text.frag");
    private static final RendererModels rendererModels = new RendererModels();

    public static void init() {
        assert Display.getWidth() != 0 || Display.getHeight() != 0;

        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);

        Matrix4f matrix = createProjectionMatrix(0, Display.getWidth(), 0, Display.getHeight());
        textShader.use();
        textShader.loadUniformFloat16("projectionMatrix", matrix);

        basicShader.use();
        basicShader.loadUniformFloat16("projectionMatrix", matrix);
    }

    public static void clear() {  // TODO also clear the depth buffer?
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public static void setViewport(int width, int height) {
        glViewport(0, 0, width, height);

        Matrix4f matrix = createProjectionMatrix(0, width, 0, height);
        textShader.use();
        textShader.loadUniformFloat16("projectionMatrix", matrix);

        basicShader.use();
        basicShader.loadUniformFloat16("projectionMatrix", matrix);
    }

    public static void drawText(int x, int y, float size, Text text) {
        textShader.use();  // Start using textShader
        textShader.loadUniformInt1("bitmap", 0);
        textShader.loadUniformFloat3("color", text.color);
        textShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(size, size)));

        text.textModel.updateData(text.positions, text.textureCoordinates);  // Update the buffer every draw
        text.fontAtlas.bind(0);
        text.textModel.bindVAO();
        glDrawArrays(GL_TRIANGLES, 0, text.textModel.getVertexCount());

        basicShader.use();  // Use the other shader needed by everyone else
    }

    public static void drawRect(int x, int y, int width, int height, int red, int green, int blue) {
        if (width < 0 || height < 0)
            throw new RuntimeException("Width and height must be greater than 0");

        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);

        basicShader.loadUniformFloat4("color", new Vector4f(r, g, b, 1.0f));
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(width, height)));

        basicShader.loadUniformInt1("textureSampler", 0);

        rendererModels.whiteTexture.bind(0);
        rendererModels.rectangle.bindVAO();
        glDrawArrays(GL_TRIANGLES, 0, rendererModels.rectangle.getVertexCount());
    }

    public static void drawPoint(int x, int y, int red, int green, int blue) {
        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);

        basicShader.loadUniformFloat4("color", new Vector4f(r, g, b, 1.0f));
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(1, 1)));

        rendererModels.whiteTexture.bind(0);
        rendererModels.point.bindVAO();
        glDrawArrays(GL_POINTS, 0, rendererModels.point.getVertexCount());
    }

    public static void drawLine(int xa, int ya, int xb, int yb, int red, int green, int blue) {
        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);

        basicShader.loadUniformFloat4("color", new Vector4f(r, g, b, 1.0f));
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(xa, ya), new Vector2f(xb - xa, yb - ya)));

        rendererModels.whiteTexture.bind(0);
        rendererModels.line.bindVAO();
        glDrawArrays(GL_LINES, 0, rendererModels.line.getVertexCount());
    }

    public static void drawImage(int x, int y, Texture image) {
        basicShader.loadUniformInt1("textureSampler", 0);
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(image.getWidth(), image.getHeight())));

        basicShader.loadUniformFloat4("color", new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));

        image.bind(0);
        rendererModels.image.bindVAO();
        glDrawArrays(GL_TRIANGLES, 0, rendererModels.image.getVertexCount());
    }

    public static void drawImage(int x, int y, int width, int height, Texture image) {
        if (width < 0 || height < 0)
            throw new RuntimeException("Width and height must be greater than 0");

        basicShader.loadUniformInt1("textureSampler", 0);
        basicShader.loadUniformFloat16("transformationMatrix", createTransformationMatrix(new Vector2f(x, y), new Vector2f(width, height)));

        basicShader.loadUniformFloat4("color", new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));

        image.bind(0);
        rendererModels.image.bindVAO();
        glDrawArrays(GL_TRIANGLES, 0, rendererModels.image.getVertexCount());
    }

    public static void setClearColor(int red, int green, int blue) {
        float r = byteToFloat(red);
        float g = byteToFloat(green);
        float b = byteToFloat(blue);

        glClearColor(r, g, b, 1.0f);
    }

}
