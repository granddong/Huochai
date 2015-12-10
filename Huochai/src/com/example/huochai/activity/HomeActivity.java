package com.example.huochai.activity;

import com.example.huochai.R;
import com.example.huochai.music.Music;
import com.example.huochai.record.Set;
import com.example.huochai.utils.AssetsLoad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;

public class HomeActivity extends Activity {

		

	 private  Button Exit,Setup,About,Start;
	

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		AssetsLoad.load(getApplicationContext());
		
		
		/**
		 * ��ʼ��Ϸ
		 */
		Start=(Button)findViewById(R.id.start);
		Start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimationSet animationSet=new AnimationSet(true);
				ScaleAnimation scaleAnimation=new ScaleAnimation(1,1.3f,1,1.3f,
						Animation.RELATIVE_TO_SELF,0.5f,
						Animation.RELATIVE_TO_SELF,0.5f);
				
		         scaleAnimation.setAnimationListener(new AnimationListener() {
						
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
								}		
							
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
								
							}
							
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								//��ִ�н���
								Intent intent = new Intent();
								intent.setClass(HomeActivity.this, SelectViewActivity.class);
								HomeActivity.this.startActivity(intent);				
								
							}
						});
						
			   AssetsLoad.playSound(getApplicationContext(), AssetsLoad.dropSoundId);
				animationSet.addAnimation(scaleAnimation);
				animationSet.setFillBefore(true);
				animationSet.setDuration(80);
				Start.startAnimation(animationSet);
				

			}		
		});
		
		
		
		/**
		 * ����
		 */
		Setup=(Button)findViewById(R.id.setup);
		Setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimationSet animationSet=new AnimationSet(true);
				ScaleAnimation scaleAnimation=new ScaleAnimation(1,1.3f,1,1.3f,
						Animation.RELATIVE_TO_SELF,0.5f,
						Animation.RELATIVE_TO_SELF,0.5f);
				
		         scaleAnimation.setAnimationListener(new AnimationListener() {
						
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
								}		
							
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
								
							}
							
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								//��ִ�н���
								Intent intent = new Intent();
								intent.setClass(HomeActivity.this, SetupActivity.class);
								HomeActivity.this.startActivity(intent);				
								
							}
						});
						
			   AssetsLoad.playSound(getApplicationContext(), AssetsLoad.dropSoundId);
				animationSet.addAnimation(scaleAnimation);
				animationSet.setFillBefore(true);
				animationSet.setDuration(80);
				Setup.startAnimation(animationSet);
				

			}		
		});
		
		
		/**
		 * ����
		 */
		About=(Button)findViewById(R.id.about);
		About.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimationSet animationSet=new AnimationSet(true);
				ScaleAnimation scaleAnimation=new ScaleAnimation(1,1.3f,1,1.3f,
						Animation.RELATIVE_TO_SELF,0.5f,
						Animation.RELATIVE_TO_SELF,0.5f);
				
		         scaleAnimation.setAnimationListener(new AnimationListener() {
						
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub
								}		
							
							public void onAnimationRepeat(Animation animation) {
								// TODO Auto-generated method stub
								
							}
							
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								//��ִ�н���
								Intent intent = new Intent();
								intent.setClass(HomeActivity.this, AboutActivity.class);
								HomeActivity.this.startActivity(intent);				
								
							}
						});
						
				AssetsLoad.playSound(getApplicationContext(), AssetsLoad.dropSoundId);
				animationSet.addAnimation(scaleAnimation);
				animationSet.setFillBefore(true);
				animationSet.setDuration(80);
				About.startAnimation(animationSet);


			}		
		});
		
		
		
		/**
		 * �˳�
		 */
		Exit=(Button)findViewById(R.id.exit);	
		Exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimationSet animationSet=new AnimationSet(true);
				ScaleAnimation scaleAnimation=new ScaleAnimation(1,1.3f,1,1.3f,
						Animation.RELATIVE_TO_SELF,0.5f,
						Animation.RELATIVE_TO_SELF,0.5f);
				
                   scaleAnimation.setAnimationListener(new AnimationListener() {
					
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						}		
					
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						//��ִ�н���
						System.exit(0);
						
					}
				});
				
                AssetsLoad.playSound(getApplicationContext(), AssetsLoad.dropSoundId);
				animationSet.addAnimation(scaleAnimation);
				animationSet.setFillBefore(true);
				animationSet.setDuration(80);
				Exit.startAnimation(animationSet);

			}
		});
	}
}
