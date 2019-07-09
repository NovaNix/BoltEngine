package GUI.Elements;

import java.awt.Color;
import java.awt.Font;

import Geometry.Shapes.Rectangle;
import Rendering.Handling.Rendering2D;
import Rendering.Image.Texture;
import Rendering.Text.TextImageCreator;
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

	Vector2f TextPosition;

	Font Style;

	float FontSize;

	public Button(Vector2f Position, Vector2f Scale, String Text, Font Style, float FontSize, Texture Regular, Texture Clicked, Runnable ClickEvent)
	{
		super(Position, Scale, true);

		this.Text = Text;

		this.RegularButton = Regular;
		this.ClickedButton = Clicked;

		this.RegularHoverButton = Regular;
		this.ClickedHoverButton = Clicked;

		this.ActiveTexture = Regular;

		this.ClickEvent = ClickEvent;

		this.Style = Style.deriveFont(FontSize);

		this.FontSize = FontSize;

		Vector2f TextScale = TextImageCreator.GetTextSize(Text, Style);

		float TextX = Position720p.GetX() + ((Scale.GetX() - TextScale.GetX()) / 2);
		float TextY = Position720p.GetY() + ((Scale.GetY() - TextScale.GetY()) / 2);

		TextPosition = new Vector2f(TextX, TextY);
	}

	@Override
	public void UpdateScale(Vector2f GUIScale)
	{
		float XSize = GUIScale.GetX() / 1280;
		float YSize = GUIScale.GetY() / 720;

		Size = Math.min(XSize, YSize);

		float XPosition = XPercent1 * GUIScale.GetX();
		float YPosition = YPercent1 * GUIScale.GetY();

		Position = new Vector2f(XPosition, YPosition);

		float XScale = Size * Scale720p.GetX();
		float YScale = Size * Scale720p.GetY();

		Scale = new Vector2f(XScale, YScale);

		Style = Style.deriveFont(FontSize * Size);

		Vector2f TextScale = TextImageCreator.GetTextSize(Text, Style);

		float TextX = Position.GetX() + ((Scale.GetX() - TextScale.GetX()) / 2);
		float TextY = Position.GetY() + ((Scale.GetY() - TextScale.GetY()) / 2);

		TextPosition = new Vector2f(TextX, TextY);

		Updated = true;

		InteractBoundary = new Rectangle(Position, Scale);

	}

	@Override
	public void PreRender()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void Render()
	{
		Rendering2D.RenderRawImage(ActiveTexture, Position, Scale, 0);
		Rendering2D.RenderRawText(TextPosition, Text, Style, Color.BLACK);
	}

	@Override
	public void Tick()
	{

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
