package com.example.root.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by root on 5/21/15.
 */
public class CustomView extends View {

    private Context context ;

    private  Activity activity ;

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
        init() ;
    }

    private void init(){
        mPaint = new Paint() ;
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(10);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mPoint.x/2,mPoint.y/2,radius,mPaint);
    }

    public  synchronized void setRadius(int radius){
        this.radius = radius ;

        invalidate();
    }
}
