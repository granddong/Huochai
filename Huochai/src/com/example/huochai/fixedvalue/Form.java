package com.example.huochai.fixedvalue;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * ע��ӱ����л�õ�set���󣬲�Ҫ����add����remove����
 *
 */
@SuppressWarnings("unchecked")
public final class Form {
	
	public static final int LENGTH = 14;
	
	public static final int ADD_SYMBOL=10;
	
	public static final int MINUS_SYMBOL=11;
	
	public static final int EQUAL_SYMBOL=12;

	public static final int NOT_EXISTS=-1;
	
	public static final int EMPTY=13;
	
	private static Set<Integer>[] set;
	
	
	//�����ڷŵ�λ�ö�Ӧ����ţ��Ǹ���Ŷ�Ӧ����
	static{
		set = new Set[LENGTH];
		for (int i = 0; i < set.length; i++) {
			set[i] = new HashSet<Integer>();
		}
		set[0].add(6);
		set[0].add(4);
		set[0].add(3);
		set[0].add(2);
		set[0].add(1);
	    set[0].add(0);

		set[1].add(4);
		set[1].add(3);

		set[2].add(6);
		set[2].add(5);
		set[2].add(3);
		set[2].add(2);
		set[2].add(1);

		set[3].add(6);
		set[3].add(5);
		set[3].add(4);
		set[3].add(3);
		set[3].add(2);

		set[4].add(5);
		set[4].add(4);
		set[4].add(3);
		set[4].add(0);

		set[5].add(6);
		set[5].add(5);
		set[5].add(4);
		set[5].add(2);
		set[5].add(0);

		set[6].add(6);
		set[6].add(5);
		set[6].add(4);
		set[6].add(2);
		set[6].add(1);
		set[6].add(0);

		set[7].add(4);
		set[7].add(3);
		set[7].add(2);

		set[8].add(6);
		set[8].add(5);
		set[8].add(4);
		set[8].add(3);
		set[8].add(2);
		set[8].add(1);
		set[8].add(0);

		set[9].add(6);
		set[9].add(5);
		set[9].add(4);
		set[9].add(3);
		set[9].add(2);
		set[9].add(0);

		set[10].add(7);//+��
		set[10].add(5);

	    set[11].add(5);//-��

		set[12].add(9);//=��
		set[12].add(8);
	}

	public static Set<Integer>[] getDataSet() {
		return set;
	}
	
	public static Set<Integer> getDataSet(int data) {
		return set[data];
	}
	
	public static Set<Integer> getDataSetCopy(int data) {
		return (Set<Integer>) ((HashSet<Integer>) (set[data])).clone();
	}
	
	public static int getData(Set<Integer> dataSet){
		for (int i=0;i<set.length;i++){
			if (dataSet.equals(set[i])){
				return i;
			}	
		}
		return NOT_EXISTS;
	}
}
