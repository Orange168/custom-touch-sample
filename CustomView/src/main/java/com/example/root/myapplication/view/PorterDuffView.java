package com.example.root.myapplication.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.root.myapplication.bo.PorterDuffBO;
import com.example.root.myapplication.utils.MeasureUtils;

/**
 * Created by Edward Lin
 * on 5/23/15 10:58 PM.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PorterDuffView extends View {

    public PorterDuffView(Context context) {
        this(context, null);
    }
    private  Context mContext ;

    private Paint mPaint ;

    private PorterDuffBO porterDuffBO ;

    private PorterDuffXfermode porterDuffXfermode ;

    private static final PorterDuff.Mode MODE = PorterDuff.Mode.DARKEN ;

    public PorterDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context ;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;

        porterDuffBO = new PorterDuffBO() ;

        porterDuffXfermode = new PorterDuffXfermode(MODE) ;

        calculate(mContext) ;
    }

    private int screenW, screenH ;

    private int s_l, s_t, d_l, d_t ;

    private static final int RECT_SIZE_SMALL = 400 ;

    private static final int RECT_SIZE_BIG = 800 ;

    private int rectX, rectY;
    /** calculate coordinates
     * @param context
     */
    private void calculate(Context context) {
        screenW = MeasureUtils.getScreenSize(context).x ;
        screenH = MeasureUtils.getScreenSize(context).y ;

        s_l = s_t = 0 ;

        d_l = screenW - RECT_SIZE_SMALL ;
        d_t = 0 ;

        //Calculate rectangle center coordinate
        rectX = screenW / 2 - RECT_SIZE_BIG / 2 ;

        rectY = RECT_SIZE_SMALL + (screenH - RECT_SIZE_SMALL) / 2 - RECT_SIZE_BIG / 2 ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

        porterDuffBO.setSize(RECT_SIZE_SMALL);

        canvas.drawBitmap(porterDuffBO.initDisBitmap(),s_l,s_t,mPaint);
        canvas.drawBitmap(porterDuffBO.initDisBitmap(),d_l,d_t,mPaint);

        int sc = canvas.saveLayer(0, 0, screenW, screenH, null, Canvas.ALL_SAVE_FLAG) ;

        porterDuffBO.setSize(RECT_SIZE_BIG);

        canvas.drawBitmap(porterDuffBO.initDisBitmap(), rectX, rectY, mPaint);

        mPaint.setXfermode(porterDuffXfermode) ;

        canvas.drawBitmap(porterDuffBO.initSrcBitmap(), rectX,rectY,mPaint);

        mPaint.setXfermode(null) ;
        canvas.restoreToCount(sc);
    }
}
