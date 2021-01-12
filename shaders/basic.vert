#version 430 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 textureCoords;

out vec2 pass_TextureCoords;

uniform mat4 transformationMatrix;
uniform mat4 viewProjectionMatrix;

void main() {

    pass_TextureCoords = textureCoords;
    gl_Position = viewProjectionMatrix * transformationMatrix * vec4(position, 0.0, 1.0);

}
