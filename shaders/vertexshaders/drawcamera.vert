#version 330 core
layout (location = 0) in vec2 Vertex;
layout (location = 1) in vec2 TexCoord;

out vec2 TexCoords;

void main()
{
    TexCoords = TexCoord;
	gl_Position = vec4(Vertex, 0, 1);
}
