package Rendering.OpenGLTest;

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
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;

import javax.swing.JComponent;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import Geometry.Shapes.Rectangle;
import Geometry.Shapes.Shape;
import Rendering.Renderable;
import Utils.Movable;
import Vectors.Vector2f;

public class Camera extends JComponent implements Movable
{

	String Name = "Unnamed Camera";

	ArrayList<Renderable> Rendered = new ArrayList<Renderable>();

	Vector2f Position = new Vector2f(0, 0);
	float Rotation = 0f;

	Rectangle CameraCollision;

	Rectangle ZoomCollision;

	float Zoom = 1f;

	Vector2f ZoomOffset = new Vector2f(0, 0);

	Vector2f AspectRatio;

	int FBOHandle;
	int FBOTexture;

	Matrix4f Projection;
	Matrix4f Model;

	public Camera(String Name, Vector2f Position)
	{
		GL.createCapabilities();

		this.Name = Name;

		this.Position = Position;

		this.CameraCollision = new Rectangle(Position, new Vector2f(0, 0));

		this.AspectRatio = new Vector2f(0, 0);

		GenerateProjection();
		GenerateModel();
	}

	public void Update()
	{
		if (getHeight() != 0 && getWidth() != 0)
		{
			this.AspectRatio = new Vector2f(1, getHeight() / getWidth());
		}

		if (!this.CameraCollision.GetScale().equals(this.GetCameraScale()))
		{
			this.CameraCollision = new Rectangle(Position, new Vector2f(getWidth(), getHeight()));

			FBOHandle = glGenFramebuffers();
			FBOTexture = glGenTextures();

			glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);
			glBindTexture(GL_TEXTURE_2D, FBOTexture);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, getWidth(), getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, FBOTexture, 0);

			if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			{
				System.out.println("Failed to generate FBO!");
				System.exit(1);
			}

			else
			{
				System.out.println("FBO Properly Generated");
			}

			glBindFramebuffer(GL_FRAMEBUFFER, 0);

			this.SetZoom(this.Zoom);

			GenerateProjection();
			GenerateModel();
		}

		// if (this instanceof SingleFollowCamera)
		// {
		// ((SingleFollowCamera) this).UpdateFollowShape();
		// ((SingleFollowCamera) this).UpdateFollowing();
		// }
		//
		// else if (this instanceof MultiFollowCamera)
		// {
		//
		// }

	}

	private void GenerateProjection()
	{
		this.Projection = new Matrix4f().setOrtho2D(-getWidth() / 2, getWidth() / 2, -getHeight() / 2, getHeight() / 2);
	}

	private void GenerateModel()
	{
		this.Model = new Matrix4f().setTranslation(Position.GetX(), Position.GetY(), 0);
	}

	public int Render()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		System.out.println("Bound Framebuffer!");

		System.out.println("Current size:" + GetCameraScale().GetX() + ", " + GetCameraScale().GetY());

		glViewport(0, 0, getWidth(), getHeight());

		Rendering.Start(Model, Projection);

		for (int i = 0; i < this.Rendered.size(); i++)
		{
			this.Rendered.get(i).Render();
		}

		System.out.println("All Rendered!");

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		return FBOTexture;
	}

	public void AddRenderable(Renderable ToRender)
	{
		this.Rendered.add(ToRender);
	}

	public void RemoveRenderable(Renderable ToRemove)
	{
		this.Rendered.remove(ToRemove);
	}

	public void SetZoom(float Zoom)
	{
		this.Zoom = Zoom;

		Vector2f ZoomCollisionScale = this.CameraCollision.GetScale().Derive();
		ZoomCollisionScale.Divide(new Vector2f(Zoom, Zoom));

		float XTranslation = (this.CameraCollision.GetScale().GetX() - ZoomCollisionScale.GetX()) / 2;
		float YTranslation = (this.CameraCollision.GetScale().GetY() - ZoomCollisionScale.GetY()) / 2;

		this.ZoomOffset = new Vector2f(XTranslation, YTranslation);

		Vector2f ZoomCollisionPosition = this.Position.Derive();
		ZoomCollisionPosition.Add(this.ZoomOffset);

		this.ZoomCollision = new Rectangle(ZoomCollisionPosition, ZoomCollisionScale);
	}

	public Vector2f GetCameraScale()
	{
		return new Vector2f(getWidth(), getHeight());
	}

	public float GetZoom()
	{
		return this.Zoom;
	}

	public Vector2f GetPosition()
	{
		return this.Position;
	}

	@Override
	public void Move(Vector2f Translation)
	{
		this.Position.Add(Translation);
		// Update();
	}

	@Override
	public void SetPosition(Vector2f Position)
	{
		this.Position.SetPosition(Position);
		// Update();
	}

	public boolean OnCamera(Shape Collision)
	{
		return this.ZoomCollision.CollidesWith(Collision);
	}

}
