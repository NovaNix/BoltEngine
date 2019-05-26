/*
 *
 */
package Menu;

import Messaging.Message;

public class MenuEvent extends Message
{

	public MenuEvent(String Name, Type Variation, Object Sender)
	{
		super(Name, Variation, Sender);
		// TODO Auto-generated constructor stub
	}

	// String Name;
	// String Info;
	//
	// MenuElement Element;
	//
	// public MenuEvent(String Name, String Info, MenuElement Element)
	// {
	// super(Info, Type.Notification, Element);
	//
	// this.Name = Name;
	// this.Info = Info;
	//
	// this.Element = Element;
	// }
	//
	// public String GetName()
	// {
	// return Name;
	// }
	//
	// public String GetInfo()
	// {
	// return Info;
	// }
	//
	// public MenuElement GetElement()
	// {
	// return Element;
	// }

}
