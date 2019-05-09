package Messaging;

import Threading.Request;
import Threading.RequestManager;

public class MessageManager extends RequestManager
{

	public MessageManager(int Threads)
	{
		super(Threads);
	}

	public void DistributeMessage(Message ToDistribute, MessageListener[] SendTo)
	{
		PushRequest(new PushRequest(ToDistribute, SendTo));
	}

	public class PushRequest extends Request
	{
		Message ToDistribute;
		MessageListener[] SendTo;

		private PushRequest(Message ToDistribute, MessageListener[] SendTo)
		{
			super("Push Request", new Runnable()
			{

				@Override
				public void run()
				{
					for (int i = 0; i < SendTo.length; i++)
					{
						SendTo[i].MessageRecieved(ToDistribute);
					}

				}
			});

			this.ToDistribute = ToDistribute;
			this.SendTo = SendTo;
		}

		public Message GetMessage()
		{
			return ToDistribute;
		}

		public MessageListener[] GetListeners()
		{
			return SendTo;
		}
	}

}
