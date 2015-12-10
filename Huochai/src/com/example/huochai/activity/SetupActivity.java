package com.example.huochai.activity;

import com.example.huochai.R;
import com.example.huochai.record.Set;
import com.example.huochai.view.SettingItemView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SetupActivity extends Activity {
	
	private SharedPreferences mpref;
	private SharedPreferences mpref1;
	private SharedPreferences mpref2;


	 int SoundInt=0;

	 private SettingItemView sivupdate;//��������
	 
	 private SettingItemView sivopen;//���ñ�������
	 
	 String count;
	 int time;
	 private EditText ticount;
	 private EditText titime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mpref = getSharedPreferences("config", MODE_PRIVATE);	
		mpref1 = getSharedPreferences("settime", MODE_PRIVATE);
		mpref2=getSharedPreferences("setcount", MODE_PRIVATE);
		initUpdateView();
		initOpenMusic();
		
		titime=(EditText) findViewById(R.id.ed_time);
		String time=mpref1.getString("settime", "");
		titime.setText(time);
		
		ticount=(EditText) findViewById(R.id.ed_count);
		String count =mpref2.getString("setcount", "");
		ticount.setText(count);
	}
	
	/**
	 * ��ʼ���Զ����¿���
	 */
	  private void initUpdateView()
	  {
		  sivupdate = (SettingItemView) findViewById(R.id.siv_update);
		    //sivupdate.setTitle("�Զ���������");
		    boolean autoUpdate = mpref.getBoolean("auto_update", true);
		    
		    if(autoUpdate)
		    {
		    	//sivupdate.setDesc("�Զ������ѿ���");
		    	sivupdate.setChecked(true);
		    }
		    else
		    {
		    	//sivupdate.setDesc("�Զ������ѹر�") ;
		    	sivupdate.setChecked(false);
		    }
		    
		    sivupdate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//�ж�checkbox��ѡ״̬
					if(sivupdate.isChecked())
					{
						//���ò���ѡ
						sivupdate.setChecked(false);
						//sivupdate.setDesc("�Զ������ѹر�");
						
						mpref.edit().putBoolean("auto_update", false).commit();
					}
					else
					{
						sivupdate.setChecked(true);
						//sivupdate.setDesc("�Զ������ѿ���");
						mpref.edit().putBoolean("auto_update", true).commit();
					}
				}
			});
	  }

	  
	
	  /**
	   * �������ֿ���
	   */

	  private void initOpenMusic()
	  {
		 sivopen=(SettingItemView) findViewById(R.id.siv_open); 
		 sivopen.setChecked(true);
		 Set.setnewMusic();			
            sivopen.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(sivopen.isChecked())
					{
						sivopen.setChecked(false);
						 Set.disMusic();
					}
					else
					{
						sivopen.setChecked(true);
						Set.setnewMusic();
					}
				}
			});
	  }
	  
	  
	  
	  @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if(keyCode==KeyEvent.KEYCODE_BACK)
			{
				new AlertDialog.Builder(this)
				.setTitle("��������")
				.setMessage("�Ƿ�ȷ������...")
				// .setIcon(R.drawable.quit)
				.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String count=ticount.getText().toString().trim();
								mpref2.edit().putString("setcount", count).commit();
								
								String time=titime.getText().toString().trim();
								mpref1.edit().putString("settime", time).commit();
								
								startActivity(new Intent(SetupActivity.this,HomeActivity.class));
								finish();
							}
						})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// ȡ����ť�¼�
						startActivity(new Intent(SetupActivity.this,HomeActivity.class));
						finish();
					}
				}).show();
			}
			return super.onKeyDown(keyCode, event);
		}
	  
}
