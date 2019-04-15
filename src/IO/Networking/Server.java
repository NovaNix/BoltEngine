package IO.Networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Server implements NetworkSide
{

	public Server(int Port)
	{
		try
		{
			Selector ServerSelector = Selector.open();
			ServerSocketChannel ServerSocket = ServerSocketChannel.open();
			InetSocketAddress Address = new InetSocketAddress("localhost", Port);

			ServerSocket.bind(Address);

			ServerSocket.configureBlocking(false);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void Update()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void PushMessage(byte[] Message) throws IOException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] PullMessage() throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean CanPushMessage()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean CanPullMessage()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
