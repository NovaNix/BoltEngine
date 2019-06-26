package Vectors;

@SuppressWarnings("rawtypes")
public abstract class Vector<H extends Vector>
{
	protected String Name = "UNNAMED";

	public String GetName()
	{
		return Name;
	}

	public abstract void Add(H Change);

	public abstract void Subtract(H Change);

	public abstract void Multiply(H Change);

	public abstract void Divide(H Change);

	public abstract H Derive();

	public abstract void Print();

}
