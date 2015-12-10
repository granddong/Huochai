package com.example.huochai.fixedvalue;
public class Change implements Comparable<Change> {
	
	int orgin;
	int result;
	
	public int getOrgin() {
		return orgin;
	}

	public int getResult() {
		return result;
	}
	
	public Change(int orgin, int result) {
		super();
		this.orgin = orgin;
		this.result = result;
	}

	public Change fan(){
		return new Change(result, orgin);
	}
	
	@Override
	public String toString() {
		return "Change [orgin=" + orgin + ", result=" + result + "]";
	}

	@Override
	public int compareTo(Change o) {
		if (orgin != o.orgin)
			return orgin - o.orgin;
		return result - o.result;
	}
	
	@Override
	public int hashCode() {
		return 31 * (31 + orgin) + this.result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Change other = (Change) obj;
		if (orgin != other.orgin)
			return false;
		if (result != other.result)
			return false;
		return true;
	}
}
