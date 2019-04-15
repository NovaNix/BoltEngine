package Messaging;

public abstract class Message
{

	String Name;
	String Description = "No Description Provided";

	Type Variation;

	MessageSender Sender;

	public Message(String Name, Type Variation, MessageSender Sender)
	{
		this.Name = Name;

		this.Variation = Variation;

		this.Sender = Sender;
	}

	public String GetName()
	{
		return Name;
	}

	public String GetDescription()
	{
		return Description;
	}

	public Type GetType()
	{
		return Variation;
	}

	public MessageSender GetSender()
	{
		return Sender;
	}

	public enum Type
	{
		Notification, Request, Error, Response
	}

}
