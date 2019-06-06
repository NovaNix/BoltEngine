package Rendering.OpenGL;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public abstract class VertexArrayObject
{

	int VAOHandle;

	public VertexArrayObject()
	{
		VAOHandle = glGenVertexArrays();
		glBindVertexArray(VAOHandle);

		BindAttributes();

		glBindVertexArray(0);
	}

	public abstract void BindAttributes();

	public abstract int GetSize();

	public int GetHandle()
	{
		return VAOHandle;
	}

	@Override
	public void finalize()
	{
		glDeleteVertexArrays(VAOHandle);
	}
}
