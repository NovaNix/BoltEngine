package Rendering.Cameras;

import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import javax.swing.JComponent;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import Geometry.Shapes.Rectangle;
import Geometry.Shapes.Shape;
import Rendering.Handling.Renderable;
import Rendering.Handling.Rendering;
import Rendering.OpenGL.FrameBufferObject;
import Rendering.Utils.RenderableContainer;
import Utils.Movable;
import Vectors.Vector2f;

public class Camera extends JComponent implements Movable, RenderableContainer
{

	private static final long serialVersionUID = -5565281269552084759L;

	private String Name = "Unnamed Camera";

	private ArrayList<Renderable> Rendered = new ArrayList<Renderable>();

	private Vector2f Position = new Vector2f(0, 0);
	private float Rotation = 0f;

	protected Rectangle CameraCollision;

	private Rectangle ZoomCollision;

	private float Zoom = 1f;

	private Vector2f ZoomOffset = new Vector2f(0, 0);

	private Vector2f AspectRatio;

	private FrameBufferObject FBO;

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
		FBO.Bind();

		FBO.Clear();

		glViewport(0, 0, getWidth(), getHeight());

		Rendering.Start(RawModel, RSModel, RefModel, Projection);

		for (int i = 0; i < this.Rendered.size(); i++)
		{
			this.Rendered.get(i).Render();
		}

		FBO.Compress();
		
		FBO.UnBind();
		
		return FBO.GetTextureHandle();
	}

	public void GenerateFBO()
	{
		GL.createCapabilities();
		FBO = new FrameBufferObject(GetCameraScale());
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
		GenerateModel();
	}

	@Override
	public void SetPosition(Vector2f Position)
	{
		this.Position.SetPosition(Position);
		GenerateModel();
	}

	public boolean OnCamera(Shape Collision)
	{
		return this.ZoomCollision.CollidesWith(Collision);
	}

	public void UpdateSize()
	{
		this.AspectRatio = new Vector2f(1, getHeight() / getWidth());

		this.CameraCollision = new Rectangle(Position, GetCameraScale());

		if (FBO == null)
		{
			GenerateFBO();
		}

		FBO.SetSize(GetCameraScale());

		this.SetZoom(this.Zoom);

		GenerateProjection();

		if (this instanceof FollowCamera)
		{
			((FollowCamera) this).UpdateFollowing();
		}
	}

	@Override
	public void finalize()
	{

	}

}
