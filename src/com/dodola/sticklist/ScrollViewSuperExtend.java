package com.dodola.sticklist;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ScrollViewSuperExtend extends ScrollView {
	private OnInterceptTouchListener mOnInterceptTouchListener;
	private OnScrollListener mOnScrollListener;
	private float xDistance;
	private float xLast;
	private float yDistance;
	private float yLast;

	public ScrollViewSuperExtend(Context paramContext) {
		super(paramContext);
	}

	public ScrollViewSuperExtend(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public ScrollViewSuperExtend(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	private boolean needIntercept() {
		if (this.mOnInterceptTouchListener != null) {
			return this.mOnInterceptTouchListener.needIntercept();
		}
		return true;
	}

	protected int computeScrollDeltaToGetChildRectOnScreen(Rect paramRect) {
		return 0;
	}

	public boolean isScrollAtBottom() {
		int i = getChildAt(0).getMeasuredHeight();
		int j = 10 + (getHeight() + getScrollY());
		boolean bool = false;
		if (i <= j) {
			bool = true;
		}
		return bool;
	}

	public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
		boolean superTouch = super.onInterceptTouchEvent(paramMotionEvent);
		switch (paramMotionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN :
				this.yDistance = 0.0F;
				this.xDistance = 0.0F;
				this.xLast = paramMotionEvent.getX();
				this.yLast = paramMotionEvent.getY();
				break;
			case MotionEvent.ACTION_MOVE :
				boolean bool1;
				float f1 = paramMotionEvent.getX();
				float f2 = paramMotionEvent.getY();
				this.xDistance += f1 - this.xLast;
				this.yDistance += f2 - this.yLast;
				this.xLast = f1;
				this.yLast = f2;
				Log.d("====", "onInterceptTouchEvent,xDistance:" + xDistance + ",yDistance:" + yDistance);
				bool1 = Math.abs(this.xDistance) < Math.abs(this.yDistance);// 如果x距离小于y距离，说明上下操作
				if (bool1) {// 上下操作
					if (isScrollAtBottom()) {// 如果滑动到底部
						Log.d("ScrollView", "====scroll at bottom =====,this.yDistance:" + this.yDistance);
						if ((this.yDistance <= 0.0F)) {// 手指向上滑动
							return false;
						} else {// 手指向下滑动
							boolean needIntercept = needIntercept();

							Log.d("ScrollView", "needIntercept:" + needIntercept);
							if (needIntercept) {// 如果list置顶
								return true;// 拦截事件，ScrollView滑动
							} else {// list没有置顶
								return false;// 不拦截时间，listView滑动
							}
						}
					} else {
						return true;
					}
				}

				return false;

			case MotionEvent.ACTION_CANCEL :
			case MotionEvent.ACTION_UP :

				break;
		}
		return false;
	}
	protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		if ((this.mOnScrollListener != null)) {
			this.mOnScrollListener.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		}
	}

	public void setOnInterceptTouchListener(OnInterceptTouchListener paramOnInterceptTouchListener) {
		this.mOnInterceptTouchListener = paramOnInterceptTouchListener;
	}

	public void setOnScrollListener(OnScrollListener paramOnScrollListener) {
		this.mOnScrollListener = paramOnScrollListener;
	}

	public static abstract interface OnInterceptTouchListener {
		public abstract boolean needIntercept();
	}

	public static abstract interface OnScrollListener {
		public abstract void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
	}
}
