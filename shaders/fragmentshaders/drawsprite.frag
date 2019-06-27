#version 330 core

uniform sampler2D Texture1; // Sprite
uniform sampler2D Texture2; // Color Palette

uniform int ColorCount;

in vec2 TexCoords;

out vec4 Color;

void main()
{
	vec4 SpriteIndex = texture(Texture1, TexCoords);

	float ID = (SpriteIndex.x * 255) + (SpriteIndex.y * 255) + (SpriteIndex.z * 255) + (SpriteIndex.w * 255);

	float Offset = (1 / ColorCount) / 2;
	
	if (ColorCount % 2 == 1)
	{
		float TexCoord = (ID) / (ColorCount);

		vec4 PaletteColor = texture(Texture2, vec2(TexCoord, 0));

		Color = PaletteColor;

	}
	
	else 
	{
		float TexCoord = (ID - 1) / (ColorCount);

		vec4 PaletteColor = texture(Texture2, vec2(TexCoord + Offset, 0));

		Color = PaletteColor;
	
	}
	
}
