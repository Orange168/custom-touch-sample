package com.example.root.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by root on 5/21/15.
 */
public class CustomView extends View implements Runnable{

    private Context context ;

    private  Activity activity ;

    private static final String TAG = "CustomView" ;

    private int radius = 100  ;

    Paint mPaint ;
    private Point mPoint ;
	private int measuredWidth;
	private int measuredHeight;

	public CustomView(Context context){
        this(context,null) ;
    }

    public CustomView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        this.context = context ;
//        activity = (Activity)context ;
        mPoint = new Point() ;
//        getResources().getDisplayMetrics().heightPixels
//        ((Activity) context).getWindow().getWindowManager().getDefaultDisplay().get
//        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(mPoint);
//        Log.e(TAG , "screenWidth=" + mPoint.x + "; screenHeight=" + mPoint.y);
        init() ;
    }

    private void init(){
        mPaint = new Paint() ;
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE); //空心
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(Color.LTGRAY);
        mPaint.setColor(Color.argb(255, 255, 128, 103));
        mPaint.setStrokeWidth(10);
//
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                1, 0, 0, 0, 0,
//                0, 1, 0, 0, 0,
//                0, 0, 1, 0, 0,
//                0, 0, 0, 1, 0,
//         }
//        ) ;
//
//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getResources().getDisplayMetrics().heightPixels ;
        int width = getResources().getDisplayMetrics().widthPixels ;
        Log.e(TAG , "screenWidth=" +width + "; screenHeight=" + height);
        canvas.drawCircle(measuredWidth/2, measuredWidth/2,radius,mPaint);
    }


    public  synchronized void setRadius(int radius){
        this.radius = radius ;

        invalidate();
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		measuredWidth = 260;
		measuredHeight = 260;
		setMeasuredDimension(measuredWidth, measuredHeight);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e(TAG, "event.getX = " + event.getX());
		Log.e(TAG, "getX = " + getX());
		Log.e(TAG, "getY = " + getY());
		Log.e(TAG, "getWidth == " + getWidth());
		Log.e(TAG, "getHeight() = " + getHeight());
		Log.e(TAG, "getPaddingBottom = " + getPaddingBottom());
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onDragEvent(DragEvent event) {
		return super.onDragEvent(event);
	}

	@Override
    public void run() {
        while (true) {
            try {
                radius = radius < 200 ? (radius +10) : 0 ;
//                  only the original  thread that create landscape_mountain view hierarchy can touch
//                  its view EXECPTION
//                invalidate() ;
                postInvalidate();

                Thread.sleep(40);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
