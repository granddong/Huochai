package com.example.huochai.fixedvalue;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetBase {
	
	private static Set<Base> addOne, addTwo, moveOne, moveTwo;

	public static Set<Base> getAddOne() {
		if (addOne == null) {
			Set<Base> set = new HashSet<Base>();
			List<Change> changeList = GetChange.getAddOne();
			for (Change change : changeList) {
				set.add(new Base(change));
			}
			addOne = set;
		}
		return addOne;
	}

	public static Set<Base> getAddTwo() {
		if (addTwo == null) {
			Set<Base> set = new HashSet<Base>();
			List<Change> changeList = GetChange.getAddOne();
			for (Change change1 : changeList) {
				Base base1 = new Base(change1);
				for (Change change2 : changeList) {
					Base base2 = new Base(change2);
					Base base = new Base(base1, base2);
					if (base.isGood()) {
						set.add(base);
					}
				}
			}
			List<Change> twoList = GetChange.getAddTwo();
			for (Change change : twoList) {
				set.add(new Base(change));
			}
			addTwo = set;
		}
		return addTwo;
	}

	public static Set<Base> getMoveOne() {
		if (moveOne == null){
			Set<Base> set = new HashSet<Base>();
			List<Change> changeList = GetChange.getAddOne();
			for (Change change1 : changeList) {
				Base base1 = new Base(change1);
				for (Change change2 : changeList) {
					Base base2 = new Base(change2).fan();
					Base base = new Base(base1, base2);
					if (base.isGood() && !set.contains(base.fan())) {
						set.add(base);
					}
				}
			}
			List<Change> moveList = GetChange.getMoveOne();
			for (Change change : moveList) {
				set.add(new Base(change));
			}
			moveOne = set;
		}
		return moveOne;
	}

	public static Set<Base> getMoveTwo() {
		if (moveTwo == null){
			Set<Base> set = new HashSet<Base>();
			List<Change> changeOne = GetChange.getAddOne();
			List<Change> changeTwo = GetChange.getAddTwo();
			for (Change two : changeTwo) {
				Base bt = new Base(two);
				for (Change one : changeOne) {
					Base bone = new Base(one);
					for (Change one1 : changeOne) {
						Base bone1 = new Base(one1);						
						Base btemp = new Base(bone, bone1);						
						Base bre = new Base(bt.fan(), btemp);						
						if (bre.isGood() && !set.contains(bre.fan()))
							set.add(bre);
					}
				}
			}
			if (moveOne == null){
				getMoveOne();
			}
			for (Base b1 : moveOne)
				for (Base b2 : moveOne){
					Base base = new Base(b1, b2);
					if (base.isGood() && !set.contains(base.fan()))
						set.add(base);
					Base base2 = new Base(b1, b2.fan());
					if (base2.isGood() && !set.contains(base2.fan())){
						set.add(base2);
					}
				}
			moveTwo = set;
		}
		return moveTwo;
	}

}
