package Rendering.OpenGL;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Color;

import Vectors.Vector2f;

public class FrameBufferObject
{

	int FBOHandle;
	int FBOTexture;

	Vector2f Size;

	Color BackgroundColor = Color.BLACK;

	public FrameBufferObject(Vector2f Size)
	{
		this.Size = Size;

		Init();
	}

	private void Init()
	{
		FBOHandle = glGenFramebuffers();
		FBOTexture = glGenTextures();

		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);
		glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, FBOTexture);

		glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, 4, GL_RGB, getWidth(), getHeight(), true);
		
	//	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, getWidth(), getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);

//		glTexParameteri(GL_TEXTURE_2D_MULTISAMPLE, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//		glTexParameteri(GL_TEXTURE_2D_MULTISAMPLE, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D_MULTISAMPLE, FBOTexture, 0);

		System.out.println("Made VBO");

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			System.out.println("Failed to generate FBO!");
			System.exit(1);
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public void SetSize(Vector2f Size)
	{
		this.Size = Size;

		glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, FBOTexture);

		glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, 4, GL_RGB, getWidth(), getHeight(), true);
	}

	public void Compress()
	{
		glBlitFramebuffer(0, 0, getWidth(), getHeight(), 0, 0, getWidth(), getHeight(), GL_COLOR_BUFFER_BIT, GL_NEAREST); 
	}
	
	public void Clear()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glClearColor(BackgroundColor.getRed() / 255f, BackgroundColor.getGreen() / 255f, BackgroundColor.getBlue() / 255f, BackgroundColor.getAlpha() / 255f);
	}

	public void SetBackgroundColor(Color Col)
	{
		BackgroundColor = Col;
	}

	public int getWidth()
	{
		return (int) Size.GetX();
	}

	public int getHeight()
	{
		return (int) Size.GetY();
	}

	public void Bind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);
	}

	public void UnBind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);
	}

	public int GetTextureHandle()
	{
		return FBOTexture;
	}

	@Override
	public void finalize()
	{

	}

}
