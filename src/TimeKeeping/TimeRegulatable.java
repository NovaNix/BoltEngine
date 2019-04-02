/*
 * 
 */
package TimeKeeping;

public interface TimeRegulatable
{

	boolean Started = false;
	
	boolean Paused = false;
	
	public void Start();
	public void Stop();
	
	public void Resume();
	public void Pause();

	public void Restart();
	
	public boolean IsPlaying();
	public boolean IsPaused();
	
	public void Forward();
	public void Reverse();
	
	public void Jump(int Amount);
	public void Rewind(int Amount);
	
	public void SetPosition(int Position);
	public int GetPosition();
	
	public void Loop();
	public void LoopBetween(int Position1, int Position2);
	public void Deloop();
	
	public boolean IsLooping();
	
	public void SpeedUp(float Amount);
	public void SlowDown(float Amount);
	
	public void SetSpeed(float Speed);
	
	public float GetSpeed();

}
