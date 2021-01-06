#version 430 core

in vec2 textureCoordinates;

out vec4 outColor;

uniform sampler2D bitmap;
uniform vec3 color;

void main() {

    outColor = vec4(color, texture(bitmap, textureCoordinates).a);

}
