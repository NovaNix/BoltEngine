package Sound;

public class LocalSound
{

	Vector2f Location;
	AudioObserver Observer;

	public AmbientSound(String AudioPath, Vector2f Location, AudioObserver Observer)
	{
		super(AudioPath);
		this.Location = Location;
		this.Observer = Observer;
	}

	@Override
	public void Update()
	{
		float Distance = Location.DistanceTo(Observer.GetObserverPosition();
	
		float Volume = 500 - Distance;
		
		if (Volume < 0)
		{
			Volume = 0;
		}
	
		SetVolume(Volume);

	}

}
