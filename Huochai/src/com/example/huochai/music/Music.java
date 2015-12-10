package com.example.huochai.music;

import com.example.huochai.R;
import android.content.Context;
import android.media.MediaPlayer;

public class Music {

	static MediaPlayer player;
	static int soundId1,soundId2;
	Context context;
	
	public Music(Context context)
	{
		this.context=context;
		player=MediaPlayer.create(context, R.drawable.clock);	
		player.setLooping(true);
	}
	
	public static void play()
	{
		player.start();
	}
	
	public boolean isPlaying()
	{
		boolean re=false;
		try
		{
			re=player.isPlaying();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			re=false;
		}
		return re;
	}
	
	public static void pause()
	{
		player.pause();
	}
	
	public static void stop()
	{
		player.stop();
		player.release();
	}
	
	public static void resume()
	{
		player.start();
	}
}
