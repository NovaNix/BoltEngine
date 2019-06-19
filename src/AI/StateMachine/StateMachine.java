package AI.StateMachine;

import AI.AI;
import Utils.Tickable;

public class StateMachine implements Tickable
{

	State CurrentState;

	public StateMachine(State CurrentState)
	{
		this.CurrentState = CurrentState;
	}

	public void UpdateState()
	{
		CurrentState = CurrentState.Update();
	}

	public State GetCurrentState()
	{
		return CurrentState;
	}

	@Override
	public void Tick()
	{
		UpdateState();

		CurrentState.RunAction();
	}

}
