package com.example.huochai.view;

import com.example.huochai.fixedvalue.SString;
import com.example.huochai.activity.MatchActivity;
import com.example.huochai.fixedvalue.Figure;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.widget.TextView;

public class ExView extends View{
	
	Figure figure;
	int level = 2;
	int height, width;
	int count=0;
	int num=5;//每个等级设置5道题目
	int subcount;
	boolean end;
	Context mContext;
	TextView hint;
	private SharedPreferences mpref;
	
	public void setHint(TextView hint){
		this.hint = hint;
	}
	

	public Figure getFigure(){
		return figure;
	}
	
	public int getheight() {
		return height;
	}

	public int getwidth() {
		return width;
	}
	
	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}
	
	public ExView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		end = false;
		this.width = MatchActivity.width;
		this.height = ((width - 40) * 2) / 9 + 10;
		Figure.load(getResources(), width);
		figure = new Figure(this, level + 1);
		mpref=context.getSharedPreferences("setcount", 0);
		//String num=mpref.getString("setcount", "");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		figure.onDraw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (figure.onTouchEvent(event)) {
			if (!end) {
				new AlertDialog.Builder(mContext)
						.setTitle("答题成功")
						.setPositiveButton("下一题", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									String sub=mpref.getString("setcount", "");
									if(TextUtils.isEmpty(sub))
									{
										if(count<num)
										{
											next();
											count++;
										}
										else
										{
											System.out.println("游戏结束");
											/**
											 * 游戏结束，当时间结束或者答题结束后显示游侠结束
											 */
										}
									}
									else
									{
									num=Integer.parseInt(sub)-1;
									if(count<num)
									{
										next();
										count++;
									}
									else
									{
										System.out.println("游戏结束");
										/**
										 * 游戏结束，当时间结束或者答题结束后显示游侠结束
										 */
									}
									}
									
								}
						}) 
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {}
						})
						.show();
				return false;
			}
			end = true;
		} else {
			if(figure.getCount() <= 0) {
				new AlertDialog.Builder(mContext)
						.setMessage("回答错误")
						.setPositiveButton("重做", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {
									reset();
								}
						}) 
						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {}
						})
						.show();
				return false;
			}
		}
		return true;
	}

	public void next() {
		figure.nextQuestion();
		end = false;
		hint.setText(SString.getString(figure.getIndex()));
		invalidate();
	}

	public void reset() {
		figure.reset();
		invalidate();
	}

	public void showAnswer() {
		figure.showResult();
	}
}