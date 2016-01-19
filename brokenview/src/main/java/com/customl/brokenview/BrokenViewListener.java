package com.customl.brokenview;

import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Edward Lin
 * on 1/19/16 8:05 AM.
 */
public class BrokenViewListener implements View.OnTouchListener {

    private BrokenView mBrokenView ;
    private BrokenConfig mConfig ;

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

        public BrokenViewListener build() {
            return new BrokenViewListener(this);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
