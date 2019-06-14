package Rendering.Cameras;

import Vectors.Vector2f;

public abstract class FollowCamera extends Camera
{

	public FollowCamera()
	{
		super();
	}
	
	public FollowCamera(Vector2f Position)
	{
		super(Position);
	}

	public abstract void UpdateFollowing();

	public abstract void UpdateFollowShape();

	public abstract void StopFollowing();

	public abstract boolean IsFollowing();

	public abstract boolean IsFollowing(Followable Tested);

}
