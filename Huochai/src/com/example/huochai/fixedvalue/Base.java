package com.example.huochai.fixedvalue;
import java.util.Arrays;

public class Base {
	
	private Change[] list;
	
	public Change[] getList() {
		return list;
	}
	
	public Base(Change[] list) {
		super();
		this.list = list;
		Arrays.sort(this.list);
	}

	public Base(Change change) {
		list = new Change[1];
		list[0] = change;
	}
	
	public Base(Base b1, Base b2) {
		int n = b1.list.length + b2.list.length;
		//int n = b2.list.length ;
		list = new Change[n];
		for (int i = 0; i < b1.list.length; i++) {
			list[i]= b1.list[i];
		}
		for (int i = b1.list.length; i < n; i++) {
			list[i] = b2.list[i - b1.list.length];
		}
		Arrays.sort(list);
	}
	
	
	public Base fan() {
		Change[] nlist = new Change[list.length];
		int count = 0;
		for (Change change : list) {
			nlist[count++] = change.fan();
		}
		return new Base(nlist);
	}

	public boolean isGood() {
		int count = 0;
		for (Change change : list) {
			if (change.getOrgin() > 9)
				count++;
		}
		return count < 2;
	}
	
	public int[] getResult() {
		int[] re = new int[list.length];
		for (int i = 0; i < re.length; i++){
			re[i] = list[i].getResult();
		}
		return re;
	}
	
	public int[] getOrign() {
		int[] re = new int[list.length];
		for (int i = 0; i < re.length; i++){
			re[i] = list[i].getOrgin();
		}
		return re;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(list);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Base other = (Base) obj;
		if (!Arrays.equals(list, other.list))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Base [list=" + Arrays.toString(list) + "]";
	}
}
