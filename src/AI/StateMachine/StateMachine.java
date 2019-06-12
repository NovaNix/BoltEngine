package AI.StateMachine;

public class StateMachine
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

}
