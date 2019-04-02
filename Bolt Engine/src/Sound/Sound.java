package Sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import IO.EasyLoader;

public abstract class Sound
{

	AudioInputStream AudioStream;
	
	AudioFormat Format;

	DataLine.Info Info;

	Clip AudioClip;
	
	boolean Muted;
	
	public Sound(String AudioPath)
	{
        
		try
		{	
			ClassLoader Loader = Thread.currentThread().getContextClassLoader();
			
			System.out.println("Finding file at " + AudioPath);
			System.out.println("Checking " + AudioPath);

			System.out.println("Resource as Stream = " + Loader.getResourceAsStream(AudioPath));
			
	        InputStream Input = Loader.getResourceAsStream(AudioPath);
	        
	        System.out.println("Input = " + (Input != null));
	        
	        AudioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(Input));
			
			Format = AudioStream.getFormat();
			Info = new DataLine.Info(Clip.class, Format);
			
			AudioClip = AudioSystem.getClip();
			
			AudioClip.open(AudioStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}
	
	public abstract void Update();
	
	public void Play()
	{
		AudioClip.start();
		
		AudioClip.setLoopPoints(0, -1);
		
		AudioClip.loop(0);
	}
	
	public void Play(int Start, int Stop)
	{
		AudioClip.start();
		
		AudioClip.setLoopPoints((Start / 1000) * AudioClip.getFrameLength(), (Stop / 1000) * AudioClip.getFrameLength());
		
		AudioClip.loop(0);
	}
	
	public void Play(int Start)
	{
		AudioClip.start();
		
		AudioClip.setLoopPoints((Start / 1000) * AudioClip.getFrameLength(), -1);
		
		AudioClip.loop(0);
	}
	
	public void Loop()
	{
		AudioClip.start();

		AudioClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void Loop(int Times)
	{
		AudioClip.start();
		
		AudioClip.setLoopPoints(0, -1);
		
		AudioClip.loop(Times);
	}
	
	public void Loop(int Times, int Start, int Stop)
	{
		AudioClip.start();

		AudioClip.setLoopPoints((Start / 1000) * AudioClip.getFrameLength(), (Stop / 1000) * AudioClip.getFrameLength());
		
		AudioClip.loop(Times);
	}

	public void Loop(int Times, int Start)
	{
		AudioClip.start();

		AudioClip.setLoopPoints((Start / 1000) * AudioClip.getFrameLength(), -1);
		
		AudioClip.loop(Times);
	}
	
	public void SetVolume(float Volume)
	{
		FloatControl gainControl = (FloatControl) AudioClip.getControl(FloatControl.Type.MASTER_GAIN);
		double gain = Volume / 100;
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);
	}

	public void Mute()
	{
		Muted = true;
		
		BooleanControl MuteControl = (BooleanControl) AudioClip.getControl(BooleanControl.Type.MUTE);
		MuteControl.setValue(Muted);
	}
	
	public void Unmute()
	{
		Muted = false;
		
		BooleanControl MuteControl = (BooleanControl) AudioClip.getControl(BooleanControl.Type.MUTE);
		MuteControl.setValue(Muted);
	}
	
	public void ToggleMute()
	{
		Muted = !Muted;
		
		BooleanControl MuteControl = (BooleanControl) AudioClip.getControl(BooleanControl.Type.MUTE);
		MuteControl.setValue(Muted);
	}
	
	public void SetMute(boolean Muted)
	{
		this.Muted = Muted;
		
		BooleanControl MuteControl = (BooleanControl) AudioClip.getControl(BooleanControl.Type.MUTE);
		MuteControl.setValue(Muted);
	}
	
	public void Close()
	{
		try
		{
			AudioClip.close();
			AudioStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean IsPlaying()
	{
		return AudioClip.isRunning();
	}
	
	public float GetLength()
	{
		return AudioClip.getMicrosecondLength() * 1000;
	}
	
}
