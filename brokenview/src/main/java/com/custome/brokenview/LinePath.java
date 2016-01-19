package com.custome.brokenview;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Edward Lin
 * on 1/19/16 10:49 PM.
 */
public class LinePath extends Path {

    public boolean isStraight;
    public Point endPoint ;
    public ArrayList<Point> points;

    public LinePath(){
        super();
        points = new ArrayList<>();
        endPoint = new Point();
    }

    public void line2End() {
        lineTo(endPoint.x,endPoint.y);
    }

    public void obtainEndPoint(int angleRandom, int[] angleBase, Rect r) {

    }
}
