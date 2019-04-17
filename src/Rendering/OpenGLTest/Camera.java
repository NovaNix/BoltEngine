package Rendering.OpenGLTest;

import java.awt.Image;
import java.util.ArrayList;

import Geometry.Shapes.Rectangle;
import Geometry.Shapes.Shape;
import Rendering.Renderable;
import Rendering.Rendering;
import Utils.Movable;
import Vectors.Vector2f;

public class Camera implements Movable
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

	public Camera(String Name, Vector2f Position)
	{
		this.Name = Name;

		this.Position = Position;

		this.CameraCollision = new Rectangle(Position, new Vector2f(0, 0));

		this.AspectRatio = new Vector2f(0, 0);
		
		FBOHandle = glGenFramebuffers();
	
		FBOTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, FBOTexture);
  	
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, GetWidth(), GetHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, NULL);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST); 
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, FBOTexture, 0); 
	}

	public void Update()
	{
		this.AspectRatio = new Vector2f(1, GetHeight() / GetWidth());

		if (!this.CameraCollision.GetScale().equals(this.GetCameraScale()))
		{
			this.CameraCollision.SetScale(this.GetCameraScale());

			this.SetZoom(this.Zoom);
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

	public int Render()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, FBOHandle); 
	
		Rendering.Start(this.GetCameraScale(), this.Position, this.ZoomOffset, new Vector2f(this.Zoom, this.Zoom), this.CameraCollision);

		for (int i = 0; i < this.Rendered.size(); i++)
		{
			this.Rendered.get(i).Render();
		}

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
		return new Vector2f(GetWidth(), GetHeight());
	}

	public float GetWidth()
	{
		return 0;
	}

	public float GetHeight()
	{
		return 0;
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
