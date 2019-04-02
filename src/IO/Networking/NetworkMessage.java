package IO.Networking;

public abstract class NetworkMessage
{

	public static String MessageName;
	
	public abstract NetworkMessage Decode(byte[] Bytes);
	
	public abstract byte[] Encode();
	
}
