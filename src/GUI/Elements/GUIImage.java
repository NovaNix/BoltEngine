package GUI.Elements;

import IO.Mouse;
import Rendering.Handling.Rendering;
import Rendering.Image.Texture;
import Vectors.Vector2f;

public class GUIImage extends GUIElement
{

	Texture DefaultImage;
	Texture HoverImage;

	Texture SelectedImage;

	Texture ActiveImage;

	float Rotation;

	public GUIImage(Texture DefaultImage, Vector2f Position, Vector2f Scale, float Rotation)
	{
		super(Position, Scale, true);

		Init(DefaultImage, DefaultImage, DefaultImage);
	}

	public GUIImage(Texture DefaultImage, Texture HoverImage, Vector2f Position, Vector2f Scale, float Rotation)
	{
		super(Position, Scale, true);

		Init(DefaultImage, HoverImage, DefaultImage);
	}

	public GUIImage(Texture DefaultImage, Texture HoverImage, Texture SelectedImage, Vector2f Position, Vector2f Scale, float Rotation)
	{
		super(Position, Scale, true);

		Init(DefaultImage, HoverImage, SelectedImage);
	}

	private void Init(Texture Default, Texture Hover, Texture Selected)
	{
		this.DefaultImage = Default;
		this.HoverImage = Hover;

		this.SelectedImage = Selected;

		this.ActiveImage = DefaultImage;
	}

	@Override
	public void PreRender()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void Render()
	{
		Rendering.RenderRawImage(ActiveImage, Position, Scale, Rotation);
	}

	@Override
	public void Tick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void Hover()
	{
		ActiveImage = HoverImage;
	}

	@Override
	protected void UnHover()
	{
		ActiveImage = DefaultImage;
	}

	@Override
	public void LeftClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void MiddleClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void RightClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void Select()
	{
		ActiveImage = SelectedImage;
	}

	@Override
	protected void Deselect()
	{
		ActiveImage = DefaultImage;
	}

	@Override
	public boolean ShouldDeselect()
	{
		return InteractBoundary.CollidesWith(Mouse.GetMousePosition()) && Mouse.LeftMouseDown();
	}

}
