package Rendering.OpenGLTest;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Rendering
{

	Shader CurrentShader;

	Shader DrawReferencedImage;

	public Rendering()
	{

	}

	private void ApplyTexture(Texture Apply, int Sampler)
	{
		if (Sampler >= 0 && Sampler <= 31)
		{
			glActiveTexture(GL_TEXTURE0 + Sampler);
			glBindTexture(GL_TEXTURE_2D, Apply.GetID());
		}
	}

	private void ApplyShader(Shader Apply)
	{
		glUseProgram(Apply.GetShaderID());
		CurrentShader = Apply;
	}
}
