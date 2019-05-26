#version 330 core
layout (location = 0) in vec2 Vertex;
layout (location = 1) in vec2 TexCoord;

uniform mat4 CameraModel;
uniform mat4 ObjectModel;
uniform mat4 Projection;

out vec2 OvalCoord;

void main()
{
	OvalCoord = TexCoord;
	gl_Position = Projection * CameraModel * ObjectModel * vec4(Vertex, 0, 1);
}
