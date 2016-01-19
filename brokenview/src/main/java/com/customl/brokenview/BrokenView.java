package com.customl.brokenview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Edward Lin
 * on 1/19/16 7:28 AM.
 */
public class BrokenView extends View {

    public BrokenView(Context context) {
        super(context);
    }

    public BrokenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrokenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
