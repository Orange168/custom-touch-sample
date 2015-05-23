package com.example.root.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.root.myapplication.utils.MeasureUtils;

/**
 * Created by root on 5/21/15.
 */
public class FilterPictureView extends View {

    private Context context ;

    private Bitmap bitmap ;

    int x, y ;

    public FilterPictureView(Context context){
        this(context,null) ;
    }


    public FilterPictureView(Context context, AttributeSet attr){
        super(context,attr);
        initPaint();
        initRes() ;
    }

    private void initRes() {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.landscape_mountain) ;
        x = MeasureUtils.getScreenSize(context).x ;
        y = MeasureUtils.getScreenSize(context).y ;
    }

    private Paint mPaint ;

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
