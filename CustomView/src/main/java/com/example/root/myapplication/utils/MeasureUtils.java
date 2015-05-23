package com.example.root.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * Created by Edward Lin
 * on 5/21/15 8:23 PM.
 */
public class MeasureUtils {

    public static Point getScreenSize(Context context) {
        if (null == context ) {
            throw new IllegalArgumentException("Context is null, make sure you get the Context is " +
                    "not null ") ;
        }
        if (!(context instanceof Activity )) {
            throw new IllegalArgumentException("Context is not a activity instance " ) ;
        }

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Point mPoint = new Point();
        mPoint.set(metrics.widthPixels, metrics.heightPixels);
        return mPoint;
    }
}
