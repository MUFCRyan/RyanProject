package com.miss.qianghongbao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.miss.qianghongbao.R;
import com.miss.qianghongbao.utils.ServiceStateUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * 抢红包主界面
 * @author Wangng
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button mStartService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
	}

	private void initData() {
		boolean serviceRunning = ServiceStateUtil.isServiceRunning(this, QiangHongBaoService.class);
		if(serviceRunning){
			mStartService.setText("关闭服务");
		}else{
			mStartService.setText("开启服务");
		}
	}

	private void initView() {
		mStartService = (Button) findViewById(R.id.start_button);
		mStartService.setOnClickListener(this);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		initData();
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onClick(View v) {
		try {
			Intent intent = new Intent(
					android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
			startActivity(intent);
			//Toast.makeText(MainActivity.this, "找到抢红包，然后开启服务即可", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
