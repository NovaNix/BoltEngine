package Rendering.Cameras;

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
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.glDeleteFramebuffers;
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
import Rendering.Rendering;
import Rendering.Utils.RenderableContainer;
import Utils.Movable;
import Vectors.Vector2f;

public class Camera extends JComponent implements Movable, RenderableContainer
{

	private String Name = "Unnamed Camera";

	private ArrayList<Renderable> Rendered = new ArrayList<Renderable>();

	private Vector2f Position = new Vector2f(0, 0);
	private float Rotation = 0f;

	protected Rectangle CameraCollision;

	private Rectangle ZoomCollision;

	private float Zoom = 1f;

	private Vector2f ZoomOffset = new Vector2f(0, 0);

	private Vector2f AspectRatio;

	private int FBOHandle;
	private int FBOTexture;

	private Matrix4f Projection;
	private Matrix4f RawModel = new Matrix4f();
	private Matrix4f RSModel;
	private Matrix4f RefModel;

	public Camera(String Name, Vector2f Position)
	{
		this.Name = Name;

		this.Position = Position;

		this.CameraCollision = new Rectangle(Position, new Vector2f(0, 0));

		this.AspectRatio = new Vector2f(0, 0);

		GenerateProjection();
		GenerateModel();
	}

	public void Update()
	{
		if (this instanceof FollowCamera)
		{
			((FollowCamera) this).UpdateFollowing();
		}
	}

	private void GenerateProjection()
	{
		this.Projection = new Matrix4f().setOrtho2D(0, getWidth(), -getHeight(), 0);
	}

	private void GenerateModel()
	{
		this.RSModel = new Matrix4f().setTranslation(0, 0, 0);
		this.RefModel = new Matrix4f().translate(-Position.GetX(), Position.GetY(), 0);
	}

	public int Render()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		GenerateProjection();
		GenerateModel();

		glViewport(0, 0, getWidth(), getHeight());

		Rendering.Start(RawModel, RSModel, RefModel, Projection);

		for (int i = 0; i < this.Rendered.size(); i++)
		{
			this.Rendered.get(i).Render();
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		return FBOTexture;
	}

	public void GenerateFBO()
	{
		if (FBOHandle != 0)
		{
			glDeleteFramebuffers(FBOHandle);
		}

		if (FBOTexture != 0)
		{
			glDeleteTextures(FBOTexture);
		}

		FBOHandle = glGenFramebuffers();
		FBOTexture = glGenTextures();

		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle);
		glBindTexture(GL_TEXTURE_2D, FBOTexture);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, getWidth(), getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, FBOTexture, 0);

		System.out.println("Made VBO");

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			System.out.println("Failed to generate FBO!");
			System.exit(1);
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);

	}

	@Override
	public void AddRenderable(Renderable ToRender)
	{
		this.Rendered.add(ToRender);
	}

	@Override
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
	}

	@Override
	public void SetPosition(Vector2f Position)
	{
		this.Position.SetPosition(Position);
	}

	public boolean OnCamera(Shape Collision)
	{
		return this.ZoomCollision.CollidesWith(Collision);
	}

	public void UpdateSize()
	{
		this.AspectRatio = new Vector2f(1, getHeight() / getWidth());

		this.CameraCollision = new Rectangle(Position, GetCameraScale());

		GL.createCapabilities();

		GenerateFBO();

		this.SetZoom(this.Zoom);

		GenerateProjection();
		GenerateModel();

		if (this instanceof SingleFollowCamera)
		{
			((SingleFollowCamera) this).UpdateFollowShape();
		}

		else if (this instanceof MultiFollowCamera)
		{
			// ((MultiFollowCamera) this).UpdateFollowShape();
		}
	}

	@Override
	public void finalize()
	{
		if (FBOHandle != 0)
		{
			glDeleteFramebuffers(FBOHandle);
		}

		if (FBOTexture != 0)
		{
			glDeleteTextures(FBOTexture);
		}
	}

}
