#version 330 core

uniform sampler2D Texture1; 

uniform vec4 InnerColor;
uniform vec4 OuterColor;

in vec2 TexCoords;

out vec4 Color;

void main()
{

	float Dis = distance(vec2(0, 0), gl_FragCoord.xy);

	if (Dis > 1)
	{
		discard;
	}
	
	vec4 CircColor = mix(InnerColor, OuterColor, Dis);
	
	Color = texture(Texture1, TexCoords) * CircColor;
}
