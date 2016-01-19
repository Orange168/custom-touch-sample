package com.custome.brokenview;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Edward Lin
 * on 1/19/16 8:05 AM.
 */
public class BrokenViewListener implements View.OnTouchListener {

    private BrokenView mBrokenView ;
    private BrokenConfig mConfig ;
    private BrokenAnimator bAnim;

    public BrokenViewListener(Builder builder) {
        this.mBrokenView = builder.mBrokenView;
        this.mConfig = builder.mConfig;
    }

    public static class Builder{
        private BrokenConfig mConfig ;
        private BrokenView mBrokenView;

        public Builder(BrokenView brokenView) {
            mBrokenView = brokenView;
            mConfig = new BrokenConfig();
        }

        public Builder setComplexity(int complexity){
            mConfig.complexity = complexity < 6 ? 6 :
                    complexity > 20 ? 20 : complexity;
            return this;
        }

        public Builder setBreakDuration(int breakDuration){
            mConfig.breakDuration = breakDuration < 200 ? 200 : breakDuration;
            return this;
        }

        public Builder setFallDuration(int fallDuration) {
            mConfig.fallDuration = fallDuration < 200 ? 200 : fallDuration;
            return this;
        }

        public Builder setCircleRiftsRadius(int radius) {
            mConfig.circleRiftsRadius = radius < 20 && radius != 0 ? 20 : radius;
            return this;
        }

        public Builder setPaint(Paint paint) {
            mConfig.paint = paint;
            return this;
        }
        public Builder setEnableArea(Region region) { // TODO: 1/19/16 ???????
            mConfig.region = region;
            mConfig.childView = null;
            return this;
        }

        public Builder setEnableArea(View childView) { // TODO: 1/19/16 ???????
            mConfig.childView = childView;
            mConfig.region = null;
            return this;
        }


        public BrokenViewListener build() {
            return new BrokenViewListener(this);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!mBrokenView.isEnable()) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = mConfig.childView;
                if (view != null) {
                    mConfig.region = new Region(view.getLeft(), view.getTop(),
                            view.getRight(), view.getBottom());
                }
                if (mConfig.region == null ||
                        mConfig.region.contains((int)event.getX(), (int)event.getY())) {
                    Point mPoint = new Point((int)event.getRawX(), (int)event.getRawY());
                    bAnim = mBrokenView.getAnimator(v);
                    if (bAnim == null)
                        bAnim = mBrokenView.createAnimator(v,mPoint,mConfig) ;
                    if (bAnim == null) return false; // TODO: 1/19/16 why is true in the original
                    if (!bAnim.isStarted()) {
                        bAnim.start();
                        mBrokenView.onBrokenStart(v) ;
                    } else if (bAnim.doReverse()) {
                        mBrokenView.onBrokenRestart(v) ;
                    }
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }else
                    return false;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                bAnim = mBrokenView.getAnimator(v);
                if (bAnim != null && bAnim.isStarted()) {
                    if (bAnim.doReverse()) mBrokenView.onBrokenCancel(v);
                }
                break;
        }
        return true;
    }
}
