package com.example.huochai.fixedvalue;

public class Distance {
	public int dx;
	public int dy;
	
	/**ÊÇ·ñË®Æ½*/
	public boolean horizontal;

	private Distance(int dx, int dy, boolean horizontal) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.horizontal = horizontal;
	}
	
	private static Distance[] distance;

	static{
		distance = new Distance[10];
		distance[0] = new Distance(0, 1, false);
		distance[1] = new Distance(0, 3, false);
		distance[2] = new Distance(1, 0, true);
		distance[3] = new Distance(2, 1, false);
		distance[4] = new Distance(2, 3, false);
		distance[5] = new Distance(1, 2, true);
		distance[6] = new Distance(1, 4, true);
		distance[7] = new Distance(1, 2, false);
		distance[8] = new Distance(1, 1, true);
		distance[9] = new Distance(1, 3, true);
	}
	

	public static Distance[] getDistance() {
		return distance;
	}
	
	public static Distance getDistanceAt(int index) {
		return distance[index];
	}
}
