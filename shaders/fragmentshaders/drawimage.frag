#version 330 core

uniform sampler2D Texture1; 

uniform float[9] Kernel = float[] (0, 0, 0, 0, 1, 0, 0, 0, 0);

uniform vec2 ImageSize;

in vec2 TexCoords;

out vec4 Color;

void main()
{

	float xoffset = 1 / ImageSize.x;
	float yoffset = 1 / ImageSize.y;
	
	vec2 offsets[9] = vec2[](
    vec2(-xoffset,  yoffset), // top-left
    vec2( 0.0f,    yoffset), // top-center
    vec2( xoffset,  yoffset), // top-right
    vec2(-xoffset,  0.0f),   // center-left
    vec2( 0.0f,    0.0f),   // center-center
    vec2( xoffset,  0.0f),   // center-right
    vec2(-xoffset, -yoffset), // bottom-left
    vec2( 0.0f,   -yoffset), // bottom-center
    vec2( xoffset, -yoffset)  // bottom-right    
   	);
   		
    vec4 sampleTex[9];
    	
    for(int i = 0; i < 9; i++)
    {
        sampleTex[i] = texture(Texture1, TexCoords + offsets[i]);
    }
    	
    vec4 col = vec4(0.0);
    	
    for(int i = 0; i < 9; i++)
    	col += sampleTex[i] * Kernel[i];
 
 	if (col.w < 1)
 	{
 		discard;
 	}
    
    Color = col;

}
