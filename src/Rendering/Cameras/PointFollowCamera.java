package Rendering.Cameras;

import Vectors.Vector2f;

public class PointFollowCamera extends FollowCamera
{

	Followable Following = null;

	public PointFollowCamera(String Name, Vector2f Position)
	{
		super(Name, Position);
	}

	@Override
	public void UpdateFollowing()
	{
		if (Following != null)
		{
			float XDis = Following.GetFollowingPosition().GetXDistanceTo(CameraCollision.GetCenter());
			float YDis = Following.GetFollowingPosition().GetYDistanceTo(CameraCollision.GetCenter());

			Move(-XDis, -YDis);
		}
	}

	@Override
	public void UpdateFollowShape()
	{

	}

	public void Follow(Followable Focused)
	{
		Following = Focused;
	}

	@Override
	public void StopFollowing()
	{
		Following = null;
	}

	@Override
	public boolean IsFollowing()
	{
		return Following != null;
	}

	@Override
	public boolean IsFollowing(Followable Tested)
	{
		return Tested == Following;
	}

}
