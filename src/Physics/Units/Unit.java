package Physics.Units;

public abstract class Unit
{

	private float UnitPerStandard;
	private boolean IsStandard;

	private UnitType Type;

	public Unit(float UnitPerStandard, UnitType Type)
	{
		this.UnitPerStandard = UnitPerStandard;
		this.IsStandard = false;

		this.Type = Type;
	}

	public Unit(UnitType Type)
	{
		this.UnitPerStandard = 1f;
		this.IsStandard = true;

		this.Type = Type;
	}

	public boolean IsStandard()
	{
		return IsStandard;
	}

	public float GetUnitPerStandard()
	{
		return UnitPerStandard;
	}

	public void Redefine(float UnitPerStandard, UnitType Type)
	{
		this.UnitPerStandard = UnitPerStandard;
		this.IsStandard = false;

		this.Type = Type;
	}

	public UnitType GetType()
	{
		return Type;
	}

	// public float

}
