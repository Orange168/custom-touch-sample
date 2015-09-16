package com.examples.customtouch.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.Scroller;

public class CustomOverScroller extends LinearLayout implements View.OnTouchListener{

	private static final String TAG = "Scroller";

	private OverScroller mScroller;

	public CustomOverScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
		mScroller = new OverScroller(context);
	}

	//调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	//调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy) {

		//设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
		invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}
	
	@Override
	public void computeScroll() {
	
		//先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
		
			//这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			
			//必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
		super.computeScroll();
	}

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.i(TAG,"getCurrX =" + mScroller.getCurrX() +"\t\tgetCurrY=" + mScroller.getCurrY()) ;
        Log.i(TAG,"getFinalX =" + mScroller.getFinalX() +"\t\tgetFinalY=" + mScroller.getFinalY()) ;

        return false;
    }
}