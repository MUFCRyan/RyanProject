package com.ryan.parallaxeffect;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private ImageView mIvImage;
	private ParallaxList mPlList;
	private ImageView mPlImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlList = (ParallaxList) findViewById(R.id.pl_list);
		View headView = View.inflate(this, R.layout.layout_header, null);
		mPlImage = (ImageView) headView.findViewById(R.id.imageView);
		mPlList.addHeaderView(headView);
		mPlList.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_list_item_1, indexArr));
		mPlList.setImageView(mPlImage);

	}
}
