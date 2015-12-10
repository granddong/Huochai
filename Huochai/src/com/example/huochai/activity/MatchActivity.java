package com.example.huochai.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.example.huochai.R;
import com.example.huochai.fixedvalue.SString;
import com.example.huochai.music.Music;
import com.example.huochai.record.Set;
import com.example.huochai.view.ExView;
import com.example.huochai.view.NumberSeekBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;

public class MatchActivity extends Activity {

	public static int width = 0;
	 Music music;
    static boolean destory;
	boolean isPause;
	private NumberSeekBar pb;
	private GestureDetector gestureScanner;
	Button resert,pause,home;
	ExView exView;
	private SharedPreferences mPref;
	int max=1000;
	int max1;
	int count=0;
	int stopCount=0;
	TextView hint;
	int SoundInt=0;
	 @SuppressLint("HandlerLeak")
	    Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	                case 0:
	            	startActivity(new Intent(MatchActivity.this,EndActivity.class));
	            	handler.removeMessages(msg.what);
	                finish();
	                break;
	                case 1:
	                	if(pb.getProgress()<pb.getMax()&&destory==false){
	                    pb.setProgress(pb.getProgress()+1);
	                	System.out.print(destory);
	                    System.out.println("66666");
	                	}
	                //    mpref.edit().putInt("time", pb.getProgress()).commit();
	                    break;
	                case 2:
	                	System.out.print(destory);
	                	System.out.println(pb.getProgress());
	                	//pb.setProgress(pb.getProgress());
	                	break;
	            }
	        }
	    };

	
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		WindowManager manager = getWindowManager();
		width = manager.getDefaultDisplay().getWidth();
		setContentView(R.layout.activity_main);
		
		isPause = false;
		destory = false;

		
		
		pb=(NumberSeekBar)findViewById(R.id.bar0);
		mPref=getSharedPreferences("settime", MODE_PRIVATE);
		String num=mPref.getString("settime", "");
		if(TextUtils.isEmpty(num))
		{
			pb.setMax(max);
		}
		else
		{
		max1=Integer.parseInt(num);
		pb.setMax(max1);
		}
		init();
	    start();
		
		exView = (ExView)this.findViewById(R.id.exView);
		this.hint = (TextView)this.findViewById(R.id.textView);
		hint.setText(SString.getString(exView.getFigure().getIndex()));
		exView.setHint(hint);
		
		resert=(Button) findViewById(R.id.reset);
		resert.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AnimationSet animationSet=new AnimationSet(true);
				ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f,0.8f,0.8f,0.8f,
						Animation.RELATIVE_TO_SELF,0.6f,
						Animation.RELATIVE_TO_SELF,0.6f);
				
		         scaleAnimation.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
							}		
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							exView.reset();
						}
					});
					
		        //AssetsLoad.playSound(getApplicationContext(), AssetsLoad.dropSoundId);
				animationSet.addAnimation(scaleAnimation);
				animationSet.setFillBefore(true);
				animationSet.setDuration(100);
				resert.startAnimation(animationSet);
				
			}
		});
		
		
		pause=(Button) findViewById(R.id.stop);
		pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				 AnimationSet animationSet=new AnimationSet(true);
					ScaleAnimation scaleAnimation=new ScaleAnimation(0.9f,0.9f,0.9f,0.9f,
							Animation.RELATIVE_TO_SELF,0.6f,
							Animation.RELATIVE_TO_SELF,0.6f);
					
					animationSet.addAnimation(scaleAnimation);
					animationSet.setFillBefore(true);
					animationSet.setDuration(100);
					pause.startAnimation(animationSet);
					if(stopCount%2==0)
					{
						pause.setBackgroundResource(R.drawable.play);
						destory=true;
						hint.setVisibility(View.INVISIBLE);
						exView.setVisibility(View.INVISIBLE);
					}
					else{
						pause.setBackgroundResource(R.drawable.pause);
						destory=false;
						hint.setVisibility(View.VISIBLE);
						exView.setVisibility(View.VISIBLE);
					}
					stopCount++;
			}
		});
		
		
		home=(Button) findViewById(R.id.home);
		// 设置音乐
				music = new Music(this);
				if (Set.isMusic()) {
					Music.play();
				    home.setBackgroundResource(R.drawable.sopen);
				    home.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							AnimationSet animationSet=new AnimationSet(true);
							ScaleAnimation scaleAnimation=new ScaleAnimation(0.9f,0.9f,0.9f,0.9f,
									Animation.RELATIVE_TO_SELF,0.6f,
									Animation.RELATIVE_TO_SELF,0.6f);
							
							animationSet.addAnimation(scaleAnimation);
							animationSet.setFillBefore(true);
							animationSet.setDuration(100);
							home.startAnimation(animationSet);
							
							Set.setnewMusic();
							
							
							//声音的开启与关闭
							if(SoundInt%2==0)
							{
								home.setBackgroundResource(R.drawable.sclose);
									 //关闭音乐  
								Music.pause();
			                    Set.disMusic();
							}
							
							else
							{
								home.setBackgroundResource(R.drawable.sopen);
				                    //开启音乐
								Music.play();
					            Set.setnewMusic();
							}
							SoundInt++;
						}
					});
				}
				else
				{
					//Music.pause();
					 home.setBackgroundResource(R.drawable.sclose);home.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								AnimationSet animationSet=new AnimationSet(true);
								ScaleAnimation scaleAnimation=new ScaleAnimation(0.9f,0.9f,0.9f,0.9f,
										Animation.RELATIVE_TO_SELF,0.6f,
										Animation.RELATIVE_TO_SELF,0.6f);
								
								animationSet.addAnimation(scaleAnimation);
								animationSet.setFillBefore(true);
								animationSet.setDuration(100);
								home.startAnimation(animationSet);
								Set.isMusic();
								Set.setnewMusic();
								Music.play();
								
								//声音的开启与关闭
								if(SoundInt%2==0)
								{
									home.setBackgroundResource(R.drawable.sopen);
				                    //开启音乐
								Music.play();
					            Set.setnewMusic();
								}
								
								else
								{
									home.setBackgroundResource(R.drawable.sclose);
									 //关闭音乐  
								Music.pause();
			                    Set.disMusic();
									
								}
								SoundInt++;
							}
						});
				}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			destory=true;
			Music.pause();
			new AlertDialog.Builder(this)
			.setTitle("退出游戏")
			.setMessage("当前退出，游戏以失败结束...")
			// .setIcon(R.drawable.quit)
			.setPositiveButton("放弃",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							//System.out.println(handler.sendEmptyMessage(2));
							startActivity(new Intent(MatchActivity.this,EndActivity.class));
							MatchActivity.this.finish();
						}
					})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// 取消按钮事件
					destory=false;
					Music.play();
				}
			}).show();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void start() {
		   pb.setTextSize(30);// 设置字体大小
	        pb.setTextColor(Color.WHITE);// 颜色
	        pb.setMyPadding(10, 10, 10, 10);// 设置padding 调用setpadding会无效
	        pb.setImagePadding(0, 1);// 可以不设置
	        pb.setTextPadding(0, 0);// 可以不设置
	}

	private void init() {
		 TimerTask tt = new TimerTask() {
	            @Override
				public void run() {
	            //if(destory==false)
	           // {
	               if(pb.getProgress()==max&&destory==false)
	            	{
	            		handler.sendEmptyMessage(0);
	            		max=max+2;
	            	}
	               
	            	else if(pb.getProgress()<max &&destory==false)
	            	{
	                handler.sendEmptyMessage(1);
	            	}
	           
	               else if(destory==true)
	                {
	            	handler.sendEmptyMessage(2);
	                }
	              
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(tt, 1000, 1000);
    }
	public void next(View v) {
		//exView.
		exView.next();
	}

	public void reset(View v) {
		exView.reset();
	}

	public void answer(View v) {
		exView.showAnswer();
	}
	
	
	
	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 关闭音乐
		if (Set.isMusic() && music.isPlaying() && destory) {
			Music.stop();
		}

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.i("tag", "onPause");
		super.onPause();

		// 暂停音乐
		if (Set.isMusic() && (!destory) && music.isPlaying()) {
			Music.pause();
		}

		// 转到暂停界面
		if ((!isPause) && (!destory)) {
			Intent intent = new Intent();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("tag", "onResume");
		super.onResume();
		isPause = false;
		// 重启音乐
		if (Set.isMusic() && (!music.isPlaying())) {
			Music.play();
		}
		}
}