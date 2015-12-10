package com.example.huochai.fixedvalue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.graphics.Point;

public class Distinction {
	private final static Map<Point,Set<Integer>> map = new HashMap<Point, Set<Integer>>();
	
	public static Set<Integer> get(int sw,int rs){
		Point point = new Point(sw,rs);
		Set<Integer> re = map.get(point);
		if (re == null){
			re = put(sw,rs);
		}
		return re;
	}
	
	private static Set<Integer> put(int sw, int rs){
		Set<Integer> set = Form.getDataSetCopy(sw);
		set.removeAll(Form.getDataSet(rs));
		map.put(new Point(sw,rs), set);
		return set;
	}

}
