package com.example.huochai.view;


import com.example.huochai.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

	private static final String NAMESPACE ="http://schemas.android.com/apk/res/com.example.huochai";
	private TextView tvTitle;
	private TextView tvDesc;
	private CheckBox cbstatus;
	private String mTitle;
	private String mDescOn;
	private String mDescOf;
	
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//int attributeCount = attrs.getAttributeCount();
		mTitle = attrs.getAttributeValue(NAMESPACE,"title");
		mDescOn = attrs.getAttributeValue(NAMESPACE,"desc_on");
		mDescOf = attrs.getAttributeValue(NAMESPACE,"desc_of");
		initView();
	}

	public SettingItemView(Context context) {
		super(context);
		initView();
	}
	private void initView()
	{
		//将定义好的布局文件设置给当前的settingactivityView
		View.inflate(getContext(), R.layout.view_setting_item,this);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		tvDesc = (TextView) findViewById(R.id.tv_desc);
		cbstatus = (CheckBox) findViewById(R.id.cb_status);
		setTitle(mTitle);
	}
	public void setTitle(String title)
	{  
		tvTitle.setText(title);
	}
	
	public void setDesc(String desc)
	{
		tvDesc.setText(desc);
	}
	
	//判断当前勾选状态
	public boolean isChecked()
	{
		return cbstatus.isChecked();
	}
	
	public void setChecked(boolean check)
	{
		cbstatus.setChecked(check);
		if(check)
		{
			setDesc(mDescOn);
		}
		else
		{
			setDesc(mDescOf);
		}
	}
}

