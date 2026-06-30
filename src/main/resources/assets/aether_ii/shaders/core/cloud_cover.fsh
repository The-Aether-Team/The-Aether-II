#version 150

#moj_import <minecraft:dynamictransforms.glsl>


in vec4 vertexColor;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    if (color.a == 0.0) {
        discard;
    }
    fragColor = vec4(color.xyz, clamp(color.a * 8.0 - 6.0, 0.0, 1.0)) * ColorModulator;
}
