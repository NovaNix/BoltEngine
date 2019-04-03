
package Rendering.Cameras;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JComponent;

import Geometry.Shapes.Rectangle;
import Geometry.Shapes.Shape;
import Rendering.Renderable;
import Rendering.Rendering;
import Utils.Movable;
import Vectors.Vector2f;

@SuppressWarnings({"serial"})
public abstract class Camera extends JComponent implements Movable
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
	
	public Camera(String Name, Vector2f Position)
	{
		this.Name = Name;
		
		this.Position = Position;
		
		this.CameraCollision = new Rectangle(Position, new Vector2f(0, 0));
		
		this.AspectRatio = new Vector2f(0, 0);
	}
	
	public void Update()
	{
		this.AspectRatio = new Vector2f(1, getHeight() / getWidth());
		
		if (!CameraCollision.GetScale().equals(GetCameraScale()))
		{
			this.CameraCollision = new Rectangle(Position, GetCameraScale());

			this.SetZoom(Zoom);
		}
		
		if (this instanceof SingleFollowCamera)
		{
			((SingleFollowCamera) this).UpdateFollowShape();
			((SingleFollowCamera) this).UpdateFollowing();
		}
		
		else if (this instanceof MultiFollowCamera)
		{
			
		}

	}
	
	public Image Render()
	{
		Rendering.Start(new Vector2f(getWidth(), getHeight()), Position, ZoomOffset, new Vector2f(Zoom, Zoom), CameraCollision);
		
		for (int i = 0; i < Rendered.size(); i++)
		{
			Rendered.get(i).Render();
		}

		CameraCollision.Render();
		
		Image Final = Rendering.GetProduct();
		
		Rendering.Clear();
		
		return Final;
	}
	
	public void AddRenderable(Renderable ToRender)
	{
		Rendered.add(ToRender);
	}
	
	public void RemoveRenderable(Renderable ToRemove)
	{
		Rendered.remove(ToRemove);
	}

//	public void UpdateScale()
//	{
//		Scale = Math.min(getWidth() / 1000f, getHeight() / 750f);
//	}
//
//	public void UpdateOffset()
//	{
//		XOffset = (((float) getWidth() - 1000) / 2) / Scale;
//		YOffset = (((float) getHeight() - 750) / 2) / Scale;
//	}
//
//	public void CameraResized()
//	{
//		UpdateScale();
//		UpdateOffset();
//		
//		Update();
//	}
//	
//	public float GetXOffset()
//	{
//		return XOffset;
//	}
//	
//	public float GetYOffset()
//	{
//		return YOffset;
//	}
	
	public void SetZoom(float Zoom)
	{
		this.Zoom = Zoom;
		
		float XTranslation = CameraCollision.GetCenter().GetX() - (CameraCollision.GetScale().GetX() * Zoom / 2) - Position.GetX();
		float YTranslation = CameraCollision.GetCenter().GetY() - (CameraCollision.GetScale().GetY() * Zoom / 2) - Position.GetY();
		
		this.ZoomOffset = new Vector2f(XTranslation, YTranslation);
		
		Vector2f ZoomCollisionPosition = Position.Derive();
		ZoomCollisionPosition.Add(ZoomOffset);
		
		Vector2f ZoomCollisionScale = CameraCollision.GetScale();
		ZoomCollisionScale.Divide(new Vector2f(Zoom, Zoom));
		
		this.ZoomCollision = new Rectangle(ZoomCollisionPosition, ZoomCollisionScale);
	}
	
	public Vector2f GetCameraScale()
	{
		return new Vector2f(getWidth(), getHeight());
	}
	
	public float GetZoom()
	{
		return Zoom;
	}
	
	public Vector2f GetPosition()
	{
		return Position;
	}

	@Override
	public void Move(Vector2f Translation)
	{
		Position.Add(Translation);
	//	Update();
	}

	@Override
	public void SetPosition(Vector2f Position)
	{
		this.Position.Add(Position);
	//	Update();
	}
	
	public boolean OnCamera(Shape Collision)
	{
		return ZoomCollision.CollidesWith(Collision);
	}
	
}
