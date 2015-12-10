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
	
	protected static final int CODE_ENTREHOME = 4;//进入主页面

	private TextView tv_version; 
	private TextView tv_progress;//下载进度展示
	
	private String mversionName;//版本名
	private int mversionCode;//版本号
	private String mDesc;//版本描述
	private String mDownload;//下载地址
	
	//在子线程中不能更新界面UI，这是可以通过handler来实现
	private Handler mHandler=new Handler(){
	public void handleMessage(android.os.Message msg)
	{
		switch(msg.what)
		{
		case CODE_UPDATE_DIALOG:
			showUpdateDialog();
			break;
		case CODE_URL_ERROR:
			Toast.makeText(SplashAcyivity.this, "url错误", Toast.LENGTH_SHORT).show();
			enterHome(); 
			break;
		case CODE_NET_ERROR:
			Toast.makeText(SplashAcyivity.this, "网络错误", Toast.LENGTH_SHORT).show();
			enterHome();
			break;
		case CODE_JSON_ERROR:
			Toast.makeText(SplashAcyivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
			enterHome();
			break;
		case CODE_ENTREHOME:
			enterHome();
			break;
		}
	};   
	};

	private SharedPreferences mPref;

	private RelativeLayout rlRoot;//根布局
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_version=(TextView)findViewById(R.id.tv_version);
        tv_version.setText("版本号："+getVersionname());
        tv_progress=(TextView) findViewById(R.id.tv_progress);
        
        
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        
        boolean autoUpdate = mPref.getBoolean("auto_update", true);
        if(autoUpdate)
        {
        checkVersion();
        }else{
        	mHandler.sendEmptyMessageDelayed(CODE_ENTREHOME,2000);//2秒后发送延时2秒
        }
        
        //给跟布局添加一个渐变的动画效果
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
     * 获取本地app的版本号
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
     * 从服务器获取版本信息进行校验
     */
    private void checkVersion()
    {
    	final long startTime = System.currentTimeMillis();
    	new Thread()
    	{
			@Override
    		//启动子线程异步加载
    		public void run() {
				HttpURLConnection coon=null;
				Message msg=Message.obtain();
    			try {
    	    		//本机地址用localhost，但是用模拟器加载本机地址时，可以用ip10.0.2.2来替换
    	    		//主线程超过5秒回阻塞
    				URL url=new URL("http://10.0.2.2:8080/update.json");
    				coon=(HttpURLConnection) url.openConnection();
    			    coon.setRequestMethod("GET");//设置请求方法
    			    coon.setConnectTimeout(5000);//设置连接超时
    			    coon.setReadTimeout(5000);//响应超时
    			    coon.connect();
    			    int responseCode=coon.getResponseCode(); 
    			    if(responseCode==200)
    			    {
    			    	InputStream inputStream=coon.getInputStream();
    			        String result = StreamUtils.readFormStream(inputStream);
    			          
    			        System.out.println("网络返回"+result);
    			        
    			        //解析json
    			        JSONObject jo=new JSONObject(result);
    			        mversionName = jo.getString("versionName");
    			        mversionCode = jo.getInt("versionCode");
    			        mDesc = jo.getString("description");
    			        mDownload = jo.getString("downloadUrl");
    			        if(mversionCode>getVersionCode())//判断是否有更新
    			        {
    			        	//判断服务器的versioncode大于本地app的versioncode
    			        	//说明有更新,弹出升级对话框
    			        	msg.what=CODE_UPDATE_DIALOG;
    			        	//showUpdateDialog();
    			        }
    			        else
    			        {//没有版本更新
    			        	msg.what=CODE_ENTREHOME;
    			        }
    			    }
    			} catch (MalformedURLException e) {
    				//url错误
    				msg.what=CODE_URL_ERROR;
    				e.printStackTrace();
    			} catch (IOException e) {
    				//网络错误
    				msg.what=CODE_NET_ERROR;
    				e.printStackTrace();
    			} catch (JSONException e) {
					//json解析失败
    				msg.what=CODE_JSON_ERROR;
					e.printStackTrace();
				}finally{
					long endTime = System.currentTimeMillis();
					long timeUsed=endTime-startTime;//访问网络花费的时间
					if(timeUsed<2000)
					{//强制休眠一段时间，使闪屏时间保持两秒
						try {
							Thread.sleep(2000-timeUsed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					 mHandler.sendMessage(msg);
					if(coon!=null)
					{
						coon.disconnect();//关闭网络连接
					}
				}
    		}
    	}.start();
    	
    }
    
   
    /**
     * 升级对话框
     */
	protected void showUpdateDialog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("最新版本"+mversionName);
		builder.setMessage(mDesc);
	//	builder.setCancelable(false);//不让用户取消对话框的显示
		builder.setPositiveButton("立即更新", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				download();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				enterHome();
			}
		});
		
		//设置取消监听，当用户点击返回键时触发
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				enterHome();
			}
		});
		
		builder.show();
	}
	
	//下载apk
	 protected void download() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

//			AssetManager mgr=getAssets();//得到AssetManager
//			Typeface tf=Typeface.createFromAsset(mgr, "fonts/1.ttf");//根据路径得到Typeface
//			tv_progress.setTypeface(tf);//设置字体
			
			tv_progress.setVisibility(View.VISIBLE);// 显示进度

			String target = Environment.getExternalStorageDirectory()
					+ "/update.apk";
			// XUtils
			HttpUtils utils = new HttpUtils();
			utils.download(mDownload, target, new RequestCallBack<File>() {

				// 下载文件的进度
				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					System.out.println("下载进度:" + current + "/" + total);
					tv_progress.setText("下载进度:" + current * 100 / total + "%");
				}

				// 下载成功
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					System.out.println("下载成功");
					// 跳转到系统下载页面
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setDataAndType(Uri.fromFile(arg0.result),
							"application/vnd.android.package-archive");
					// startActivity(intent);
					startActivityForResult(intent, 0);// 如果用户取消安装的话,
														// 会返回结果,回调方法onActivityResult
				}

				// 下载失败
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(SplashAcyivity.this, "下载失败!",
							Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			Toast.makeText(SplashAcyivity.this, "没有找到sdcard!",
					Toast.LENGTH_SHORT).show();
		}
	}
	 
	 //安装取消，用户回调此方法
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}
	//进入主页面
    private void enterHome()
    {
    	Intent intent =new Intent(this,HomeActivity.class);
    	startActivity(intent);
    	finish();
    }
    
}
