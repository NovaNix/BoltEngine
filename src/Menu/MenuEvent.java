/*
 * 
 */
package Menu;

import Menu.Components.MenuElement;
import Messaging.Message;

public class MenuEvent extends Message
{

	String Name;
	String Info;

	MenuElement Element;

	public MenuEvent(String Name, String Info, MenuElement Element)
	{
		super(Info, Type.Notification, Element);

		this.Name = Name;
		this.Info = Info;

		this.Element = Element;
	}

	public String GetName()
	{
		return Name;
	}

	public String GetInfo()
	{
		return Info;
	}

	public MenuElement GetElement()
	{
		return Element;
	}

}
