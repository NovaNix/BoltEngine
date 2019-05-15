package Menu.Updated;

import java.util.ArrayList;

import Vectors.Vector2f;

public class Menu
{

	String Name;
	Vector2f Size;

	MenuScreen ActiveScreen;

	ArrayList<MenuScreen> Screens = new ArrayList<MenuScreen>();

	public Menu(String Name)
	{
		this.Name = Name;

	}

	public void Update()
	{
		ActiveScreen.Update();
	}

	public void SetSize(Vector2f Size)
	{
		this.Size = Size;

		for (int i = 0; i < Screens.size(); i++)
		{
			Screens.get(i).SetSize(Size);
		}
	}

}
