#version 330 core
layout (location = 0) in vec2 Vertex;
layout (location = 1) in vec2 TexCoord;

uniform mat4 CameraModel;
uniform mat4 ObjectModel;
uniform mat4 Projection;

uniform float LayerDepth;

out vec2 TexCoords;

void main()
{
    TexCoords = TexCoord;
	mat4 ProjectionViewModel = Projection * CameraModel * ObjectModel;
    gl_Position = ProjectionViewModel * vec4(Vertex, LayerDepth, 1.0);
}
