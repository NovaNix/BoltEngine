/*
 * 
 */
package Utils;

import Vectors.Vector2f;

public interface Movable
{

	public void Move(Vector2f Translation);

	public void SetPosition(Vector2f Position);

	public default void Translate(float X, float Y)
	{
		Move(X, Y);
	}

	public default void Translate(float[] Vars)
	{
		Move(Vars[0], Vars[1]);
	}

	public default void Translate(Vector2f Translation)
	{
		Move(Translation.GetX(), Translation.GetY());
	}

	public default void Move(float X, float Y)
	{
		Move(new Vector2f(X, Y));
	}

	public default void Move(float[] Vars)
	{
		Translate(Vars);
	}

	public default void SetX(float X)
	{
		SetPosition(new Vector2f(X, 0));
	}

	public default void SetY(float Y)
	{
		SetPosition(new Vector2f(0, Y));
	}

	public default void SetPosition(float[] Position)
	{
		SetPosition(new Vector2f(Position[0], Position[1]));
	}

}
