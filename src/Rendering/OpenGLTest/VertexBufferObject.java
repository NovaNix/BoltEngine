package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

public class VertexBufferObject
{

	VertexBuffer[] Buffers;

	int[] Index;

	int IndexID;
	
	public VertexBufferObject(VertexBuffer[] Buffers, int[] I)
	{
		this.Buffers = Buffers;
		
		this.Index = I;
		
		IndexID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IndexID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Index, GL_STATIC_DRAW);
	}

	public VertexBuffer GetBuffer(int Buffer)
	{
		return Buffers.[Buffer];	
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
	
	public class VertexBuffer
	{
		int BufferID;
		
		float[] Data;
		
		int GroupSize;
		
		public VertexBuffer(float[] Data, int GroupSize)
		{
			this.Data = Data;
			
			this.BufferID = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, BufferID);
			glBufferData(GL_ARRAY_BUFFER, Data, GL_STATIC_DRAW);
			
			this.GroupSize = GroupSize;
		}
		
		public int GetID()
		{
			return BufferID;
		}
		
		public float[] GetData()
		{
			return Data;
		}
		
		public int GetGroupSize()
		{
			return GroupSize;
		}
	}

}
