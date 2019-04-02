package IO.Networking;

import java.util.HashMap;

public class NetworkProtocol <H extends Number>
{

	HashMap<H, NetworkMessage> MessageTypes = new HashMap<H, NetworkMessage>();
	
	public NetworkProtocol()
	{
		
	}
	
	public void AddAcceptedMessageType(NetworkMessage Type, H ID)
	{
		MessageTypes.put(ID, Type);
	}
	
	public boolean SentFromNetworkProtocol(byte[] Bytes)
	{
		return false;
	}
	
	public H GetMessageID(byte[] Message)
	{
		return null;
	}
	
	public NetworkMessage ParseBytes(byte[] Bytes)
	{
		if (SentFromNetworkProtocol(Bytes)) 
		{
			for (H ID : MessageTypes.keySet())
			{
				if (ID == GetMessageID(Bytes))
				{
					return MessageTypes.get(ID).Decode(Bytes);
				}

			}
		}
		
		return null;
	}

	
}
