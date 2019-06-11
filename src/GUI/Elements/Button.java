package GUI.Elements;

import Rendering.Handling.Rendering;
import Rendering.Image.Texture;
import TimeKeeping.Timer;
import Vectors.Vector2f;

public class Button extends GUIElement
{

	String Text;

	Texture RegularButton;
	Texture ClickedButton;

	Texture RegularHoverButton;
	Texture ClickedHoverButton;

	Texture ActiveTexture;

	Runnable ClickEvent;

	Timer ClickTimer = new Timer(0.5f);

	public Button(Vector2f Position, Vector2f Scale, String Text, Texture Regular, Texture Clicked, Runnable ClickEvent)
	{
		super(Position, Scale, true);

		this.Text = Text;

		this.RegularButton = Regular;
		this.ClickedButton = Clicked;

		this.RegularHoverButton = Regular;
		this.ClickedHoverButton = Clicked;

		this.ActiveTexture = Regular;

		this.ClickEvent = ClickEvent;
	}

	@Override
	public void PreRender()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void Render()
	{
		Rendering.RenderRawImage(ActiveTexture, Position, Scale, 0);
		// Rendering.RenderRawText(Position, Text, Font.ARIAL, new Color(0, 0, 0));
	}

	@Override
	public void Tick()
	{
		// System.out.println("Tick2");

	}

	@Override
	protected void Hover()
	{
		ActiveTexture = RegularHoverButton;
	}

	@Override
	protected void UnHover()
	{
		ActiveTexture = RegularButton;

	}

	@Override
	public void LeftClick()
	{
		ClickEvent.run();
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
		ActiveTexture = ClickedButton;
		ClickTimer.Set();
	}

	@Override
	protected void Deselect()
	{
		ActiveTexture = RegularButton;

	}

	@Override
	public boolean ShouldDeselect()
	{
		return ClickTimer.Check();
	}

}
