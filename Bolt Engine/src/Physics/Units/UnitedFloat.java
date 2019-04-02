package Physics.Units;

public class UnitedFloat extends Number
{

	float Value;
	Unit ValueUnit;
	
	public UnitedFloat(float Value, Unit ValueUnit)
	{
		this.Value = Value;
		this.ValueUnit = ValueUnit;
	}
	
	public UnitedFloat ConvertTo(Unit NewUnit)
	{
		if (NewUnit.GetType().equals(ValueUnit.GetType()))
		{
			Value /= ValueUnit.GetUnitPerStandard();
			Value *= NewUnit.GetUnitPerStandard();
			
			this.ValueUnit = NewUnit;
		}
		
		return this;
	}
	
	public Unit GetUnit()
	{
		return ValueUnit;
	}
	
	@Override
	public int intValue()
	{
		return (int) Value;
	}

	@Override
	public long longValue()
	{
		return (long) Value;
	}

	@Override
	public float floatValue()
	{
		return Value;
	}

	@Override
	public double doubleValue()
	{
		return Value;
	}

}
