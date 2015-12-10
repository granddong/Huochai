package com.example.huochai.fixedvalue;
import java.util.LinkedList;
import java.util.List;

public class GetChange {
	
	private static List<Change> addOne, addTwo, moveOne, moveTwo;

	public static List<Change> getAddOne() {
		if (addOne == null) {
			List<Change> re = new LinkedList<Change>();
			re.add(new Change(5, 6));
			re.add(new Change(1, 7));
			re.add(new Change(0, 8));
			re.add(new Change(6, 8));
			re.add(new Change(9, 8));
			re.add(new Change(3, 9));
			re.add(new Change(5, 9));
			re.add(new Change(11, 10));
			addOne = re;
		}
		return addOne;
	}

	public static List<Change> getAddTwo() {
		if (addTwo == null) {
			List<Change> re = new LinkedList<Change>();
			re.add(new Change(7, 3));
			re.add(new Change(1, 4));
			re.add(new Change(2, 8));
			re.add(new Change(3, 8));
			re.add(new Change(5, 8));
			re.add(new Change(4, 9));
			addTwo = re;
		}
		return addTwo;
	}

	public static List<Change> getMoveOne() {
		if (moveOne == null) {
			List<Change> re = new LinkedList<Change>();
			re.add(new Change(6, 0));
			re.add(new Change(9, 0));
			re.add(new Change(3, 2));
			re.add(new Change(5, 3));
			re.add(new Change(9, 6));
			moveOne = re;
		}
		return moveOne;
	}

	public static List<Change> getMoveTwo() {
		if (moveTwo == null){
			List<Change> re = new LinkedList<Change>();
			re.add(new Change(5,2));
			moveTwo = re;
		}
		return moveTwo;
	}
}