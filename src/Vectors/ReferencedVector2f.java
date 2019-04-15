package Vectors;

public class ReferencedVector2f extends Vector2f
{

	Vector2f ReferencePoint;
	Vector2f Translation;

	public ReferencedVector2f(String Name, Vector2f ReferencePoint, Vector2f Translation)
	{
		this.Name = Name;

		this.ReferencePoint = ReferencePoint;
		this.Translation = Translation;
		UpdateHashCode();
	}

	public ReferencedVector2f(Vector2f ReferencePoint, Vector2f Translation)
	{
		this.ReferencePoint = ReferencePoint;
		this.Translation = Translation;
		UpdateHashCode();
	}

	public ReferencedVector2f(String Name, Vector2f ReferencePoint)
	{
		this.Name = Name;

		this.ReferencePoint = ReferencePoint;
		this.Translation = new Vector2f(0, 0);
		UpdateHashCode();
	}

	public ReferencedVector2f(Vector2f ReferencePoint)
	{
		this.ReferencePoint = ReferencePoint;
		this.Translation = new Vector2f(0, 0);
		UpdateHashCode();
	}

	public void SetReference(Vector2f Reference)
	{
		this.ReferencePoint = Reference;
		UpdateHashCode();
	}

	@Override
	public void Invert()
	{
		Translation.Invert();
		UpdateHashCode();
	}

	@Override
	public void SetX(float X)
	{
		Vector2f NewPosition = new Vector2f(X, GetY());

		Add(new Vector2f(ReferencePoint.GetXDistanceTo(NewPosition), 0));

		// Add already updates hashcode, so updating it here is not necessary
	}

	@Override
	public void SetY(float Y)
	{
		Vector2f NewPosition = new Vector2f(GetX(), Y);

		Add(new Vector2f(0, ReferencePoint.GetYDistanceTo(NewPosition)));

		// Add already updates hashcode, so updating it here is not necessary
	}

	@Override
	public float GetX()
	{
		return ReferencePoint.GetX() + Translation.GetX();
	}

	@Override
	public float GetY()
	{
		return ReferencePoint.GetY() + Translation.GetY();
	}

	@Override
	public void Add(Vector2f Change)
	{
		Translation.Add(Change);
		UpdateHashCode();
	}

	@Override
	public void Subtract(Vector2f Change)
	{
		Translation.Subtract(Change);
		UpdateHashCode();
	}

	@Override
	public void Multiply(Vector2f Change)
	{
		Translation.Multiply(Change);
		UpdateHashCode();
	}

	@Override
	public void Divide(Vector2f Change)
	{
		Translation.Divide(Change);
		UpdateHashCode();
	}

	public Vector2f Derive()
	{
		return new ReferencedVector2f(ReferencePoint, Translation);
	}
}
