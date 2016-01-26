package com.custome.brokenview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Edward Lin
 * on 1/19/16 7:28 AM.
 */
public class BrokenView extends View {

    private boolean enable;

    private HashMap<View, BrokenAnimator> animatorMap;

    private LinkedList<BrokenAnimator> animList ;

    public BrokenView(Context context) {
        this(context,null);
    }

    public BrokenView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BrokenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_HARDWARE,null);
        animatorMap = new HashMap<>();
        animList = new LinkedList<>();
        enable = true;
    }

    public static BrokenView add2Window(Activity activity){
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        BrokenView brokenView = new BrokenView(activity);
        rootView.addView(brokenView,new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Utils.screenHeight = dm.heightPixels;
        Utils.screenWidth = dm.widthPixels;
        return brokenView;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public BrokenAnimator getAnimator(View v) {
        BrokenAnimator animator = animatorMap.get(v);
        if (animator != null && animator.getStage() != BrokenAnimator.STAGE_EARLYEND) {
            return animator;
        } else
            return null;
    }

    public BrokenAnimator createAnimator(final View v, Point mPoint, BrokenConfig mConfig) {
        Bitmap bitmap = Utils.convertView2Bitmap(v);
        if (bitmap == null) return null;
        BrokenAnimator bAnim = new BrokenAnimator(this,v,bitmap,mPoint,mConfig);
        bAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                BrokenAnimator bAnim = (BrokenAnimator) animation;
                bAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        BrokenAnimator bA = (BrokenAnimator) animation;
                        bA.setInterpolator(new LinearInterpolator());
                        bA.setStage(BrokenAnimator.STAGE_FALLING);
                        bA.setFallingDuration();
                        onBrokenFallingEnd(v);
                        bA.removeUpdateListener(this);
                    }
                });
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                BrokenAnimator bAnim = (BrokenAnimator) animation;
                animatorMap.remove(v);
                animList.remove(bAnim);
            }
        });

        animList.add(bAnim);
        animatorMap.put(v, bAnim);
        return bAnim;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (BrokenAnimator bAnim : animList) {
            bAnim.draw(canvas);
        }
    }

    public void reset(){
        for (BrokenAnimator bAnim: animList) {
            bAnim.removeAllListeners();
            bAnim.cancel();
        }
        animList.clear();
        animatorMap.clear();
        invalidate();
    }

    public void onBrokenCancel(View v) {
    }

    public void onBrokenStart(View v) {
    }

    public void onBrokenRestart(View v) {

    }

    public void onBrokenFallingEnd(View view) {
        view.setVisibility(INVISIBLE);
    }
}
