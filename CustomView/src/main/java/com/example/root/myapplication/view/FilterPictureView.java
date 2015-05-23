package com.example.root.myapplication.view;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.view.View;

import com.example.root.myapplication.R;
import com.example.root.myapplication.utils.MeasureUtils;

/**
 * Created by root on 5/21/15.
 */
public class FilterPictureView extends View {

    private Context context ;

    private Bitmap bitmap ;

    int x, y , w,h;

    public FilterPictureView(Context context){
        this(context,null) ;
    }


    public FilterPictureView(Context context, AttributeSet attr){
        super(context,attr);
        this.context = context ;
        initPaint();
        initRes() ;
    }

    private void initRes() {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.landscape_mountain) ;
        x = (MeasureUtils.getScreenSize(context).x -bitmap.getWidth())/2 ;
        w = (MeasureUtils.getScreenSize(context).x +bitmap.getWidth())/2 ;
        y = (MeasureUtils.getScreenSize(context).y - bitmap.getHeight())/2;
        h = (MeasureUtils.getScreenSize(context).y + bitmap.getHeight())/2;
    }

    private Paint mPaint ;
    private AvoidXfermode avoidXfermode; // AV mode
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        // 1. ColorMatrixColorFilter
        ColorMatrix darkenedColorMatrix = new ColorMatrix(new float[] {
                0.5F, 0, 0, 0, 0,
                0, 0.5F, 0, 0, 0,
                0, 0, 0.5F, 0, 0,
                0, 0, 0, 1, 0,
        }) ;
        ColorMatrix grayedColorMatrix = new ColorMatrix(new float[] {
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0, 0, 0, 1, 0,
        }) ;
        ColorMatrix reversedColorMatrix = new ColorMatrix(new float[] {
                -1, 0, 0, 1, 1,
                0, -1, 0, 1, 1,
                0, 0, -1, 1, 1,
                0, 0, 0, 1, 0,
        }) ;
//        mPaint.setColorFilter(new ColorMatrixColorFilter(grayedColorMatrix)) ;
        //2. LightingColorFilter (int mul, int add)  0xAARRGGBB mul:colorMultiply, add:ColorAdd
        //3. PorterDuffColorFilter
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));

        //5. Xfermode AvoidXfermode、 PixelXorXfermode、 PorterDuffXfermode
        /**
         *  当画布中有跟0XFFFFFFFF色不一样的地方时候才“染”色
         *  note:  set Manifest.xml above API 16
         *   android:hardwareAccelerated="false"
         *  modify the tolerance in AvoidXformode
         */
        avoidXfermode = new AvoidXfermode(0XFFFFFFFF, 255, AvoidXfermode.Mode.TARGET);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, x, y, mPaint);
        // 5.  “染”什么色是由我们自己决定的
        mPaint.setARGB(255, 211, 53, 243);
        mPaint.setXfermode(avoidXfermode);
        canvas.drawRect(x, y, w, h, mPaint);
    }
}
