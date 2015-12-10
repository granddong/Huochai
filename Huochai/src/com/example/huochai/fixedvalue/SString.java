package com.example.huochai.fixedvalue;

public class SString {
	private final static String[] str={
		"请你移除一根火柴棒并使等式成立... ",
		"请你移除两根火柴棒并使等式成立... ",
		"请你添加一根火柴棒并使等式成立... ",
		"请你添加两根火柴棒并使等式成立... ",
		"请你移动一根火柴棒并使等式成立... ",
		"请你移动两根火柴棒并使等式成立... "
	};
	
	public static String getString(int i)
	{
		return str[i];
	}
}
