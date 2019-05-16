#version 330 core

uniform sampler2D Texture1; 

in vec2 TexCoord;

out vec4 Color;

void main()
{
	Color = texture(Texture1, TexCoord);
}
