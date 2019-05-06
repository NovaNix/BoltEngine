/*
 *
 */
package Menu.Components;

import java.util.ArrayList;

import Engine.BoltEngine;
import Menu.MenuEvent;
import Menu.MenuListener;
import Messaging.Message;
import Rendering.Renderable;
import Vectors.Vector2f;

public abstract class MenuElement implements Renderable
{

	String ComponentName;

	Vector2f Position;
	Vector2f Scale;

	ArrayList<MenuListener> Listeners = new ArrayList<MenuListener>();

	public abstract void Update();

	public abstract void LeftClick();

	public abstract void RightClick();

	public abstract void MiddleClick();

	public abstract void Hover();

	public abstract void Dehover();

	public void PushEvent(String Name, String Info)
	{
		MenuEvent Event = new MenuEvent(Name, Info, this);

		for (int i = 0; i < Listeners.size(); i++)
		{
			Listeners.get(i).NewMenuEvent(Event);
		}
	}

	public void PushMessage(Message Pushed)
	{
		MenuListener[] ListenerArray = new MenuListener[Listeners.size()];

		ListenerArray = Listeners.toArray(ListenerArray);

		BoltEngine.MessagingEngineManager.DistributeMessage(Pushed, ListenerArray);
	}

	public void AddListener(MenuListener Listener)
	{
		Listeners.add(Listener);
	}

	public void RemoveListener(MenuListener Listener)
	{
		Listeners.remove(Listener);
	}

}
