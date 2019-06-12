package AI.StateMachine;

import AI.AI;

public class StateMachine extends AI
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
