package Engine;

public class BoltMath
{

	public static float[] GetAngles(Polygon Poly)
	{
		Vector2f Corners = Poly.getCorners();
		
		float[] Angles = new float[Corners.length];	
		
		for (int i = 0; i < Corners.length; i++)
		{
			
			int PreviousCorner;
			int NextCorner;

			if (Corner == 0)
			{
				PreviousCorner = Corners.length - 1;
				NextCorner = 1;
			}

			else if (Corner == Corners.length - 1)
			{
				PreviousCorner = Corner - 1;
				NextCorner = 0;
			}

			else
			{
				PreviousCorner = Corner - 1;
				NextCorner = Corner + 1;
			}
			
			Vector2f LastPoint = Corners[PreviousCorner];
			Vector2f CurrentPoint = Corners[i];
			Vector2f NextPoint = Corners[NextCorner];
			
			double A1 = Math.atan2(LastPoint.GetY() - CurrentPoint.GetY(), LastPoint.GetX() - CurrentPoint.GetX());
			double A2 = Math.atan2(NextPoint.GetY() - CurrentPoint.GetY(), NextPoint.GetX() - CurrentPoint.GetX());
			
			if (A1 > A2)
			{
				A2 += 2 * Math.PI;	
			}
				
			float Angle = (float) Math.toDegrees(A2 - A1); 
			
			Angles[i] = Angle;
		}
		
		return Angles;
	}


}
