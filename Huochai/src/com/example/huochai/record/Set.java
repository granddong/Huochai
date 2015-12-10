package com.example.huochai.record;

public class Set {

	static boolean isMusic = true;

	public static boolean isMusic() {
		return isMusic;
	}

	public static void setMusic() {
		if (Set.isMusic) {
			Set.isMusic = false;
		} else {
			Set.isMusic = true;
		}
	}

	public static void setnewMusic() {
			Set.isMusic = true;
	}
	
	public static void disMusic() {
			Set.isMusic = false;
	}
	
	public static void setMusic(boolean music) {
		Set.isMusic = music;
	}

	
}
