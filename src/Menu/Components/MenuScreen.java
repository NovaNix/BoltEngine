/*
 * 
 */
package Menu.Components;

import java.util.ArrayList;

import Rendering.Renderable;

public class MenuScreen implements Renderable
{

	ArrayList<MenuElement> MenuElements = new ArrayList<MenuElement>();
	
	public MenuScreen()
	{
		
	}
	
	@Override
	public void Render()
	{
		for (int i = 0; i < MenuElements.size(); i++)
		{
			MenuElements.get(i).Render();
		}
		
	}
	
	public void Update()
	{
		for (int i = 0; i < MenuElements.size(); i++)
		{
			MenuElements.get(i).Update();
		}
	}
	
	public void AddElement(MenuElement Element)
	{
		MenuElements.add(Element);
	}
	
	public void RemoveElement(MenuElement Element)
	{
		MenuElements.remove(Element);
	}

}
