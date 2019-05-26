#version 330 core
layout (location = 0) in vec2 Vertex;

uniform mat4 CameraModel;
uniform mat4 ObjectModel;
uniform mat4 Projection;

void main()
{
	gl_Position = Projection * CameraModel * ObjectModel * vec4(Vertex, 0, 1);
}
