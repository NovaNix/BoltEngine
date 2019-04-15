package Rendering.Animation;

import java.awt.Image;

public class Animation
{

	Image[] Frames;
	float FPS;

	public Animation(Image[] Frames, float FPS)
	{
		this.Frames = Frames;
		this.FPS = FPS;
	}

	public Image GetFrameAt(int Frame)
	{
		return Frames[Frame];
	}

}
