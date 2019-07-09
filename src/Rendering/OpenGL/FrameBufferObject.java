package Rendering.OpenGL;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
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
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_2D_MULTISAMPLE;
import static org.lwjgl.opengl.GL32.glTexImage2DMultisample;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.Color;

import Vectors.Vector2f;

public class FrameBufferObject
{

	int FBOHandle;
	int FBOTexture;
	int FBODepthBuffer;

	int RenderableFBOHandle;
	int RenderableFBOTexture;

	Vector2f Size;

	Color BackgroundColor = Color.BLACK;

	public FrameBufferObject(Vector2f Size)
	{
		this.Size = Size;

		Init();
	}

	private void Init()
	{
		// MultiSampled VBO

		FBOHandle = glGenFramebuffers();
		FBOTexture = glGenTextures();

		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);
		glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, FBOTexture);

		glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, 4, GL_RGB, getWidth(), getHeight(), true);

		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D_MULTISAMPLE, FBOTexture, 0);

		FBODepthBuffer = glGenTextures();

		glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, FBODepthBuffer);

		glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, 4, GL_DEPTH_COMPONENT24, getWidth(), getHeight(), true);

		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D_MULTISAMPLE, FBODepthBuffer, 0);

		int Status = glCheckFramebufferStatus(GL_FRAMEBUFFER);

		if (Status != GL_FRAMEBUFFER_COMPLETE)
		{
			System.err.println("Failed to generate FBO! Error Code " + Status);
			System.err.println("Incomplete Multisample: " + GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE);
			System.err.println("Incomplete Attachment: " + GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT);
			System.err.println("Incomplete Drawbuffer: " + GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER);
			System.exit(1);
		}

		System.out.println("Made VBO 1");

		// Renderable VBO

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		RenderableFBOHandle = glGenFramebuffers();
		RenderableFBOTexture = glGenTextures();

		glBindFramebuffer(GL_FRAMEBUFFER, RenderableFBOHandle);
		glBindTexture(GL_TEXTURE_2D, RenderableFBOTexture);

		// glTexImage2DMultisample(GL_TEXTURE_2D, 4, GL_RGB, getWidth(), getHeight(),
		// true);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, getWidth(), getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, RenderableFBOTexture, 0);

		System.out.println("Made VBO 2");

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

		glBindTexture(GL_TEXTURE_2D, RenderableFBOTexture);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, getWidth(), getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);
	}

	public void Clear()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glClearColor(BackgroundColor.getRed() / 255f, BackgroundColor.getGreen() / 255f, BackgroundColor.getBlue() / 255f, BackgroundColor.getAlpha() / 255f);

		glBindFramebuffer(GL_FRAMEBUFFER, RenderableFBOHandle);

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
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, RenderableFBOHandle);
		glBindFramebuffer(GL_READ_FRAMEBUFFER, FBOHandle);

		glBlitFramebuffer(0, 0, getWidth(), getHeight(), 0, 0, getWidth(), getHeight(), GL_COLOR_BUFFER_BIT, GL_NEAREST);

		return RenderableFBOTexture;
	}

	@Override
	public void finalize()
	{

	}

}
