package AI.AIActions;

import AI.Puppetable;
import AI.PathFinding.Path;
import Vectors.Vector2f;

public class FollowPath extends AIAction
{

	Path Follow;

	float Speed;

	int CurrentLocation = 0;

	boolean Repeat = false;

	public FollowPath(Puppetable Control, Path Follow, float Speed)
	{
		super(Control);

		this.Follow = Follow;

		this.Speed = Speed;

		this.CurrentLocation = Follow.GetClosestPoint(Control.GetFollowingPosition());
	}

	public FollowPath(Puppetable Control, Path Follow, float Speed, boolean Repeat)
	{
		super(Control);

		this.Follow = Follow;

		this.Speed = Speed;

		this.CurrentLocation = Follow.GetClosestPoint(Control.GetFollowingPosition());

		this.Repeat = Repeat;
	}

	@Override
	public void Tick()
	{
		if (CurrentLocation < Follow.GetPointCount())
		{
			Vector2f FollowingPos = Control.GetFollowingPosition();
			Vector2f PathPoint = Follow.GetPoint(CurrentLocation);

			float CurrentDistance = FollowingPos.GetDistanceTo(PathPoint);

			float XDis = FollowingPos.GetXDistanceTo(PathPoint);
			float YDis = FollowingPos.GetYDistanceTo(PathPoint);

			if (CurrentDistance <= Speed)
			{
				Control.Move(XDis, YDis);

				CurrentLocation++;
			}

			else
			{
				float Percent = Speed / CurrentDistance;

				Control.Move(Percent * XDis, Percent * YDis);
			}
		}

		else if (Repeat)
		{
			Follow = Follow.GetFlipped();
			CurrentLocation = 0;
		}

		else
		{
			ActionDone = true;
		}
	}

}
