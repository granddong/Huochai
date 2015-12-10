package com.example.huochai.fixedvalue;
import java.util.LinkedList;
import java.util.List;

public class Compose {
	public final static int TEN = 10;
	private static int[][] data;

	static {
		data = new int[4950][];
		int count = 0;
		for (int i = 1; i < 100; i++) {
			for (int j = 1; j <= i; j++) {
				data[count] = calComposeAdd(i, j);
				count++;
			}
		}
	}

	private static int[] calCompose(int a) {
		int[] re = new int[TEN];
		while (a > 0) {
			int m = a % TEN;
			re[m]++;
			a = a / TEN;
		}
		return re;
	}

	private static int[] calComposeAdd(int a, int b) {
		int[] re = new int[TEN];
		int[] rea = calCompose(a);
		int[] reb = calCompose(b);
		int[] rec = calCompose(a + b);
		for (int i = 0; i < TEN; i++) {
			re[i] += (rea[i] + reb[i] + rec[i]);
		}
		return re;
	}

	private static int[] calCompose(int[] result) {
		int[] re = new int[TEN];
		for (int r : result) {
			if (r < TEN) {
				re[r]++;
			}
		}
		return re;
	}

	private static List<Integer> contain(int[] result) {
		List<Integer> re = new LinkedList<Integer>();
		int[] compose = calCompose(result);
		int count = 0;
		for (int com : compose) {
			if (com > 0)
				count++;
		}
		int[] have = new int[count];
		count = 0;
		for (int i=0;i<compose.length;i++){
			if (compose[i]> 0)
				have[count++] = i;
		}
		
		for (int i = 0; i < 4950; i++) {
			int j;
			for (j = 0; j < count; j++) {
				if (data[i][have[j]] < compose[have[j]]) {
					break;
				}
			}
			if (j == count)
				re.add(i);
		}
		return re;
	}

	public static Plus getPlus(int[] result){

		List<Integer> list = contain(result);
		int index = Calculate.random.nextInt(list.size());
		int zhi = list.get(index);
		return new Plus(zhi);
	}
	
	public static class Plus {
		private static int[] toa;
		private static int[] tob;

		static {
			toa = new int[4950];
			tob = new int[4950];
			int count = 0;
			for (int i = 1; i < 100; i++) {
				for (int j = 1; j <= i; j++) {
					toa[count] = i;
					tob[count] = j;
					count++;
				}
			}
		}
		/**
		 * 第一个加数
		 */
		int a;

		/**
		 * 第二个加数
		 */
		int b;

		/**
		 * 和
		 */
		int c;

		private Plus(int zhi) {
			a = toa[zhi];
			b = tob[zhi];
			c = a + b;
		}

		/**
		 * @return the a
		 */
		public int getA() {
			return a;
		}

		/**
		 * @return the b
		 */
		public int getB() {
			return b;
		}

		/**
		 * @return the c
		 */
		public int getC() {
			return c;
		}
	}
}
