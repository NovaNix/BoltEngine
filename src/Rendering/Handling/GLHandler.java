package Rendering.Handling;

import java.util.ArrayList;

// This class makes it possible to make and destroy OpenGL things in any thread 
public class GLHandler
{

	ArrayList<GLRequest> Requests = new ArrayList<GLRequest>();

	// Threading

	// Creation

	// Trash Handling

	public enum GLType
	{
		Texture, VBO, VAO, FBO
	}

	public enum Action
	{
		Create, Destroy
	}

	private class GLRequest
	{

		public GLRequest(GLType Type, Action Request)
		{

		}
	}

}
