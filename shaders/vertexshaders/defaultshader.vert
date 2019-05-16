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
//    gl_Position = Projection * CameraModel * ObjectModel * vec4(Vertex, -LayerDepth, 1.0);
    gl_Position = Projection * vec4(Vertex, -LayerDepth, 1.0);
    TexCoords = TexCoord;
}
