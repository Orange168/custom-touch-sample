package com.custome.brokenview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * Created by Edward Lin
 * on 1/19/16 7:47 AM.
 */
public class Utils {

    private static Canvas mCanvas;
    private static Random random = new Random();
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

    private static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int tryCount) {
        while (tryCount-- > 0) {
            try {
                return Bitmap.createBitmap(width, height, config);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                System.gc();
            }
        }
        return null;
    }

    public static int nextInt(int a) {
        return random.nextInt(a);
    }
}
