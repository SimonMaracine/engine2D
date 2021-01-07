package engine2D.core.renderer.font;

import engine2D.core.renderer.Model;
import engine2D.core.renderer.texture.Texture;
import org.joml.Vector3f;

public class Text {

    public Vector3f color;
    public Model textModel;
    public Texture fontAtlas;

    public float[] positions;
    public float[] textureCoordinates;

    Text(Vector3f color, Model textModel, Texture fontAtlas, float[] positions, float[] textureCoordinates ) {
        this.textModel = textModel;
        this.fontAtlas = fontAtlas;
        this.color = color;
        this.positions = positions;
        this.textureCoordinates = textureCoordinates;
    }

}
