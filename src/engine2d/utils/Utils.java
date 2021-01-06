package engine2d.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Utils {

    public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.translate(new Vector3f(translation, 0));
		matrix.scale(scale.x, scale.y, 1);
		return matrix;
	}

	public static Matrix4f createProjectionMatrix(float left, float right, float bottom, float top) {
        Matrix4f matrix = new Matrix4f();
        matrix.ortho2D(left, right, bottom, top);
        return matrix;
    }

    public static float byteToFloat(int x) {
        return x >= 255 ? 1.0f : x / 256.0f;
    }

}
