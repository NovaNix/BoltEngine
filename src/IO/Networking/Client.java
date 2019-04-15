package IO.Networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client implements NetworkSide
{

	InetSocketAddress Address = null;
	SocketChannel ClientSocket = null;

	Selector ChannelManager;

	SelectionKey Key;

	ByteBuffer Buffer = ByteBuffer.allocate(1048);

	public Client(String IP, int Port)
	{

		try
		{
			Address = new InetSocketAddress(IP, Port);
			ClientSocket = SocketChannel.open(Address);

			ClientSocket.configureBlocking(false);

			ChannelManager = Selector.open();

			Key = ClientSocket.register(ChannelManager, SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_ACCEPT | SelectionKey.OP_CONNECT);

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void Update()
	{

	}

	@Override
	public void PushMessage(byte[] Message) throws IOException
	{
		Buffer = ByteBuffer.wrap(Message);
		ClientSocket.write(Buffer);

		Buffer.clear();
	}

	@Override
	public byte[] PullMessage() throws IOException
	{
		Buffer.flip();

		ClientSocket.read(Buffer);

		byte[] ByteArray = new byte[Buffer.remaining()];
		Buffer.get(ByteArray);

		Buffer.clear();

		return ByteArray;
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
