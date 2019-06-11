package GUI.Elements;

import Geometry.Shapes.Rectangle;
import Rendering.Handling.PreRenderable;
import Utils.Tickable;
import Vectors.Vector2f;

public abstract class GUIElement implements PreRenderable, Tickable
{

	Vector2f Position720p;
	Vector2f Scale720p;

	protected Vector2f Position;
	protected Vector2f Scale;

	protected Rectangle InteractBoundary;

	float XPercent1;
	float XPercent2;

	float YPercent1;
	float YPercent2;

	float ScaleXPercent;
	float ScaleYPercent;

	boolean Static;
	boolean Updated = true;

	float Size;

	public GUIElement(Vector2f Position, Vector2f Scale, boolean Static)
	{
		this.Position = Position;
		this.Scale = Scale;

		this.Position720p = Position;
		this.Scale720p = Scale;

		this.Size = 1;

		this.ScaleXPercent = Scale.GetX() / 1280;
		this.ScaleYPercent = Scale.GetY() / 720;

		this.XPercent1 = Position.GetX() / 1280;
		this.XPercent2 = (Position.GetX() + Scale.GetX()) / 1280;

		this.YPercent1 = Position.GetY() / 720;
		this.YPercent2 = (Position.GetY() + Scale.GetY()) / 720;

		this.Static = Static;

		InteractBoundary = new Rectangle(Position, Scale);
	}

	public void UpdateScale(Vector2f GUIScale)
	{
		float XSize = GUIScale.GetX() / 1280;
		float YSize = GUIScale.GetY() / 720;

		Size = Math.min(XSize, YSize);

		System.out.println("Size: " + Size);

		float XPosition = XPercent1 * GUIScale.GetX();
		float YPosition = YPercent1 * GUIScale.GetY();

		Position = new Vector2f(XPosition, YPosition);

		float XScale = Size * Scale720p.GetX();
		float YScale = Size * Scale720p.GetY();

		Scale = new Vector2f(XScale, YScale);

		Updated = true;

		InteractBoundary = new Rectangle(Position, Scale);

	}

	boolean Hovering;

	public final void SetHover(boolean Hover)
	{
		if (Hover && Hovering == false)
		{
			Hover();
			Updated = true;
		}

		else if (!Hover && Hovering)
		{
			UnHover();
			Updated = true;
		}
	}

	protected abstract void Hover();

	protected abstract void UnHover();

	public abstract void LeftClick();

	public abstract void MiddleClick();

	public abstract void RightClick();

	boolean Selected;

	public final void SetSelected(boolean Select)
	{
		if (Select && Selected == false)
		{
			Select();
			Updated = true;
		}

		else if (!Select && Selected)
		{
			Deselect();
			Updated = true;
		}
	}

	protected abstract void Select();

	protected abstract void Deselect();

	public abstract boolean ShouldDeselect();

	public Rectangle GetInteractBoundary()
	{
		return InteractBoundary;
	}

	public boolean IsUpdated()
	{
		return Updated;
	}

	public boolean IsStatic()
	{
		return Static;
	}

	public void SetUpdated(boolean Updated)
	{
		this.Updated = Updated;
	}

}
