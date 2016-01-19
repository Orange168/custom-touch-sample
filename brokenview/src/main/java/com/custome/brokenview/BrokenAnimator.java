package com.custome.brokenview;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Edward Lin
 * on 1/19/16 9:28 PM.
 */
public class BrokenAnimator extends ValueAnimator {
    public final static int STAGE_OVER = 5 ;
    private int stage = 0 ;
    private final BrokenView mBrokenView;
    private final View view;
    private final Bitmap mBitmap;
    private final Point mTouchPoint;
    private final BrokenConfig mConfig;

    public BrokenAnimator(BrokenView brokenView, View v, Bitmap bitmap, Point mTouchPoint, BrokenConfig config) {
        mBrokenView = brokenView;
        view = v;
        mBitmap = bitmap;
        this.mTouchPoint = mTouchPoint;
        mConfig = config;
        init() ;
    }

    private void init() {
        ArrayList<Path> mPathArray = new ArrayList<>();
        Path mPath = new Path();
        PathMeasure mPathMeasure = new PathMeasure();

        create() ;

    }

    private void create() {
        Rect r = new Rect();
        view.getGlobalVisibleRect(r);
        r.offset(-mTouchPoint.x, -mTouchPoint.y);

        Rect brokenViewR = new Rect();
        mBrokenView.getGlobalVisibleRect(brokenViewR);
        mTouchPoint.x -= brokenViewR.left;
        mTouchPoint.y -= brokenViewR.top;

        buildBrokenLines(r);
    }

    private void buildBrokenLines(Rect r) {
        LinePath[] baseLines = new LinePath[mConfig.complexity];
        buildBaseLines(baseLines,r);
    }

    private void buildBaseLines(LinePath[] baseLines, Rect r) {
        for (int i = 0; i < mConfig.complexity; i++) {
            baseLines[i] = new LinePath();
            baseLines[i].moveTo(0,0);
        }
        buildFirstLine(baseLines[0], r);

        //First angle
        int angle = (int) Math.toDegrees(
                Math.atan(-(float) baseLines[0].endPoint.y / baseLines[0].endPoint.x)
        );

        int[] angleBase = new int[4];
        angleBase[0] = (int) Math.toDegrees(Math.atan((float) r.top / r.left));
        angleBase[1] = (int) Math.toDegrees(Math.atan(-(float) r.top / r.right));
        angleBase[2] = (int) Math.toDegrees(Math.atan(-(float) r.bottom / r.left));
        angleBase[3] = (int) Math.toDegrees(Math.atan((float) r.bottom / r.right));

        if (baseLines[0].endPoint.x < 0)
            angle += 180;
        else if (baseLines[0].endPoint.y > 0)
            angle += 360 ;// TODO: 1/19/16 ???

        //random angle range
        int range = 360 / mConfig.complexity / 3 ;
        int angleRandom = 0;
        for (int i = 0; i < mConfig.complexity; i++) {
            angle = angle + 360 / mConfig.complexity;

            baseLines[i].obtainEndPoint(angleRandom,angleBase,r);
            baseLines[i].line2End();
        }

    }

    private void buildFirstLine(LinePath path, Rect r) {
        int[] range = new int[]{-r.left, -r.top, r.right, r.bottom};
        int max = -1 ;
        int maxId = 0 ;
        for (int i = 0; i < 4; i++) {
            if (range[i] > max) {
                max = range[i];
                maxId = i;
            }
        }

        switch (maxId) {
            case 0:
                path.endPoint.set(r.left,Utils.nextInt(r.top + r.height()));
                break;
            case 1:
                path.endPoint.set(Utils.nextInt(r.width() + r.left),r.top);
                break;
            case 2:
                path.endPoint.set(r.right,r.top + r.height());
                break;
            case 3:
                path.endPoint.set(Utils.nextInt(r.width() + r.left),r.bottom);
                break;
        }
        path.line2End();
    }

    public int getStage() {
        return stage;
    }

    public boolean doReverse() {
        return false;
    }
}
