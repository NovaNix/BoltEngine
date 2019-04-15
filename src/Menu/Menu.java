package Menu;

import java.util.ArrayList;

import Menu.Components.MenuScreen;
import Rendering.Renderable;

public class Menu implements Renderable
{

	boolean Visable = true;

	ArrayList<MenuScreen> Screens = new ArrayList<MenuScreen>();

	MenuScreen ActiveScreen;

	public Menu()
	{

	}

	@Override
	public void Render()
	{
		if (Visable)
		{
			ActiveScreen.Render();
		}
	}

	public void Update()
	{
		if (Visable)
		{
			ActiveScreen.Update();
		}
	}

	public void AddScreen(MenuScreen Screen)
	{
		Screens.add(Screen);
	}

	public void RemoveScreen(MenuScreen Screen)
	{
		Screens.remove(Screen);
	}

	public void SetActiveScreen(int Selected)
	{
		ActiveScreen = Screens.get(Selected);
	}

	public void SetActiveScreen(MenuScreen Screen)
	{
		ActiveScreen = Screen;
	}

	public void Hide()
	{
		Visable = false;
	}

	public void Show()
	{
		Visable = true;
	}

	public boolean GetVisable()
	{
		return Visable;
	}

	public void SetVisability(boolean Visable)
	{
		this.Visable = Visable;
	}

}
