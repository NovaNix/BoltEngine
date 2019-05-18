#version 330 core

uniform sampler2D Texture1; 

uniform int Blur;

uniform vec2 ImageSize;

in vec2 TexCoords;

out vec4 Color;

void main()
{

	if (Blur == 1)
	{
		// Blur Code gotten from an opengl tutorial, and was slightly modified
		
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
   		
		float kernel[9] = float[](
    	1.0 / 16, 2.0 / 16, 1.0 / 16,
    	2.0 / 16, 4.0 / 16, 2.0 / 16,
    	1.0 / 16, 2.0 / 16, 1.0 / 16  
		);
    
    	vec4 sampleTex[9];
    	
    	for(int i = 0; i < 9; i++)
    	{
        	sampleTex[i] = texture(Texture1, TexCoords + offsets[i]);
    	}
    	
    	vec4 col = vec4(0.0);
    	
    	for(int i = 0; i < 9; i++)
        	col += sampleTex[i] * kernel[i];
    
    	Color = col;
	}

	else
	{
		Color = texture(Texture1, TexCoords);
	}
}
