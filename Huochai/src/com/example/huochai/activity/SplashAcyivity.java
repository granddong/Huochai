package com.example.huochai.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.huochai.R;
import com.example.huochai.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashAcyivity extends Activity {

	protected static final int CODE_UPDATE_DIALOG = 0;

	protected static final int CODE_URL_ERROR = 1;

	protected static final int CODE_NET_ERROR = 2;

	protected static final int CODE_JSON_ERROR = 3;
	
	protected static final int CODE_ENTREHOME = 4;//������ҳ��

	private TextView tv_version; 
	private TextView tv_progress;//���ؽ���չʾ
	
	private String mversionName;//�汾��
	private int mversionCode;//�汾��
	private String mDesc;//�汾����
	private String mDownload;//���ص�ַ
	
	//�����߳��в��ܸ��½���UI�����ǿ���ͨ��handler��ʵ��
	private Handler mHandler=new Handler(){
	public void handleMessage(android.os.Message msg)
	{
		switch(msg.what)
		{
		case CODE_UPDATE_DIALOG:
			showUpdateDialog();
			break;
		case CODE_URL_ERROR:
			Toast.makeText(SplashAcyivity.this, "url����", Toast.LENGTH_SHORT).show();
			enterHome(); 
			break;
		case CODE_NET_ERROR:
			Toast.makeText(SplashAcyivity.this, "�������", Toast.LENGTH_SHORT).show();
			enterHome();
			break;
		case CODE_JSON_ERROR:
			Toast.makeText(SplashAcyivity.this, "���ݽ�������", Toast.LENGTH_SHORT).show();
			enterHome();
			break;
		case CODE_ENTREHOME:
			enterHome();
			break;
		}
	};   
	};

	private SharedPreferences mPref;

	private RelativeLayout rlRoot;//������
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_version=(TextView)findViewById(R.id.tv_version);
        tv_version.setText("�汾�ţ�"+getVersionname());
        tv_progress=(TextView) findViewById(R.id.tv_progress);
        
        
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        
        boolean autoUpdate = mPref.getBoolean("auto_update", true);
        if(autoUpdate)
        {
        checkVersion();
        }else{
        	mHandler.sendEmptyMessageDelayed(CODE_ENTREHOME,2000);//2�������ʱ2��
        }
        
        //�����������һ������Ķ���Ч��
        AlphaAnimation aim=new AlphaAnimation(0.3f, 1f);
        aim.setDuration(2000);
        rlRoot.startAnimation(aim);
    }
    private String getVersionname()
    {
    	PackageManager packageManager = getPackageManager();
    	try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
			int versionCode = packageInfo.versionCode;
			String versionName = packageInfo.versionName;
			System.out.println("versionName=" + versionName + ";versionCode="
					+ versionCode);
			return versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "";
    }
    
    /**
     * ��ȡ����app�İ汾��
     */
    private int  getVersionCode()
    {
    	PackageManager packageManager = getPackageManager();
    	try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    	
    	return -1;
    }
    /**
     * �ӷ�������ȡ�汾��Ϣ����У��
     */
    private void checkVersion()
    {
    	final long startTime = System.currentTimeMillis();
    	new Thread()
    	{
			@Override
    		//�������߳��첽����
    		public void run() {
				HttpURLConnection coon=null;
				Message msg=Message.obtain();
    			try {
    	    		//������ַ��localhost��������ģ�������ر�����ַʱ��������ip10.0.2.2���滻
    	    		//���̳߳���5�������
    				URL url=new URL("http://10.0.2.2:8080/update.json");
    				coon=(HttpURLConnection) url.openConnection();
    			    coon.setRequestMethod("GET");//�������󷽷�
    			    coon.setConnectTimeout(5000);//�������ӳ�ʱ
    			    coon.setReadTimeout(5000);//��Ӧ��ʱ
    			    coon.connect();
    			    int responseCode=coon.getResponseCode(); 
    			    if(responseCode==200)
    			    {
    			    	InputStream inputStream=coon.getInputStream();
    			        String result = StreamUtils.readFormStream(inputStream);
    			          
    			        System.out.println("���緵��"+result);
    			        
    			        //����json
    			        JSONObject jo=new JSONObject(result);
    			        mversionName = jo.getString("versionName");
    			        mversionCode = jo.getInt("versionCode");
    			        mDesc = jo.getString("description");
    			        mDownload = jo.getString("downloadUrl");
    			        if(mversionCode>getVersionCode())//�ж��Ƿ��и���
    			        {
    			        	//�жϷ�������versioncode���ڱ���app��versioncode
    			        	//˵���и���,���������Ի���
    			        	msg.what=CODE_UPDATE_DIALOG;
    			        	//showUpdateDialog();
    			        }
    			        else
    			        {//û�а汾����
    			        	msg.what=CODE_ENTREHOME;
    			        }
    			    }
    			} catch (MalformedURLException e) {
    				//url����
    				msg.what=CODE_URL_ERROR;
    				e.printStackTrace();
    			} catch (IOException e) {
    				//�������
    				msg.what=CODE_NET_ERROR;
    				e.printStackTrace();
    			} catch (JSONException e) {
					//json����ʧ��
    				msg.what=CODE_JSON_ERROR;
					e.printStackTrace();
				}finally{
					long endTime = System.currentTimeMillis();
					long timeUsed=endTime-startTime;//�������绨�ѵ�ʱ��
					if(timeUsed<2000)
					{//ǿ������һ��ʱ�䣬ʹ����ʱ�䱣������
						try {
							Thread.sleep(2000-timeUsed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					 mHandler.sendMessage(msg);
					if(coon!=null)
					{
						coon.disconnect();//�ر���������
					}
				}
    		}
    	}.start();
    	
    }
    
   
    /**
     * �����Ի���
     */
	protected void showUpdateDialog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("���°汾"+mversionName);
		builder.setMessage(mDesc);
	//	builder.setCancelable(false);//�����û�ȡ���Ի������ʾ
		builder.setPositiveButton("��������", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				download();
			}
		});
		builder.setNegativeButton("�Ժ���˵", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				enterHome();
			}
		});
		
		//����ȡ�����������û�������ؼ�ʱ����
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				enterHome();
			}
		});
		
		builder.show();
	}
	
	//����apk
	 protected void download() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

//			AssetManager mgr=getAssets();//�õ�AssetManager
//			Typeface tf=Typeface.createFromAsset(mgr, "fonts/1.ttf");//����·���õ�Typeface
//			tv_progress.setTypeface(tf);//��������
			
			tv_progress.setVisibility(View.VISIBLE);// ��ʾ����

			String target = Environment.getExternalStorageDirectory()
					+ "/update.apk";
			// XUtils
			HttpUtils utils = new HttpUtils();
			utils.download(mDownload, target, new RequestCallBack<File>() {

				// �����ļ��Ľ���
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					System.out.println("���ؽ���:" + current + "/" + total);
					tv_progress.setText("���ؽ���:" + current * 100 / total + "%");
				}

				// ���سɹ�
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					System.out.println("���سɹ�");
					// ��ת��ϵͳ����ҳ��
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setDataAndType(Uri.fromFile(arg0.result),
							"application/vnd.android.package-archive");
					// startActivity(intent);
					startActivityForResult(intent, 0);// ����û�ȡ����װ�Ļ�,
														// �᷵�ؽ��,�ص�����onActivityResult
				}

				// ����ʧ��
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(SplashAcyivity.this, "����ʧ��!",
							Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			Toast.makeText(SplashAcyivity.this, "û���ҵ�sdcard!",
					Toast.LENGTH_SHORT).show();
		}
	}
	 
	 //��װȡ�����û��ص��˷���
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}
	//������ҳ��
    private void enterHome()
    {
    	Intent intent =new Intent(this,HomeActivity.class);
    	startActivity(intent);
    	finish();
    }
    
}
