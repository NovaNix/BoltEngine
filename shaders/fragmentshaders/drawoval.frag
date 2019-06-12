#version 330 core

uniform vec4 InnerColor;
uniform vec4 OuterColor;

in vec2 OvalCoord;

out vec4 Color;

void main()
{

	float Dis = distance(OvalCoord, vec2(0.5,0.5));

	vec4 Col1 = InnerColor;
	vec4 Col2 = OuterColor;

	if (Dis > 0.5)
	{
		discard;
	}

	Color = mix(Col1, Col2, Dis);
}
