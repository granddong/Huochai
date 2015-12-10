package com.example.huochai.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.huochai.R;
import com.example.huochai.view.ViewPagerAdapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Guide3Activity extends FragmentActivity {

	private ViewPager viewPage;
	private Fragment5 mFragment1;
	private Fragment6 mFragment2;
	private PagerAdapter mPgAdapter;
	private RadioGroup dotLayout;
	private List<Fragment> mListFragment = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide1);

		initView();
		viewPage.setOnPageChangeListener(new MyPagerChangeListener());
	}

	private void initView() {
		dotLayout = (RadioGroup) findViewById(R.id.advertise_point_group);
		viewPage = (ViewPager) findViewById(R.id.viewpager);
		mFragment1 = new Fragment5();
		mFragment2 = new Fragment6();
		mListFragment.add(mFragment1);
		mListFragment.add(mFragment2);
		mPgAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				mListFragment);
		viewPage.setAdapter(mPgAdapter);

	}

	public class MyPagerChangeListener implements OnPageChangeListener {

		public void onPageSelected(int position) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
		}

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
