#version 330 core

uniform vec4 InnerColor;
uniform vec4 OuterColor;

in vec2 OvalCoord;

out vec4 Color;

void main()
{

	float Dis = distance(OvalCoord, vec2(0.5,0.5));

	if (Dis > 0.5)
	{
		discard;
	}

	Color = mix(InnerColor, OuterColor, Dis);
}
