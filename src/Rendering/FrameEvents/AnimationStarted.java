/*
 *
 */
package Rendering.FrameEvents;

public class AnimationStarted extends FrameEvent
{

	public AnimationStarted(Object Sender)
	{
		super("An animation is being started!", Type.Notification, Sender);
	}

	@Override
	public Object[] GetData()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
