package engine2d.core.renderer;

import engine2d.core.renderer.texture.Texture;

class RendererModels {

    private final float[] rectVertices = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
    };

    final Model image = new Model(rectVertices, rectVertices);

    final Model rectangle = new Model(rectVertices);

    final Model point = new Model(new float[]{
            0.0f, 0.0f
    });

    final Model line = new Model(new float[]{
            0.0f, 0.0f,
            1.0f, 1.0f
    });

    final Texture whiteTexture = new Texture("res/images/one_pixel.png");

}
