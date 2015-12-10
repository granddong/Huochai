package com.example.huochai.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.huochai.R;

public class AssetsLoad {

	// 声音文件
	public static SoundPool soundPool;
	public static int dropSoundId;

	public static void load(Context context) {
		soundLoad(context);
	}


	public static void soundLoad(Context context) {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		dropSoundId = soundPool.load(context, R.raw.drop_sound, 1);
	}

	public static void playSound(Context contenxt, int soundId) {
		AudioManager manager = (AudioManager) contenxt
				.getSystemService(Context.AUDIO_SERVICE);
		// 获取当前音量和当前音量
		float currVol = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVol = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = currVol / maxVol;
		soundPool.play(soundId, maxVol, maxVol, 1, 0, 1.0f);

	}

}
