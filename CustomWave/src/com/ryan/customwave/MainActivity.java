package com.ryan.customwave;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText mEtContent;
	private Button mBtnContent;
	private CustomWave mCvWave;
	private Button mBtnCancel;
	private String text = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	public void initView() {
		mCvWave = (CustomWave) findViewById(R.id.cv_wave);
		mEtContent = (EditText) findViewById(R.id.et_wave_content);
		mBtnContent = (Button) findViewById(R.id.btn_wave_content);
		mBtnCancel = (Button) findViewById(R.id.btn_wave_cancel);
		mBtnContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				text = mEtContent.getText().toString().trim();
				if (text == null || text == "") {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(),
									"输入框内容不能为空!请点取消并输入文本后再次加入您想要显示的任何文字", 0).show();
						}
					});
					return;
				} else {
					MainActivity.this.setText(text);
				}
			}
		});
		mBtnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCvWave.cancelText();
				mEtContent.setText("");
			}
		});
	}

	public String setText(String text) {
		mCvWave.addText(text);
		return text;
	}

}
