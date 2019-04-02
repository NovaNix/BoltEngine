package Messaging;

import java.util.ArrayList;

public class MessageManager implements Runnable
{

	boolean IsAlive = false;
	
	ArrayList<PushRequest> Requests = new ArrayList<PushRequest>(); 
	
	@Override
	public void run()
	{
		IsAlive = true;
		
		while (IsAlive)
		{
			for (int i = 0; i < Requests.size(); i++)
			{
				Message ToDistribute = Requests.get(i).GetMessage();
				
				MessageListener[] SendTo = Requests.get(i).GetListeners();
				
				for (int j = 0; j < SendTo.length; j++)
				{
					SendTo[j].MessageRecieved(ToDistribute);
				}
			}
			
			Requests.clear();
			
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void Kill()
	{
		IsAlive = false;
	}
	
	public void DistributeMessage(Message ToDistribute, MessageListener[] SendTo)
	{
		Requests.add(new PushRequest(ToDistribute, SendTo));
		
		notify();
	}

	public class PushRequest
	{
		Message ToDistribute;
		MessageListener[] SendTo;
		
		private PushRequest(Message ToDistribute, MessageListener[] SendTo)
		{
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
