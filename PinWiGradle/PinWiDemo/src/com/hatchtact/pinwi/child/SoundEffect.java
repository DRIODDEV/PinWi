package com.hatchtact.pinwi.child;



import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
 

public class SoundEffect {
	private SoundPool sp;
	private int soundId;

	public SoundEffect(Context context, int resid)
	{ 
		sp = new SoundPool(1, AudioManager.STREAM_MUSIC,100);
		soundId = sp.load(context, resid, 1);
	}
	public void play(float volume)
	{
		
		sp.play(soundId, volume, volume, 1, 0, 1);
		
	}
	public void stop()
	{
		sp.pause(soundId);
		sp.stop(soundId);
	}

	public void release()
	{
		sp.release();
	}	
}
