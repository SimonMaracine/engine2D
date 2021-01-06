#version 430 core

in vec2 pass_TextureCoords;

out vec4 outColor;

uniform vec4 color;
uniform sampler2D textureSampler;

void main() {

    outColor = color * texture(textureSampler, pass_TextureCoords);

}
