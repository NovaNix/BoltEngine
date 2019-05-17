#version 330 core

uniform sampler2D Texture1; // Sprite
uniform sampler2D Texture2; // Color Palette

in vec2 TexCoords;

out vec4 Color;

void main()
{
	vec2 SpriteIndex = texture(Texture1, TexCoords).rg;

	vec4 PaletteColor = texture(Texture2, SpriteIndex);

	Color = PaletteColor;
}
