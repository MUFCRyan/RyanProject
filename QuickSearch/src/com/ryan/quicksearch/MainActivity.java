package com.ryan.quicksearch;

import java.util.ArrayList;
import java.util.Collections;

import com.ryan.quicksearch.QuickSearchMenu.OnIndexSelectedListener;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView mLvNames;
	private QuickSearchMenu mQsmMenu;
	private String currentLetter;
	private ArrayList<Friend> friends = new ArrayList<Friend>();
	private TextView mTvIndicate;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		mQsmMenu = (QuickSearchMenu) findViewById(R.id.qsm_search_menu);
		mLvNames = (ListView) findViewById(R.id.lv_names);
		mTvIndicate = (TextView) findViewById(R.id.tv_indicate_letter);
		mQsmMenu.setOnIndexSelectedListener(new OnIndexSelectedListener() {
			@Override
			public void onSelected(String text) {
				currentLetter = text;
				//循环判断当前选中的字母并跳转到相应界面
				for (int i = 0; i < friends.size(); i++) {
					if (TextUtils.equals(text, friends.get(i).getInitLetter())) {
						mLvNames.setSelection(i);
						break;
					}
				}
				mTvIndicate.setVisibility(View.VISIBLE);
				mTvIndicate.setText(text);
				mHandler.removeCallbacksAndMessages(null);
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mTvIndicate.setVisibility(View.GONE);
					}
				}, 1000);
			}
		});
		//初始化数据
		fillList();
		//排序集合元素
		Collections.sort(friends);
		mLvNames.setAdapter(new QuickSearchAdapter());
	}
	
	//创建适配器
	public class QuickSearchAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			System.out.println("getCount------------------");
			return friends.size();
		}

		@Override
		public Object getItem(int position) {
			System.out.println("getItem------------------");
			return friends.get(position);
		}

		@Override
		public long getItemId(int position) {
			System.out.println("getItemId------------------");
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			System.out.println("getView----before");
			SearchViewHoldr holder;
			if (convertView == null) {
				convertView = View.inflate(MainActivity.this, R.layout.adapter_friend, null);
				holder = new SearchViewHoldr();
				holder.mTvLetter = (TextView) convertView.findViewById(R.id.first_word);
				holder.mTvName = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			}else {
				holder = (SearchViewHoldr) convertView.getTag();
			}
			if (position>0) {
				if (friends.get(position).getInitLetter().equals(friends.get(position-1).getInitLetter())) {
					holder.mTvLetter.setVisibility(View.GONE);
				}else{
					holder.mTvLetter.setVisibility(View.VISIBLE);
				}
			}else {
				holder.mTvLetter.setVisibility(View.VISIBLE);
			}
			Friend friend = friends.get(position);
			holder.mTvName.setText(friend.getName());
			holder.mTvLetter.setText(friend.getInitLetter());
			System.out.println("getView----after");
			return convertView;
		}
	}

	//创建ViewHolder
	private static class SearchViewHoldr{
		TextView mTvLetter;
		TextView mTvName;
	}
	
	//虚拟数据
	private void fillList() {
		// 虚拟数据
		friends.add(new Friend("李伟"));
		friends.add(new Friend("张三"));
		friends.add(new Friend("阿三"));
		friends.add(new Friend("阿四"));
		friends.add(new Friend("段誉"));
		friends.add(new Friend("段正淳"));
		friends.add(new Friend("张三丰"));
		friends.add(new Friend("陈坤"));
		friends.add(new Friend("林俊杰1"));
		friends.add(new Friend("陈坤2"));
		friends.add(new Friend("王二a"));
		friends.add(new Friend("林俊杰a"));
		friends.add(new Friend("张四"));
		friends.add(new Friend("林俊杰"));
		friends.add(new Friend("王二"));
		friends.add(new Friend("王二b"));
		friends.add(new Friend("赵四"));
		friends.add(new Friend("杨坤"));
		friends.add(new Friend("赵子龙"));
		friends.add(new Friend("杨坤1"));
		friends.add(new Friend("李伟1"));
		friends.add(new Friend("宋江"));
		friends.add(new Friend("宋江1"));
		friends.add(new Friend("李伟3"));
	}

}
