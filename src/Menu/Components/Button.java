
package Menu.Components;

import Geometry.Shapes.Rectangle;
import Geometry.Shapes.Shape;
import IO.Keys.Key;
import IO.Keys.MouseKey;
import Utils.Script;
import Utils.Triggerable;
import Vectors.Vector2f;

public class Button extends MenuElement implements Triggerable
{

	MenuText Text;
	MenuImage Icon;

	Shape Body;

	Key TriggerKey;

	MouseKey Mouse;

	boolean Clicked = false;

	Script TriggerScript;

	public Button(Vector2f Position, Vector2f Scale, MenuText Text, Key TriggerKey)
	{
		this.Position = Position;
		this.Scale = Scale;

		this.Body = new Rectangle(Position, Scale);

		this.Text = Text;

		this.TriggerKey = TriggerKey;
	}

	public Button(Vector2f Position, Vector2f Scale, MenuImage Icon, Key TriggerKey)
	{
		this.Position = Position;
		this.Scale = Scale;

		this.Body = new Rectangle(Position, Scale);

		this.Icon = Icon;

		this.TriggerKey = TriggerKey;
	}

	public Button(Vector2f Position, Vector2f Scale, MenuText Text, MenuImage Icon, Key TriggerKey)
	{
		this.Position = Position;
		this.Scale = Scale;

		this.Body = new Rectangle(Position, Scale);

		this.Text = Text;
		this.Icon = Icon;

		this.TriggerKey = TriggerKey;
	}

	@Override
	public void Trigger()
	{
		if (TriggerScript != null)
		{
			TriggerScript.Run();
		}
		Clicked = true;
	}

	@Override
	public void Render()
	{
		if (Icon != null)
		{
			Icon.Render();
		}

		if (Text != null)
		{
			Text.Render();
		}

	}

	@Override
	public void Update()
	{
		if (TriggerKey.IsTyped(false))
		{
			Mouse.ResetTypedState();

			this.PushEvent("Button Pressed", "A button has been pressed.");
			Trigger();
		}

		else if (Mouse.IsTyped(false) && Body.CollidesWith(Mouse.GetLastClickLocation()))
		{
			Mouse.ResetTypedState();

			this.PushEvent("Button Pressed", "A button has been pressed.");
			Trigger();
		}

	}

	public void SetMouse(MouseKey Button)
	{
		this.Mouse = Button;
	}

	public boolean Clicked()
	{
		boolean WasClicked = Clicked;

		Clicked = false;

		return WasClicked;
	}

	@Override
	public void SetTriggerScript(Script TriggerScript)
	{
		this.TriggerScript = TriggerScript;
	}

	public Shape GetBody()
	{
		return Body;
	}

	@Override
	public void LeftClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void RightClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void MiddleClick()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void Hover()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void Dehover()
	{
		// TODO Auto-generated method stub

	}
}