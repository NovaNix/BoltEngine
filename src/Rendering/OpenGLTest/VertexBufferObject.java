package Rendering.OpenGLTest;

import Geometry.Shapes.Polygon;
import Rendering.Renderable;
import Utils.Vector2fUtils;
import Vectors.Vector2f;

public class VertexBufferObject
{

	long VBOID = 0L;
	
	
	long VBufferID = 0L;
	long TBufferID = 0L;
	
	long IBufferID = 0L;
	
	float[] Verticies;
	float[] TextureCoords;

	int[] Indicies;

	public VertexBufferObject(float[] V, float[] T, int[] I)
	{
		this.Verticies = V;
		this.TextureCoords = T;
		this.Indicies = I;
	}

	public void GenerateVBO()
	{
		VBOID = glGenVertexArrays(1);
		glBindVertexArray(VBOID);
		
		VBufferID = glGenBuffers(1);
		glBindBuffer(GL_ARRAY_BUFFER, VBufferID);
		glBufferData(GL_ARRAY_BUFFER, Verticies.length, Verticies, GL_STATIC_DRAW);
		
		TBufferID = glGenBuffers(1);
		glBindBuffer(GL_ARRAY_BUFFER, TBufferID);
		glBufferData(GL_ARRAY_BUFFER, TextureCoords.length, TextureCoords, GL_STATIC_DRAW);
		
 		IBufferID = glGenBuffers(1);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBufferID);
 		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Indicies.length, Indicies, GL_STATIC_DRAW);
	}
	
	public float[] GetVerticies()
	{
		return this.Verticies;
	}

	public float[] GetTextureCoords()
	{
		return this.TextureCoords;
	}

	public int[] GetIndicies()
	{
		return this.Indicies;
	}

}
