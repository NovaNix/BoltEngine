package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class VertexBufferObject
{

	long VBOID = 0L;

	int VBufferID = 0;
	int TBufferID = 0;

	int IBufferID = 0;

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
		VBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBufferID);
		glBufferData(GL_ARRAY_BUFFER, Verticies, GL_STATIC_DRAW);

		TBufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, TBufferID);
		glBufferData(GL_ARRAY_BUFFER, TextureCoords, GL_STATIC_DRAW);

		IBufferID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBufferID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Indicies, GL_STATIC_DRAW);
	}

	public int GetVBufferID()
	{
		return VBufferID;
	}

	public int GetTBufferID()
	{
		return TBufferID;
	}

	public int GetIBufferID()
	{
		return IBufferID;
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
