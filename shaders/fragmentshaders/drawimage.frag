#version 330

uniform sampler2D Texture1; 

uniform vec4 Hue = vec4(0, 0, 0, 1);

in vec2 TexCoords;

out vec4 Color;

void main()
{
	Color = texture(Texture1, TexCoord) * Hue;
}
