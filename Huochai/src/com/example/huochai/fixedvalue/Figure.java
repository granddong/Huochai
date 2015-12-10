package com.example.huochai.fixedvalue;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.huochai.R;
import com.example.huochai.fixedvalue.Calculate;
import com.example.huochai.fixedvalue.Category;
import com.example.huochai.fixedvalue.Distance;
import com.example.huochai.fixedvalue.Distinction;
import com.example.huochai.fixedvalue.Form;
import com.example.huochai.fixedvalue.Calculate.Expression;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Figure {

	private static Bitmap imgv, imgh;

	private final static int SX = 20, SY = 20;
	// 定义初级，中级，高级
	public static final int PRIMARY = 1, MIDDLE = 2, HIGH = 3;
	// 缩放未平移以后的矩阵
	private static Matrix matrix;
	// 位置到坐标的映射
	private final static Map<Point, Point> locationToIndex = new HashMap<Point, Point>();

	private final static Point[][] location = new Point[9][10];

	private static int drawlong, drawshort;

	private final static Set<Integer> EMPTY = new HashSet<Integer>();

	private final static Paint paint = new Paint();
	
	private static Point recyclePoint;
	// 级别
	private int level;
	// 当前种类
	private int index;
	private final List<Set<Integer>> showNumberSet = new ArrayList<Set<Integer>>(9);
	private List<Integer> showlist;
	private Expression ex;
	private int start;
	// 是否要加一个
	private boolean waitToAdd;
	// 剩余多少个
	private int count;

	private int lasti, lastj;

	private boolean hasLast;

	private int movex, movey;

	private boolean moving;

	private int deg;

	private View view;

	private ExecutorService executor;
	
	public int getCount() {
		return count;
	}

	/**
	 * 得到提示信息
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 得到要显示的内容
	 */
	public List<Set<Integer>> getShowNumberSet() {
		return showNumberSet;
	}
	
	public Figure(View view, int level) {
		this.level = level;
		this.view = view;
		nextQuestion();
		executor = Executors.newSingleThreadExecutor();//创建一个单线程，来执行工作
	}

	public static void load(Resources resources, int width) {
		InputStream is = resources.openRawResource(R.drawable.h);
		imgh = BitmapFactory.decodeStream(is);
		is = resources.openRawResource(R.drawable.v);
		imgv = BitmapFactory.decodeStream(is);

		int imglong = imgh.getWidth();
		int imgwidth = imgh.getHeight();

		int dt = (width - SX * 2) / 9;
		int length = (int) ((float) dt / 5 * 4);
		int half = length / 2;

		Distance[] dis = Distance.getDistance();

		locationToIndex.clear();
		for (int i = 0; i < location.length; i++) {
			for (int j = 0; j < location[i].length; j++) {
				int x = SX + i * dt + dis[j].dx * half;
				//int x = SX+ i * dt;
				int y = SY + dis[j].dy * half;
				//int y = SY;
				Point point = new Point(x, y);
				location[i][j] = point;
				locationToIndex.put(point, new Point(i, j));
			}
		}
		
		matrix = new Matrix();
		float f = length / (float) imglong;
		matrix.setScale(f, f);

		drawlong = length;
		drawshort = (int) (f * imgwidth);
		recyclePoint = new Point(width, (SY + length) * 2);
	}

	private static int distance(float x, float y, Point point) {
		return (int) ((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
	}

	/**
	 * 设置下一个问题
	 */
	public void nextQuestion() {
		index = Calculate.random.nextInt(level * 2);
		Calculate cal = Calculate.getCalculate(Category.values()[index]);
		ex = cal.getRandomExpression();
		showlist = ex.getShowList();
		reset();
	}

	/**
	 * 判断是否成立
	 */
	private boolean isRight() {
		if (count > 0)
			return false;
		final List<Integer> list = new ArrayList<Integer>();
		for (Set<Integer> set : showNumberSet) {
			int zhi = Form.getData(set);
			if (zhi == Form.NOT_EXISTS)
				return false;
			else
				list.add(zhi);
		}
		return Calculate.isRight(list);
	}

	/**
	 * 添加一根火柴
	 */
	public void add(int location, int zhi) {
		showNumberSet.get(location).add(zhi);
	}

	/**
	 * 去掉一根火柴
	 */
	public void remove(int location, int zhi) {
		showNumberSet.get(location).remove(zhi);
	}

	/**
	 * 判断某个位置是否包含某个火柴棒
	 */
	public boolean contains(int location, int zhi) {
		return showNumberSet.get(location).contains(zhi);
	}

	/**
	 * 复位
	 */
	public void reset() {
		showNumberSet.clear();
		int size = showlist.size();
		start = (9 - size) / 2;
		for (int i = 0; i < start; i++)
			showNumberSet.add(EMPTY);
		for (int zhi : showlist) {
			showNumberSet.add(Collections.synchronizedSet(Form.getDataSetCopy(zhi)));
		}
		int en = 9 - size - start;
		for (int i = 0; i < en; i++)
			showNumberSet.add(EMPTY);

		count = index % 2 + 1;
		waitToAdd = (index == 2 || index == 3);
	}

	public Expression getEx() {
		return ex;
	}

	public Set<Integer> getShowNumberSetAt(int i) {
		return showNumberSet.get(i);
	}

	public void onDraw(Canvas canvas) {
		Distance[] dis = Distance.getDistance();
		for (int i = 0; i < showNumberSet.size(); i++) {
			Set<Integer> set = showNumberSet.get(i);
			synchronized (set) {
				for (int num : set) {
					Matrix matrix = new Matrix(Figure.matrix);
					if (dis[num].horizontal) {
						matrix.postTranslate(location[i][num].x - drawlong / 2, location[i][num].y - drawshort / 2);
						canvas.drawBitmap(imgh, matrix, paint);
					} else {
						matrix.postTranslate(location[i][num].x - drawshort / 2, location[i][num].y - drawlong / 2);
						canvas.drawBitmap(imgv, matrix, paint);
					}
				}
			}
		}
		if (moving) {
			Matrix matrix = new Matrix(Figure.matrix);
			matrix.postTranslate(movex - drawlong / 2, movey - drawshort / 2);
			deg += 20;
			deg %= 360;
			matrix.postRotate(deg, movex, movey);
			canvas.drawBitmap(imgh, matrix, paint);
		}
	}

	private boolean isConsider(int i, int j) {
		Set<Integer> set = showNumberSet.get(i);
		if (set == EMPTY)
			return false;
		if (ex.getEqualLocation() + start == i)
			return false;
		if (ex.getSymbolLocation() + start == i) {
			if (j != 7)
				return false;
			else {
				if (waitToAdd)
					return !set.contains(j);
				return set.contains(j);
			}
		}
		if (j > 6)
			return false;
		if (waitToAdd) {
			return !set.contains(j);
		} else {
			return set.contains(j);
		}
	}

	private Point near(float x, float y) {
		Point re = null;
		int distance = 10000;
		for (int i = 0; i < location.length; i++)
			for (int j = 0; j < location[i].length; j++) {
				if (isConsider(i, j)) {
					int nd = distance(x, y, location[i][j]);
					if (nd < distance) {
						distance = nd;
						re = new Point(i, j);
					}
				}
			}
		return re;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (count <= 0)
			return false;
		float x = event.getX();
		float y = event.getY();

		// 去模式
		if (index < 2) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Point point = near(x, y);
				if (point != null) {
					remove(point.x, point.y);
					count--;
					view.invalidate();
					return isRight();
				}
			}
		} else if (index < 4) {// 加模式
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Point point = near(x, y);
				if (point != null) {
					add(point.x, point.y);
					count--;
					view.invalidate();
					return isRight();
				}
			}
		} else {// 移动模式
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Point point = near(x, y);
				if (point != null) {
					remove(point.x, point.y);
					lasti = point.x;
					lastj = point.y;
					hasLast = true;
					waitToAdd = true;
					view.invalidate();
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				Point point = near(x, y);
				moving = false;
				if (point != null && (point.x != lasti || point.y != lastj)) {
					add(point.x, point.y);
					hasLast = false;
					waitToAdd = false;
					count--;
					view.invalidate();
					return isRight();
				}
				if (hasLast) {
					add(lasti, lastj);
					hasLast = false;
					waitToAdd = false;
					view.invalidate();
				}
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				moving = true;
				movex = (int) event.getX();
				movey = (int) event.getY();
				view.invalidate();
			}
		}
		return false;
	}

	public void showResult() {
		reset();
		view.invalidate();
		List<Integer> resultList = ex.getResultList();

		int t = 0;

		if (index < 2) {
			Point[] prm = new Point[count];
			for (int i = 0; i < resultList.size() && t < count; i++) {
				int sw = showlist.get(i);
				int rs = resultList.get(i);

				if (rs != sw) {
					Set<Integer> set = Distinction.get(sw, rs);
					for (int zhi : set) {
						prm[t++]=new Point(i+start,zhi);
					}
				}
			}
			for (Point p:prm){
				executor.execute(move(p,recyclePoint));
			}
		}

		else if (index < 4) {
			Point[] padd = new Point[count];
			for (int i = 0; i < resultList.size() && t < count; i++) {
				int sw = showlist.get(i);
				int rs = resultList.get(i);

				if (rs != sw) {
					Set<Integer> set = Distinction.get(rs, sw);
					for (int zhi : set) {
						padd[t++] = new Point(i+start,zhi);
					}
				}
			}
			for (Point p : padd){
				executor.execute(move(recyclePoint,p));
			}
		} else {
			int tt=0;
			Point[] prm = new Point[count];
			Point[] padd = new Point[count];
			for (int i = 0; i < resultList.size() && (t < count || tt < count); i++) {
				int sw = showlist.get(i);
				int rs = resultList.get(i);

				if (rs != sw) {
					Set<Integer> set = Distinction.get(sw, rs);
					for (int zhi : set) {
						prm[t++] = new Point(i+start,zhi);
					}
					set = Distinction.get(rs, sw);
					for (int zhi : set) {
						padd[tt++] = new Point(i+start,zhi);
					}
				}
			}
			for (int i = 0;i < count; i++){
				executor.execute(move(prm[i], padd[i]));
			}
		}

	}
	
	
	private Runnable move(final Point start,final Point destinct){
		return new Runnable(){
			@Override
			public void run() {
				Point stl=start, del=destinct;
				if (start!=recyclePoint){
					stl=location[start.x][start.y];
					remove(start.x, start.y);
				}
				if (destinct!=recyclePoint){
					del=location[destinct.x][destinct.y];
				}
				moving=true;
				float dx=(del.x - stl.x) / 30f;
				float dy=(del.y - stl.y) / 30f;

				for (int i=0; i < 30; i++){
					movex=(int)(stl.x+dx*i);
					movey=(int)(stl.y+dy*i);
					view.postInvalidate();
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
						moving=false;
					}finally{
						view.postInvalidate();
					}
				}
				if (del != recyclePoint){
					add(destinct.x, destinct.y);
				}
				moving =false;
				view.postInvalidate();
			}		
		};
	}
}