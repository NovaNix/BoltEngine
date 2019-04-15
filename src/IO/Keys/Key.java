/*
 * 
 */
package IO.Keys;

/**
 * The Class Key.
 */
public abstract class Key
{

	/** The Name. */
	String Name = "Null";

	/** The value storing whether the key is down or not. */
	boolean Down = false;

	boolean Typed = false;

	/**
	 * Instantiates a new key.
	 */
	public Key()
	{

	}

	/**
	 * Gets the name.
	 *
	 * @return Name
	 */
	public String GetName()
	{
		return Name;
	}

	/**
	 * Checks if key is down.
	 *
	 * @return true, if down
	 */
	public boolean IsDown()
	{
		return Down;
	}

	public boolean IsTyped()
	{
		boolean WasTyped = Typed;

		Typed = false;

		return WasTyped;
	}

	public boolean IsTyped(boolean ResetState)
	{
		if (ResetState)
		{
			return IsTyped();
		}

		else
		{
			return Typed;
		}
	}

	public void ResetDownState()
	{
		Down = false;
	}

	public void ResetTypedState()
	{
		Typed = false;
	}

	public void ResetAllStates()
	{
		Typed = false;
		Down = false;
	}

}
