package IO.Networking;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkManager implements Runnable
{

	NetworkSide Side;

	boolean Alive = false;

	ArrayList<SendRequest> OutRequests;

	@Override
	public void run()
	{
		Alive = true;

		OutRequests = new ArrayList<SendRequest>();

		while (Alive)
		{
			Side.Update();

			for (SendRequest Request : OutRequests)
			{
				try
				{
					Side.PushMessage(Request.GetData());
					Request.SetSent(true);
					OutRequests.remove(Request);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	public SendRequest SendData(byte[] Data)
	{
		SendRequest Request = new SendRequest(Data);

		OutRequests.add(Request);

		return Request;
	}

	public void Kill()
	{
		Alive = false;
	}

	public class SendRequest
	{
		byte[] Data;

		boolean Sent = false;

		long RequestCreated;

		public SendRequest(byte[] Data)
		{
			this.Data = Data;
			this.RequestCreated = System.currentTimeMillis();
		}

		public void SetSent(boolean State)
		{
			Sent = State;
		}

		public boolean IsSent()
		{
			return Sent;
		}

		public byte[] GetData()
		{
			return Data;
		}
	}

}
