package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class ArrayBuffer
{
	int BufferID;

	float[] Data;

	int GroupSize;

	public ArrayBuffer(float[] Data, int GroupSize)
	{
		this.Data = Data;

		this.BufferID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, BufferID);
		glBufferData(GL_ARRAY_BUFFER, CreateBuffer(Data), GL_STATIC_DRAW);

		this.GroupSize = GroupSize;
	}

	public FloatBuffer CreateBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
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
