package Vectors;

public class Vector2f extends Vector<Vector2f>
{

	protected float X = 0;
	protected float Y = 0;

	private Long HashCode = 0L;

	public Vector2f()
	{
		this.X = 0;
		this.Y = 0;
		// UpdateHashCode();
	}

	public Vector2f(float X, float Y)
	{
		this.X = X;
		this.Y = Y;
		UpdateHashCode();
	}

	public Vector2f(float[] Vars)
	{
		this.X = Vars[0];
		this.Y = Vars[1];
		UpdateHashCode();
	}

	public Vector2f(String Name, float X, float Y)
	{
		this.Name = Name;

		this.X = X;
		this.Y = Y;
		UpdateHashCode();
	}

	public Vector2f(String Name, float[] Vars)
	{
		this.Name = Name;

		this.X = Vars[0];
		this.Y = Vars[1];
		UpdateHashCode();
	}

	public Vector2f(int X, int Y)
	{
		this.X = X;
		this.Y = Y;
		UpdateHashCode();
	}

	public Vector2f(int[] Vars)
	{
		this.X = Vars[0];
		this.Y = Vars[1];
		UpdateHashCode();
	}

	public Vector2f(String Name, int X, int Y)
	{
		this.Name = Name;

		this.X = X;
		this.Y = Y;
		UpdateHashCode();
	}

	public Vector2f(String Name, int[] Vars)
	{
		this.Name = Name;

		this.X = Vars[0];
		this.Y = Vars[1];
		UpdateHashCode();
	}

	public void UpdateHashCode()
	{
		this.HashCode = (((long) GetX()) << 32) | ((long) GetY() & 0xffffffffL);
	}

	public void Invert()
	{
		SetX(-GetX());
		SetY(-GetY());

		UpdateHashCode();
	}

	public float GetX()
	{
		return X;
	}

	public float GetY()
	{
		return Y;
	}

	public void SetX(float X)
	{
		this.X = X;

		UpdateHashCode();
	}

	public void SetY(float Y)
	{
		this.Y = Y;

		UpdateHashCode();
	}

	public float GetMagnitude()
	{
		return GetDistanceTo(new Vector2f(0, 0));
	}

	public float GetDirection()
	{
		return (float) Math.toDegrees(Math.atan2(GetY(), GetX()));
	}

	public float GetAngle()
	{
		float Angle = (float) Math.toDegrees(Math.atan2(GetY(), GetX()));

		if (Angle < 0)
		{
			Angle = 360 + Angle;
		}

		return Angle;
	}

	public float GetDotProduct(Vector2f Vector)
	{
		return (GetX() * Vector.GetX()) + (GetY() * Vector.GetY());
	}

	public void Rotate(float Angle, Vector2f Point)
	{
		double RadianAngle = Math.toRadians(Angle);

		double RotatedX = Math.cos(RadianAngle) * (GetX() - Point.GetX()) - Math.sin(RadianAngle) * (GetX() - Point.GetY()) + Point.GetX();
		double RotatedY = Math.sin(RadianAngle) * (GetX() - Point.GetX()) + Math.cos(RadianAngle) * (GetY() - Point.GetY()) + Point.GetY();

		SetPosition(new Vector2f((float) RotatedX, (float) RotatedY));
	}

	public float GetDistanceTo(Vector2f Point)
	{
		float XDistance = Math.abs(Point.GetX() - GetX());
		float YDistance = Math.abs(Point.GetY() - GetY());

		return (float) Math.sqrt(Math.pow(XDistance, 2) + Math.pow(YDistance, 2));
	}

	public float GetXDistanceTo(Vector2f Point)
	{
		return Point.GetX() - GetX();
	}

	public float GetYDistanceTo(Vector2f Point)
	{
		return Point.GetY() - GetY();
	}

	public Vector2f GetAxisDistanceTo(Vector2f Point)
	{
		return new Vector2f(GetXDistanceTo(Point), GetYDistanceTo(Point));
	}

	public float[] GetSlopeIntercept(Vector2f SecondaryPoint)
	{
		float Slope = (SecondaryPoint.GetY() - GetY()) / (SecondaryPoint.GetX() - GetX());

		float YIntercept = GetY() - (Slope * GetX());

		if (Slope == -0)
		{
			Slope = 0;
		}

		return new float[] { Slope, YIntercept };
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Vector2f)
		{
			return GetX() == ((Vector2f) obj).GetX() && (GetY() == ((Vector2f) obj).GetY());
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		return HashCode.hashCode();
	}

	public boolean AboveDomain(float X)
	{
		return X < GetX();
	}

	public boolean BelowDomain(float X)
	{
		return X > GetX();
	}

	public boolean AboveRange(float Y)
	{
		return Y < GetY();
	}

	public boolean BelowRange(float Y)
	{
		return Y > GetY();
	}

	public boolean InDomain(float X1, float X2)
	{
		return ((Math.min(X1, X2) <= GetX()) && (GetX() <= Math.max(X1, X2)));
	}

	public boolean InRange(float Y1, float Y2)
	{
		return ((Math.min(Y1, Y2) <= GetY()) && (GetY() <= Math.max(Y1, Y2)));
	}

	@Override
	public void Add(Vector2f Change)
	{
		this.X += Change.GetX();
		this.Y += Change.GetY();

		UpdateHashCode();
	}

	@Override
	public void Subtract(Vector2f Change)
	{
		this.X -= Change.GetX();
		this.Y -= Change.GetY();

		UpdateHashCode();
	}

	@Override
	public void Multiply(Vector2f Change)
	{
		this.X *= Change.GetX();
		this.Y *= Change.GetY();

		UpdateHashCode();
	}

	public void Multiply(float Change)
	{
		this.X *= Change;
		this.Y *= Change;

		UpdateHashCode();
	}

	@Override
	public void Divide(Vector2f Change)
	{
		this.X /= Change.GetX();
		this.Y /= Change.GetY();

		UpdateHashCode();
	}

	public void SetPosition(Vector2f Position)
	{
		SetX(Position.GetX());
		SetY(Position.GetY());

		UpdateHashCode();
	}

	public void Add(float ChangeX, float ChangeY)
	{
		this.X += ChangeX;
		this.Y += ChangeY;

		UpdateHashCode();
	}

	public void Subtract(float ChangeX, float ChangeY)
	{
		this.X -= ChangeX;
		this.Y -= ChangeY;

		UpdateHashCode();
	}

	public void Multiply(float ChangeX, float ChangeY)
	{
		this.X *= ChangeX;
		this.Y *= ChangeY;

		UpdateHashCode();
	}

	public void Divide(float ChangeX, float ChangeY)
	{
		this.X /= ChangeX;
		this.Y /= ChangeY;

		UpdateHashCode();
	}

	public void SetPosition(float ChangeX, float ChangeY)
	{
		SetX(ChangeX);
		SetY(ChangeY);

		UpdateHashCode();
	}

	@Override
	public void Print()
	{
		System.out.println(Name + ": " + GetX() + ", " + GetY());
	}

	@Override
	public String toString()
	{
		return "" + GetX() + ", " + GetY();
	}

	public Vector2f Derive()
	{
		return new Vector2f(GetX(), GetY());
	}

}
