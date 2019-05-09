package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class VertexBufferObject
{

	ArrayBuffer[] Buffers;

	int[] Index;

	int IndexID;

	public VertexBufferObject(ArrayBuffer[] Buffers, int[] I)
	{
		this.Buffers = Buffers;

		this.Index = I;

		IndexID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IndexID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Index, GL_STATIC_DRAW);
	}

	public ArrayBuffer GetBuffer(int Buffer)
	{
		return Buffers[Buffer];
	}

	public ArrayBuffer[] GetBuffers()
	{
		return Buffers;
	}

	public int GetIndexID()
	{
		return IndexID;
	}

	public int[] GetIndex()
	{
		return Index;
	}

	public int GetSize()
	{
		return Buffers.length;
	}

}
