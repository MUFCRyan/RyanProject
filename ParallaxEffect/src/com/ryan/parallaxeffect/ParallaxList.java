package com.ryan.parallaxeffect;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

public class ParallaxList extends ListView {

	private ImageView headView;
	// 图片高度
	private int mImageInitHeight;
	// 图片所在控件高度
	private int mHeadViewHeight;

	public ParallaxList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ParallaxList(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ParallaxList(Context context) {
		super(context);
	}

	public void setImageView(final ImageView view) {
		headView = view;
		// 获取视图数观察者并监听
		headView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						mImageInitHeight = headView.getDrawable()
								.getIntrinsicHeight();
						mHeadViewHeight = headView.getMeasuredHeight();
						// 移除监听
						headView.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});
	}

	// 重写overScrollBy方法并通过其中的参数deltaY和isTouchEvent进行下一步处理
	@SuppressLint("NewApi")
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		// 判断是否为滑动事件及ListView下部越界
		if (isTouchEvent && deltaY < 0) {
			// 获取图片控件拉伸时的高度
			int newY = headView.getLayoutParams().height - deltaY / 3;
			// 判断newY大小防止超出图片原始大小
			if (newY <= mImageInitHeight) {
				// 重新对高度赋值并将结果设定回去
				headView.getLayoutParams().height = newY;
				headView.requestLayout();
			}
			// 动态改变控件高度
		}
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY,
				isTouchEvent);
	}

	// 监听滑动事件并进行下一步处理
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			// 使图片回到原始位置并显示相应的动画效果
			ValueAnimator animator = ValueAnimator.ofInt(
					headView.getMeasuredHeight(), mHeadViewHeight);
			animator.setDuration(400);
			//实时更新动画
			animator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					headView.getLayoutParams().height = (Integer) animation.getAnimatedValue();
					headView.requestLayout();
				}
			});
			//设置插值器并开始动画
			animator.setInterpolator(new OvershootInterpolator(5.0f));
			animator.start();
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

}
