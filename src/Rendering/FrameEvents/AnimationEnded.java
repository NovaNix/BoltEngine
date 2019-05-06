/*
 *
 */
package Rendering.FrameEvents;

public class AnimationEnded extends FrameEvent
{
	public AnimationEnded(Object Sender)
	{
		super("An animation just ended!", Type.Notification, Sender);
	}

	@Override
	public Object[] GetData()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
