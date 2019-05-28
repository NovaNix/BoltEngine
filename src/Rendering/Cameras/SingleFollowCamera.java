package Rendering.Cameras;

import Geometry.Segment;
import Geometry.Shapes.Circle;
import Geometry.Shapes.Rectangle;
import Geometry.Shapes.Shape;
import Vectors.ReferencedVector2f;
import Vectors.Vector2f;

public class SingleFollowCamera extends Camera
{

	Followable Following = null;

	Shape FollowCollision;

	public SingleFollowCamera(String Name, Vector2f Position)
	{
		super(Name, Position);
	}

	public void UpdateFollowing()
	{
		if (Following != null)
		{
			if (FollowCollision != null)
			{
				if (!FollowCollision.CollidesWith(Following.GetFollowingPosition()))
				{
					Segment Path = new Segment(FollowCollision.GetCenter(), Following.GetFollowingPosition());

					Vector2f[] Collisions = FollowCollision.GetCollisionPointsWith(Path);

					if (Collisions != null)
					{
						Vector2f MoveTo = Collisions[0];
						Move(MoveTo.GetXDistanceTo(Following.GetFollowingPosition()), MoveTo.GetYDistanceTo(Following.GetFollowingPosition()));
					}
				}
			}

			else
			{
				Following = null;
				FollowCollision = null;
			}
		}
	}

	public void UpdateFollowShape()
	{
		if (FollowCollision instanceof Circle)
		{
			Follow(Following, ((Circle) FollowCollision).GetRadius());
		}

		else if (FollowCollision instanceof Rectangle)
		{
			float XDistance = ((Rectangle) FollowCollision).GetScale().GetX() / 2;
			float YDistance = ((Rectangle) FollowCollision).GetScale().GetY() / 2;

			Follow(Following, XDistance, YDistance);
		}
	}

	public void Follow(Followable Focused, float Radius)
	{
		Following = Focused;

		FollowCollision = new Circle(CameraCollision.GetCenter(), Radius);
	}

	public void Follow(Followable Focused, float XDistance, float YDistance)
	{
		Following = Focused;

		ReferencedVector2f FollowPosition = new ReferencedVector2f(CameraCollision.GetCenter(), new Vector2f(-XDistance, -YDistance));

		FollowCollision = new Rectangle(FollowPosition, new Vector2f(XDistance * 2, YDistance * 2));
	}

	public void StopFollowing()
	{
		Following = null;

		FollowCollision = null;
	}

	public boolean IsFollowing()
	{
		return Following != null;
	}

	public boolean IsFollowing(Followable Tested)
	{
		return Tested == Following;
	}

}
