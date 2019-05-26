#version 330 core
layout (location = 0) in vec2 Vertex;

uniform vec4 LinePosition;

uniform mat4 CameraModel;
uniform mat4 Projection;

void main()
{
	vec2 Pos;
	
	if (Vertex.x == 0)
	{
		Pos = LinePosition.xy;
	}
	
	else
	{
		Pos = LinePosition.zw;
	}
	
	Pos = vec2(Pos.x, -Pos.y);

	gl_Position = Projection * CameraModel * vec4(Pos, 0, 1);
}
