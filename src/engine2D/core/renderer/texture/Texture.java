package engine2D.core.renderer.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import engine2D.core.assetsManager.AssetsManager;
import engine2D.core.assetsManager.Disposable;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture implements Disposable {

    private final int id;
    private int width, height, noChannels;
    private ByteBuffer data;

    public Texture(String filePath) {
        loadTexture(filePath);

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

        stbi_image_free(data);
        AssetsManager.addAsset(this);
    }

    public Texture(String filePath, TexParams parameters) {
        loadTexture(filePath);

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, parameters.minFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, parameters.magFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, parameters.wrapS);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, parameters.wrapT);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);

        stbi_image_free(data);
        AssetsManager.addAsset(this);
    }

    public Texture(String filePath, boolean flip, int channels, TexParams parameters) {
        loadTexture(filePath, flip, channels);

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, parameters.minFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, parameters.magFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, parameters.wrapS);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, parameters.wrapT);

        if (channels == 4)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        else if (channels == 3)
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, data);
        else
            throw new RuntimeException("Only 3 or 4 channels are supported");

        stbi_image_free(data);
        AssetsManager.addAsset(this);
    }

    @Override
    public void dispose() {
        glDeleteTextures(id);
    }

    public void bind(int textureUnit) {
        glActiveTexture(GL_TEXTURE0 + textureUnit);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNoChannels() {
        return noChannels;
    }

    private void loadTexture(String filePath) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer n = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(true);
            data = stbi_load(filePath, w, h, n, 4);
            if (data == null)
                throw new RuntimeException("Failed to load image " + filePath + "\n" + stbi_failure_reason());

            width = w.get();
            height = h.get();
            noChannels = n.get();
        }
    }

    private void loadTexture(String filePath, boolean flip, int channels) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer n = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(flip);
            data = stbi_load(filePath, w, h, n, channels);
            if (data == null)
                throw new RuntimeException("Failed to load image " + filePath + "\n" + stbi_failure_reason());

            width = w.get();
            height = h.get();
            noChannels = n.get();
        }
    }

}
