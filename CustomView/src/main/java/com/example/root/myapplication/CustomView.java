package com.example.root.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.root.myapplication.utils.MeasureUtils;


/**
 * Created by root on 5/21/15.
 */
public class CustomView extends View implements Runnable{

    private Context context ;

    private  Activity activity ;

    private static final String TAG = "CustomView" ;

    private int radius ;

    Paint mPaint ;
    private Point mPoint ;
    public CustomView(Context context){
        this(context,null) ;
    }

    public CustomView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        this.context = context ;
        activity = (Activity)context ;
        mPoint = new Point() ;
        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(mPoint);
        Log.e(TAG , "screenWidth=" + mPoint.x + "; screenHeight=" + mPoint.y);
        init() ;
    }

    private void init(){
        mPaint = new Paint() ;
        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.STROKE); //空心
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(Color.LTGRAY);
        mPaint.setColor(Color.argb(255, 255, 128, 103));
        mPaint.setStrokeWidth(10);

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
         }
        ) ;

        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(MeasureUtils.getScreenSize(activity).x/2,
                MeasureUtils.getScreenSize(activity).y/2,radius,mPaint);
    }


    public  synchronized void setRadius(int radius){
        this.radius = radius ;

        invalidate();
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
