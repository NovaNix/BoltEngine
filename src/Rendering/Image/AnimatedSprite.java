package Rendering.Image;

import java.awt.image.BufferedImage;

import Rendering.Animation.FrameRegulator;

public class AnimatedSprite extends Sprite
{

	Sprite[] Sprites;

	Sprite ActiveSprite;

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

	@Override
	protected void CompileImage()
	{
		ActiveSprite.SwitchToPalette(Colors);
		this.Compiled = ActiveSprite.GetImage();
	}

	public Texture GetImage()
	{
		ActiveSprite = Sprites[Clock.GetRelativeFrame(Sprites.length)];

		this.CompileImage();

		return Compiled;
	}

	public int[] GetPixelData()
	{
		return ActiveSprite.GetPixelData();
	}

}
