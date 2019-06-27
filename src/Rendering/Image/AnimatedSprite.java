package Rendering.Image;

import java.awt.image.BufferedImage;

import Rendering.Animation.FrameRegulator;

public class AnimatedSprite extends Sprite
{

	Sprite[] Sprites;

	FrameRegulator Clock;

	public AnimatedSprite(BufferedImage[] Template, float FPS)
	{
		super(Template[0]);

		Sprites = new Sprite[Template.length];

		for (int i = 0; i < Template.length; i++)
		{
			Sprites[i] = new Sprite(Template[i]);
		}

		Clock = new FrameRegulator(FPS);
	}

	public void StartAnimation()
	{
		Clock.Start();
	}

	public void StopAnimation()
	{
		Clock.Stop();
	}

	public int[] GetPixelData()
	{
		return Sprites[Clock.GetRelativeFrame(Sprites.length)].GetPixelData();
	}

	public Sprite GetCurrentFrame()
	{
		return Sprites[Clock.GetRelativeFrame(Sprites.length)];
	}

	@Override
	public void BindGraphic()
	{
		GetCurrentFrame().BindGraphic();
	}
}
