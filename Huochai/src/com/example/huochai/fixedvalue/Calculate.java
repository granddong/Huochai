package com.example.huochai.fixedvalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.example.huochai.fixedvalue.Compose.Plus;



public class Calculate {
	
	public static Random random = new Random();
	private final static Map<Category, Calculate> map = new HashMap<Category, Calculate>();
	private Category cate;
	List<Base> baseList;
	int size;
	
	private Calculate(Category cate) {
		this.cate = cate;
		getBaseList();
	}
	
	/**
	 * ȷ������һ��SetBase
	 */
	private void getBaseList() {
		Set<Base> set = null;
		switch (cate) {
			case ADD_ONE:
			case MINUS_ONE:
				set = GetBase.getAddOne();
				break;
			case ADD_TWO:
			case MINUS_TWO:
				set = GetBase.getAddTwo();
				break;
			case MOVE_ONE:
				set = GetBase.getMoveOne();
				break;
			case MOVE_TWO:
				set = GetBase.getMoveTwo();
				break;
		}
		size = set.size();
		baseList = new ArrayList<Base>(size);
		for (Base base : set) {
			baseList.add(base);
		}
	}

	/**
	 * �Ƿ񽻻� result �� orgin
	 * 
	 * @return ��������򷵻�true;
	 */
	private boolean swap() {
		boolean re = false;
		switch (cate) {
		case ADD_ONE:
		case ADD_TWO:
			re = false;
			break;
		case MINUS_ONE:
		case MINUS_TWO:
			re = true;
			break;
		case MOVE_ONE:
		case MOVE_TWO:
			re = (random.nextInt(2) > 0);
			break;
		}
		
		return re;
	}
	
	/**
	 * ȡ�ñ��ʽ���� ��ȡ��base,��ȡ�÷���
	 */
	public Expression getRandomExpression() {
		int index = random.nextInt(size);
		Base base = baseList.get(index);
		boolean swap = swap();
		int[] orgin = base.getOrign();
		int[] result = base.getResult();
		if (!swap) {
			return new Expression(orgin, result);
		} else {
			return new Expression(result, orgin);
		}
	}

	public class Expression {
		// �Ƿ�Ϊ�ӷ�
		boolean isAdd;
		// ��ȷ������
		int[] dataRight;
		// ��ʾ������
		int[] dataShow;
		// �Ƿ�仯����
		boolean changeSymbol;
		// ��ȷ������
		private int[] result;
		// ԭʼ������
		private int[] orgin;
		// �ӷ�����
		private Plus plus;
		// ����λ��
		private int symbolLocation;
		// �Ⱥ�λ��
		private int equalLocation;
		// �����
		private List<Integer> resultList;
		// Ҫ��ʾ�����ݵ�list ���ݵĳ��Ⱦ���Ҫ��ʾ���ݵĳ��� ���ڼӺż�����Ⱥŵ�Լ����Form��
		List<Integer> showList;
		
		public boolean getisAdd() {
			return isAdd;
		}

		public int[] getDataRight() {
			return dataRight;
		}

		public int[] getDataShow() {
			return dataShow;
		}

		public boolean isChangeSymbol() {
			return changeSymbol;
		}
		
		/**
		 * ȡ������Ļ��Ҫ��ʾ�����ֺͷ��ŵ�list  list�Ĵ�С����Ҫ��ʾ���ݵĳ���    ���ڼӺż�����Ⱥŵ�Լ����Form��
		 */
		public List<Integer> getShowList() {
			return showList;
		}

		public int getSymbolLocation() {
			return symbolLocation;
		}

		public int getEqualLocation() {
			return equalLocation;
		}
		
		public List<Integer> getResultList(){
			return resultList;
		}

		private Expression(int[] orgin, int[] result) {
			this.result = result;
			this.orgin = orgin;
			plus = Compose.getPlus(result);
			isAdd = isAdd();
			caldataRight();
			caldataShow();
			calList();
		}

		private void addList(int i) {
			if (dataShow[i] > 99 || dataRight[i] > 99){
				showList.add(dataShow[i] / 100);
				resultList.add(dataRight[i] / 100);
			}
			if (dataShow[i] > 9 || dataRight[i] > 9){
				showList.add(dataShow[i] % 100 / 10);
				resultList.add(dataRight[i] % 100 / 10);
			}
			showList.add(dataShow[i] % 10);
			resultList.add(dataRight[i] % 10);
		}

		private void calList() {
			showList = new ArrayList<Integer>();//�޲ι��캯������ָ����С��ʱ��Ĭ�ϴ�СΪ10������
			resultList=new ArrayList<Integer>();
			addList(0);
			symbolLocation = showList.size();
			if (isAdd){
				resultList.add(Form.ADD_SYMBOL);
			}else{
				resultList.add(Form.MINUS_SYMBOL);
			}
			if (isAdd != changeSymbol) {
				showList.add(Form.ADD_SYMBOL);
			} else {
				showList.add(Form.MINUS_SYMBOL);
			}
			addList(1);
			equalLocation = showList.size();
			showList.add(Form.EQUAL_SYMBOL);
			resultList.add(Form.EQUAL_SYMBOL);
			addList(2);
		}

		/**
		 * ������ʾ������ ���ݵĳ��Ⱦ���Ҫ��ʾ���ݵĳ��� ���ڼӺż�����Ⱥŵ�Լ����Form��
		 */
		private void caldataShow() {
			Set<Integer> guo = new HashSet<Integer>();
			String str = Calculate.format(dataRight);
			for (int i = 0; i < result.length; i++) {
				int find = result[i];
				if (result[i] < 10) {
					ArrayList<Integer> arrayList = has(str, find);
					arrayList.removeAll(guo);
					int size = arrayList.size();
					int replace = arrayList.get(random.nextInt(size));
					guo.add(replace);
					str = replace(str, replace, orgin[i]);
				} else {
					changeSymbol = true;
				}
			}
			dataShow = format(str);
		}

		/**
		 * ����result
		 */
		private void caldataRight() {
			dataRight = new int[3];
			if (isAdd) {
				dataRight[2] = plus.getC();
				if (random.nextBoolean()) {
					dataRight[0] = plus.getA();
					dataRight[1] = plus.getB();
				} else {
					dataRight[1] = plus.getA();
					dataRight[0] = plus.getB();
				}
			} else {
				dataRight[0] = plus.getC();
				if (random.nextBoolean()) {
					dataRight[2] = plus.getA();
					dataRight[1] = plus.getB();
				} else {
					dataRight[1] = plus.getA();
					dataRight[2] = plus.getB();
				}
			}
		}

		/**
		 * ��������ȷ�Ľ�����Ƿ�ʹ�üӷ�
		 */
		private boolean isAdd() {
			for (int i = 0; i < result.length; i++) {
				if (result[i] == 10)
					return true;
				if (result[i] == 11)
					return false;
			}
			return (random.nextBoolean());
		}
	}

	public static Calculate getCalculate(Category cate) {
		Calculate re = map.get(cate);
		if (re != null)
			return re;
		re = new Calculate(cate);
		map.put(cate, re);
		return re;
	}
	
	private static String replace(String str, int index, int org) {
		return str.substring(0, index) + org + str.substring(index + 1);
	}

	private static ArrayList<Integer> has(String str, int zhi) {
		ArrayList<Integer> re = new ArrayList<Integer>();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);//�����ض������µ��ַ�
			if (ch - '0' == zhi) {
				re.add(i);
			}
		}
		return re;
	}

	private static String format(int[] r) {
		String re = "";
		for (int res : r) {
			re += format(res);
		}
		return re;
	}

	private static String format(int r) {
		String re = "" + r;
		while (re.length() < 3) {
			re = "#" + re;
		}
		return re;
	}

	private static int[] format(String str) {
		int[] re = new int[3];
		for (int i = 0; i < 3; i++) {
			String sub = str.substring(i * 3, i * 3 + 3);
			while (sub.contains("#"))
				sub = sub.substring(1);
			re[i] = Integer.parseInt(sub);
		}
		return re;
	}

	/**
	 * ���ڼӺż�����Ⱥŵ�Լ����Form�� ��ʾ�������Ƿ񹹳ɵ�ʽ
	 * @param list ����
	 * @return �Ƿ񹹳ɵ�ʽ
	 */
	public static boolean isRight(List<Integer> list) {
		Integer a = null, b = null;
		for (int i = 0; i < list.size(); i++) {
			int zhi = list.get(i);
			if (zhi == Form.NOT_EXISTS)
				return false;
			if (zhi > 9) {
				if (zhi < Form.EQUAL_SYMBOL)
					a = i;
				else if (zhi == Form.EQUAL_SYMBOL) {
					b = i;
				}
			}
		}
		if (a == null || b == null)
			return false;

		int da = 0;
		int db = 0;
		int dc = 0;

		for (int i = 0; i < a; i++) {
			if (list.get(i) != Form.EMPTY)
				da = da * 10 + list.get(i);
		}

		for (int i = a + 1; i < b; i++) {
			db = db * 10 + list.get(i);
		}

		for (int i = b + 1; i < list.size(); i++) {
			if (list.get(i) != Form.EMPTY)
				dc = dc * 10 + list.get(i);
		}

		boolean isAdd = list.get(a) == Form.ADD_SYMBOL;

		if (isAdd)
			return da + db == dc;
		else
			return da - db == dc;
	}
}
