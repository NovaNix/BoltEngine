package Sound;

import Vectors.Vector2f;

public class LocalSound extends Sound
{

	Vector2f Location;
	AudioObserver Observer;

	public LocalSound(String AudioPath, Vector2f Location, AudioObserver Observer)
	{
		super(AudioPath);
		this.Location = Location;
		this.Observer = Observer;
	}

	@Override
	public void Update()
	{
		float Distance = Location.GetDistanceTo(Observer.GetObserverPosition());

		float Volume = 500 - Distance;

		if (Volume < 0)
		{
			Volume = 0;
		}

		SetVolume(Volume);

	}

}
