package AI.StateMachine;

import java.util.HashMap;
import java.util.Set;

import Utils.Condition;

public class State
{

	public HashMap<Condition, State> Transitions = new HashMap<Condition, State>();

	Runnable StateAction;

	public State(Runnable StateAction)
	{
		this.StateAction = StateAction;
	}

	public State Update()
	{
		Set<Condition> Conditions = Transitions.keySet();

		for (Condition Key : Conditions)
		{
			if (Key.Test())
			{
				return Transitions.get(Key);
			}
		}

		return this;
	}

	public Runnable GetStateAction()
	{
		return StateAction;
	}

	public void RunAction()
	{
		StateAction.run();
	}

	public void AddTransition(State TransitionTo, Condition Condition)
	{
		Transitions.put(Condition, TransitionTo);
	}

	public void RemoveTransition(State TransitionTo, Condition Condition)
	{
		Transitions.remove(Condition, TransitionTo);
	}

}
