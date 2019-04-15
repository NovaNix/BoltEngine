/*
 * 
 */
package IO.Keys;

import java.util.ArrayList;

public class MultiKey extends Key
{

	ArrayList<Key> Keys = new ArrayList<>();

	boolean AllPress = false;

	public MultiKey(Key[] Keys, boolean AllPress)
	{
		this.AllPress = AllPress;

		for (int i = 0; i < Keys.length; i++)
		{
			this.Keys.add(Keys[i]);
		}
	}

	public void AddKey(Key New)
	{
		Keys.add(New);
	}

	public void RemoveKey(Key ToRemove)
	{
		Keys.remove(ToRemove);
	}

	@Override
	public boolean IsDown()
	{
		if (AllPress)
		{
			for (int i = 0; i < Keys.size(); i++)
			{
				if (!Keys.get(i).IsDown())
				{
					return false;
				}
			}
			return true;
		}

		else
		{
			for (int i = 0; i < Keys.size(); i++)
			{
				if (Keys.get(i).IsDown())
				{
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public boolean IsTyped()
	{
		if (AllPress)
		{
			for (int i = 0; i < Keys.size(); i++)
			{
				if (!Keys.get(i).IsTyped())
				{
					return false;
				}
			}
			return true;
		}

		else
		{
			for (int i = 0; i < Keys.size(); i++)
			{
				if (Keys.get(i).IsTyped())
				{
					return true;
				}
			}
			return false;
		}
	}

}
