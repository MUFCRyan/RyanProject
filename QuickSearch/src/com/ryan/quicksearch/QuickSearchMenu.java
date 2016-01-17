package com.ryan.quicksearch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class QuickSearchMenu extends View {

	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private Paint paint;
	private int cellHeight;
	private int pointY;
	private int position = -1;
	private int lastPosition = -1;

	public QuickSearchMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public QuickSearchMenu(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public QuickSearchMenu(Context context) {
		this(context, null);
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(12);
	}

	// 在onSizeChanged方法中可以获得测量后的宽高
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		cellHeight = this.getMeasuredHeight() / indexArr.length;
		// System.out.println("cellHeight="+cellHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 循环绘制字母
		for (int i = 0; i < indexArr.length; i++) {
			//若当前位置同触碰位置,就用黑色绘制,否则用白色绘制
			if (lastPosition==i) {
				paint.setColor(Color.BLACK);
			}else {
				paint.setColor(Color.WHITE);
			}
			canvas.drawText(indexArr[i], this.getMeasuredWidth() / 2,
					cellHeight / 2 + getTextHeight(indexArr[i]) + cellHeight
							* i, paint);
			invalidate();
		}
	}

	// 获取指定字符串的高度
	protected int getTextHeight(String text) {
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return bounds.height();
	}

	// 监听触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			// 获取手指点击或滑动位置并作相应处理
			pointY = (int) event.getY();
			//获取当前点击位置
			position = (int) (pointY / cellHeight + 0.5f);
			//若当前触碰位置与上一次不同就将此次位置记录
			if (lastPosition != position) {
				//判断当前位置是否越界
				if (position >= 0 && position <= indexArr.length - 1) {
					//若不越界就赋给上次位置
					lastPosition = position;
					//回调监听器的相应方法,将当前点击的字母传给调用者
					if (mListener != null) {
						mListener.onSelected(indexArr[lastPosition]);
					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			lastPosition = -1;
			break;

		default:
			break;
		}
		return true;
	}

	// 设置监听器和接口
	public interface OnIndexSelectedListener {
		void onSelected(String text);
	}

	private OnIndexSelectedListener mListener;

	public void setOnIndexSelectedListener(OnIndexSelectedListener listener) {
		mListener = listener;
	}
}
