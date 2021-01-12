package engine2D.core.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class OrthographicCamera {

    private Matrix4f viewMatrix = new Matrix4f();  // It's "final", but it's actually "mutable" :P
    private final Matrix4f projectionMatrix = new Matrix4f();
    private final Matrix4f viewProjectionMatrix = new Matrix4f();

    private Vector3f position = new Vector3f();
    private Vector3f rotation = new Vector3f();  // In degrees

    public OrthographicCamera(float left, float right, float bottom, float top) {
        projectionMatrix.ortho2D(left, right, bottom, top);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        recalculateViewMatrix();
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
        recalculateViewMatrix();
    }

    public Matrix4f getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    private void recalculateViewMatrix() {
        Matrix4f mat = new Matrix4f();
        mat.translate(position);
        mat.rotate((float) Math.toRadians(rotation.x), new Vector3f(1.0f, 0.0f, 0.0f));
        mat.rotate((float) Math.toRadians(rotation.y), new Vector3f(0.0f, 1.0f, 0.0f));
        mat.rotate((float) Math.toRadians(rotation.z), new Vector3f(0.0f, 0.0f, 1.0f));

        mat.invert();  // Have no idea why :P

        viewMatrix = mat;

        // Cache the result of view * projection
        projectionMatrix.mul(viewMatrix, viewProjectionMatrix);
    }

}
