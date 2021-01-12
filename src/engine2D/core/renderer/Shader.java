package engine2D.core.renderer;

import engine2D.core.assetsManager.AssetsManager;
import engine2D.core.assetsManager.Disposable;
import org.joml.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

class Shader implements Disposable {

    private final int id;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    private final HashMap<String, Integer> cache = new HashMap<>();

    Shader(String vertexShader, String fragmentShader) {
        vertexShaderID = createAndCompileShader(vertexShader, GL_VERTEX_SHADER);
        fragmentShaderID = createAndCompileShader(fragmentShader, GL_FRAGMENT_SHADER);

        id = glCreateProgram();
        glAttachShader(id, vertexShaderID);
        glAttachShader(id, fragmentShaderID);
        glLinkProgram(id);
        glValidateProgram(id);

        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(id));
            throw new RuntimeException("Could not link up shader program");
        }

        AssetsManager.addAsset(this);
    }

    @Override
    public void dispose() {
        glDetachShader(id, vertexShaderID);
        glDetachShader(id, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(id);
    }

    void loadUniformInt1(String name, int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    void loadUniformInt3(String name, Vector3i vector) {
        glUniform3i(getUniformLocation(name), vector.x, vector.y, vector.z);
    }

    void loadUniformInt4(String name, Vector4i vector) {
        glUniform4i(getUniformLocation(name), vector.x, vector.y, vector.z, vector.w);
    }

    void loadUniformFloat1(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    void loadUniformFloat2(String name, Vector2f vector) {
        glUniform2f(getUniformLocation(name), vector.x, vector.y);
    }

    void loadUniformFloat3(String name, Vector3f vector) {
        glUniform3f(getUniformLocation(name), vector.x, vector.y, vector.z);
    }

    void loadUniformFloat4(String name, Vector4f vector) {
        glUniform4f(getUniformLocation(name), vector.x, vector.y, vector.z, vector.w);
    }

    void loadUniformFloat16(String name, Matrix4f matrix) {
        float[] array = new float[16];
        matrix.get(array);
        glUniformMatrix4fv(getUniformLocation(name), false, array);
    }

    void loadUniformBoolean(String name, boolean truth) {
        glUniform1i(getUniformLocation(name), truth ? 1 : 0);
    }

    void use() {
        glUseProgram(id);
    }

    void stop() {
        glUseProgram(0);
    }

    private int getUniformLocation(String name) {
        for (String variableName : cache.keySet()) {
            if (name.equals(variableName))
                return cache.get(variableName);
        }

        int location = glGetUniformLocation(id, name);
        cache.put(name, location);

        if (location == -1)
            System.out.println("There is no uniform variable '" + name + "' in shader");

        return location;
    }

    private static int createAndCompileShader(String path, int type) {
        File file = new File(path);
        StringBuilder shaderSource = new StringBuilder();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                shaderSource.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource);
        glCompileShader(shaderID);

        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(shaderID));
            throw new IllegalStateException("Could not compile shader");
        }

        return shaderID;
    }

}
