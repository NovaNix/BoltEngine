/*
 * 
 */
package Debugging;

public interface Debuggable
{

	public default void Log(String Name, Object Data)
	{
		System.out.println(Name + ": " + Data);
	}

}
