package AI.StateMachine;

import java.util.HashMap;
import java.util.Set;

import Utils.Question;

public class State
{

	public HashMap<Question, State> Transitions = new HashMap<Question, State>();

	Runnable StateAction;

	public State(Runnable StateAction)
	{
		this.StateAction = StateAction;
	}

	public State Update()
	{
		Set<Question> Conditions = Transitions.keySet();

		for (Question Key : Conditions)
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

	public void AddTransition(State TransitionTo, Question Condition)
	{
		Transitions.put(Condition, TransitionTo);
	}

	public void RemoveTransition(State TransitionTo, Question Condition)
	{
		Transitions.remove(Condition, TransitionTo);
	}

}
