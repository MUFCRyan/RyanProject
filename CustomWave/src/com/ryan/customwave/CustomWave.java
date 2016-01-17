package com.ryan.customwave;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;

public class CustomWave extends View {

	private int[] mColors = new int[] { Color.RED, Color.BLUE, Color.GREEN,
			Color.YELLOW, Color.GRAY, Color.MAGENTA };
	private ArrayList<WaveBean> mWaveList = new ArrayList<WaveBean>();
	private String text = "";

	public CustomWave(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomWave(Context context, AttributeSet attrs) {
		super(context, attrs);
	} 

	public CustomWave(Context context) {
		super(context);
	}

	/*public void addText(String name) {
		text = name;
	}*/

	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 半径变大/线宽变宽/颜色变淡
			// 刷新数据
			flushData();
			// 刷新界面
			invalidate();
			// mWaveList不为空时,继续发送消息
			if (!mWaveList.isEmpty()) {
				mHandler.sendEmptyMessageDelayed(0, 60);
			}
		};
	};
	public MainActivity activity;

	// 设置触摸事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			addPoint((int) event.getX(), (int) event.getY());
			break;

		default:
			break;
		}
		return true;
	}

	// 刷新圆环所有数据
	protected void flushData() {
		// 遍历要显示圆环的集合
		ArrayList<WaveBean> deleteList = new ArrayList<WaveBean>();
		for (WaveBean wave : mWaveList) {
			// 半径变大/线宽变宽/颜色变淡
			if (text == "") {
				wave.radius += 9;
				wave.paint.setStrokeWidth(wave.radius / 3);
			} else {
				wave.textSize += 15;
				wave.paint.setTextSize(wave.textSize);
			}
			int alpha = wave.paint.getAlpha();
			// 将消失的圆环添加到特定集合中
			if (alpha <= 0) {
				deleteList.add(wave);
				continue;
			}
			alpha -= 7;
			if (alpha < 0) {
				alpha = 0;
			}
			wave.paint.setAlpha(alpha);
		}
		// 删除所有已消失圆环
		mWaveList.removeAll(deleteList);
	}

	// 绘制圆环
	@Override
	protected void onDraw(Canvas canvas) {
		// 遍历集合绘制所有圆环
		for (WaveBean wave : mWaveList) {
			if (text == "") {
				canvas.drawCircle(wave.cx, wave.cy, wave.radius, wave.paint);
			}
			canvas.drawText(text, wave.cx, wave.cy, wave.paint);
		}
	}

	// 添加一个点
	private void addPoint(int x, int y) {
		if (mWaveList.isEmpty()) {
			// 当前点第一次绘制
			addWave(x, y);
			// 发送消息
			mHandler.sendEmptyMessage(0);
		} else {
			// 相邻两圆环有一定距离才添加
			WaveBean lastWave = mWaveList.get(mWaveList.size() - 1);
			if (text=="") {
				if (Math.abs(x - lastWave.cx) > 15
						|| Math.abs(y - lastWave.cy) > 15) {
					addWave(x, y);
				}
			}else {
				if (Math.abs(x - lastWave.cx) > 40
						|| Math.abs(y - lastWave.cy) > 40) {
					addWave(x, y);
				}
			}
		}
	}

	private void addWave(int x, int y) {
		WaveBean wave = new WaveBean();
		wave.cx = x;
		wave.cy = y;

		Paint paint = new Paint();
		// 随机设置颜色
		int index = (int) (Math.random() * mColors.length);
		paint.setColor(mColors[index]);
		paint.setAlpha(255);
		if (text == "") {
			wave.radius = 0;
			paint.setStrokeWidth(wave.radius / 3);
			paint.setStyle(Style.STROKE);
			paint.setAntiAlias(true);
		} else {
			wave.textSize = 10;
			//设置画笔文字大小
			paint.setTextSize(wave.textSize);
			//设置画笔文笔绘制时起始点相对于其自身的位置
			paint.setTextAlign(Align.CENTER);

		}
		wave.paint = paint;
		/*
		 * if (mListener != null) { mListener.onChange(wave.text); }
		 */
		// 添加到集合中
		mWaveList.add(wave);
	}


	public void cancelText() {
		this.text = "";
	}

	public void addText(String text2) {
		text = text2;
		//System.out.println("addText:"+text);
	}

}
