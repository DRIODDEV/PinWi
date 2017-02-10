package com.hatchtact.pinwi.child;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

public class ChildMusicPlayer
{
	private MediaPlayer mediaPlayer;

	
	public ChildMusicPlayer(Context c,int resID)
	{
		mediaPlayer = MediaPlayer.create(c, resID);
		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MediaPlayer getMediaPlayer()
	{
		return mediaPlayer;
	}
	
	public void pause()
	{
		mediaPlayer.pause();
	}

	
	public void play()
	{
		mediaPlayer.start();
	}

	public void stop()
	{
		if(mediaPlayer.isPlaying())
		{
			mediaPlayer.pause();
		}
		mediaPlayer.stop();
	}

	public void release()
	{
		if(null!=mediaPlayer)
		{
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
}
