package IO.Networking;

import java.io.IOException;

public interface NetworkSide
{

	public void Update();

	public void PushMessage(byte[] Message) throws IOException;

	public byte[] PullMessage() throws IOException;

	public boolean CanPushMessage();

	public boolean CanPullMessage();

}
