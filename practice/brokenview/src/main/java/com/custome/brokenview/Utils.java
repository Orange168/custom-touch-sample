package com.custome.brokenview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by Edward Lin
 * on 1/19/16 7:47 AM.
 */
public class Utils {

    private static Canvas mCanvas;
    private static Random random = new Random();
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
    private Utils(){}

    public static int screenWidth ;
    public static int screenHeight ;

    public static Bitmap convertView2Bitmap(View v) {
        v.clearFocus();
        Bitmap bitmap = createBitmapSafely(v.getWidth(),v.getHeight(),
                Bitmap.Config.ARGB_4444,2) ;
        if (bitmap != null) {
            mCanvas = new Canvas();
            mCanvas.setBitmap(bitmap);
            mCanvas.translate(-v.getScrollX(),-v.getScrollY());// TODO: 1/19/16 Debug the values
            v.draw(mCanvas);
            mCanvas.setBitmap(null);
        }
        return bitmap;
    }

    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int tryCount) {
        while (tryCount-- > 0) {
            try {
                return Bitmap.createBitmap(width, height, config);
            } catch (OutOfMemoryError e) {
                Log.e("Utils", "OOM: Error" + e.toString());
                System.gc();
            }
        }
        return null;
    }

    public static int nextInt(int a) {
        return random.nextInt(a);
    }

    public static int nextInt(int a, int b) {
        int i = 0;
        try {
            i = Math.min(a, b) + random.nextInt(Math.abs(a - b));
        } catch (Exception e) {
            Log.e("Utils", "a =" + a + ";\tb=" + b);
            e.printStackTrace();
        }
        return i;
    }

    public static int dp2px(int threshold) {
        return Math.round(threshold * DENSITY);
    }

    public static float nextFloat(int a, int b) {
        return Math.min(a, b) + random.nextFloat() * Math.abs(a - b);
    }

    public static float nextFloat(float v) {
        return random.nextFloat() * v;
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
}
