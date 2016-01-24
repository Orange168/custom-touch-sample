package com.example.root.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Edward Lin
 * on 1/21/16 8:08 AM.
 */
public class PathView extends View {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private Path segmentPath;

    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPathMeasure = new PathMeasure();
        segmentPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.save();
//        canvas.rotate(30);
        mPath.moveTo(100,100);
        mPath.lineTo(100,500);
        mPath.lineTo(400,500);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
        mPathMeasure.setPath(mPath,false);
        int length = (int) mPathMeasure.getLength();
        mPathMeasure.getSegment(0, length-50, segmentPath, true);
//        canvas.translate(300,300);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(segmentPath,mPaint);
        float[] pos = new float[2];
        float[] tan = new float[2];
        canvas.restore();
        mPathMeasure.getPosTan(400, pos, tan);
        Log.e("pathView", "pos0=" + pos[0] + "; \t pos1=" + pos[1]);
        Log.e("pathView", "tan0=" + tan[0] + "; \t tan1=" + tan[1]);
//        Log.e("pathView","degree" + Math.toDegrees(tan[0]))
    }
}
