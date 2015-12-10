package com.example.huochai.activity;

import com.example.huochai.R;
import com.example.huochai.music.Music;
import com.example.huochai.record.Set;
import com.example.huochai.utils.AssetsLoad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectViewActivity extends Activity {

	
	 Button imb1,imb2,imb3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_level);
		AssetsLoad.load(getApplicationContext());
		imb1=(Button) findViewById(R.id.leve1);
		imb1.setOnClickListener(new LevelClickListen());
		imb2=(Button) findViewById(R.id.leve2);
		imb2.setOnClickListener(new Leve2ClickListen());
		imb3=(Button) findViewById(R.id.leve3);
		imb3.setOnClickListener(new Leve3ClickListen());
		
	}
	
	class LevelClickListen implements OnClickListener{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AnimationSet animationSet=new AnimationSet(true);
			ScaleAnimation scaleAnimation=new ScaleAnimation(0.9f,0.9f,0.9f,0.9f,
					Animation.RELATIVE_TO_SELF,0.8f,
					Animation.RELATIVE_TO_SELF,0.8f);
			
	         scaleAnimation.setAnimationListener(new AnimationListener() {
					
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						}		
					
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						//动执行结束
						
						//MatchActivity.setlevel(1);
						Intent intent = new Intent();
						intent.setClass(SelectViewActivity.this, Guide1Activity.class);
						SelectViewActivity.this.startActivity(intent);
						SelectViewActivity.this.finish();
					}
				});
				
	        AssetsLoad.playSound(getApplicationContext(), AssetsLoad.dropSoundId);
			animationSet.addAnimation(scaleAnimation);
			animationSet.setFillBefore(true);
			animationSet.setDuration(100);
			imb1.startAnimation(animationSet);

		}
	}
	
	class Leve2ClickListen implements OnClickListener{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AnimationSet animationSet=new AnimationSet(true);
			ScaleAnimation scaleAnimation=new ScaleAnimation(0.9f,0.9f,0.9f,0.9f,
					Animation.RELATIVE_TO_SELF,0.8f,
					Animation.RELATIVE_TO_SELF,0.8f);
			
	         scaleAnimation.setAnimationListener(new AnimationListener() {
					
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						}		
					
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						//动执行结束
						
						//MatchActivity.setlevel(1);
						Intent intent = new Intent();
						intent.setClass(SelectViewActivity.this, Guide2Activity.class);
						SelectViewActivity.this.startActivity(intent);
						SelectViewActivity.this.finish();
					}
				});
				
	        AssetsLoad.playSound(getApplicationContext(), AssetsLoad.dropSoundId);
			animationSet.addAnimation(scaleAnimation);
			animationSet.setFillBefore(true);
			animationSet.setDuration(100);
			imb2.startAnimation(animationSet);

		}
	}
	
	class Leve3ClickListen implements OnClickListener{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AnimationSet animationSet=new AnimationSet(true);
			ScaleAnimation scaleAnimation=new ScaleAnimation(0.9f,0.9f,0.9f,0.9f,
					Animation.RELATIVE_TO_SELF,0.8f,
					Animation.RELATIVE_TO_SELF,0.8f);
			
	         scaleAnimation.setAnimationListener(new AnimationListener() {
					
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						}		
					
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						//动执行结束
						
						//MatchActivity.setlevel(1);
						Intent intent = new Intent();
						intent.setClass(SelectViewActivity.this, Guide3Activity.class);
						SelectViewActivity.this.startActivity(intent);
						SelectViewActivity.this.finish();
					}
				});
				
	        AssetsLoad.playSound(getApplicationContext(), AssetsLoad.dropSoundId);
			animationSet.addAnimation(scaleAnimation);
			animationSet.setFillBefore(true);
			animationSet.setDuration(100);
			imb3.startAnimation(animationSet);

		}
	}
}
