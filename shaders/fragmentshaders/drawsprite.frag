#version 330 core

uniform sampler2D Texture1; // Sprite
uniform sampler2D Texture2; // Color Palette

uniform int ColorCount;

in vec2 TexCoords;

out vec4 Color;

void main()
{
	vec4 ColorIndex = texture(Texture1, TexCoords);

	float ID = (ColorIndex.x * 255) + (ColorIndex.y  * 255) + (ColorIndex.z * 255) + (ColorIndex.w * 255);
	
	ID = ID / ColorCount;

	ID = ID + ((1.0 / ColorCount) / 2);

	Color = texture(Texture2, vec2(ID, 0));
	
}
