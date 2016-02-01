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
    private int startLength;

    public LinePath(){
        super();
        points = new ArrayList<>();
        startLength = -1;
        endPoint = new Point();
    }

    public void line2End() {
        lineTo(endPoint.x,endPoint.y);
    }

    public void obtainEndPoint(int angleRandom, int[] angleBase, Rect r) {
        float gradient = -(float) Math.tan(Math.toRadians(angleRandom));
        int endX = 0, endY = 0;
        if (angleRandom >= 0 && angleRandom < 90) {
            if (angleRandom < angleBase[0]) {
                endX = r.right;
                endY = (int)(endX * gradient);
            } else if (angleRandom > angleBase[0]) {
                endY = r.top;
                endX = (int) (endY / gradient);
            } else if (angleRandom == angleBase[0]) {
                endY = r.top;
                endX = r.right;
            }
        } else if (angleRandom > 90 && angleRandom <= 180) {
            if (180 - angleRandom < angleBase[1]) {
                endX = r.left;
                endY = (int) (endX * gradient);
            } else if (180 - angleRandom > angleBase[1]) {
                endY = r.top;
                endX = (int) (endY / gradient);
            } else if (180 - angleRandom == angleBase[1]) {
                endY = r.top;
                endX = r.left;
            }
        } else if (angleRandom > 180 && angleRandom < 270) {
            if (angleRandom - 180 < angleBase[2]) {
                endX = r.left;
                endY = (int) (endX * gradient);
            } else if (angleRandom - 180 > angleBase[2]) {
                endY = r.bottom;
                endX = (int) (endY / gradient);
            } else if (angleRandom - 180 == angleBase[2]) {
                endY = r.bottom;
                endX = r.left;
            }
        } else if (angleRandom > 270 && angleRandom < 360) {
            if (360 - angleRandom < angleBase[3]) {
                endX = r.right;
                endY = (int) (endX * gradient);
            } else if (360 - angleRandom > angleBase[3]) {
                endY = r.bottom;
                endX = (int) (endY / gradient);
            } else if (360 - angleRandom == angleBase[3]) {
                endY = r.bottom;
                endX = r.right;
            }
        }
        else if(angleRandom == 90) {
            endX = 0;
            endY = r.top;
        }
        else if(angleRandom == 270) {
            endX = 0;
            endY = r.bottom;
        }
        endPoint.set(endX,endY);
    }

    public void setStraight(boolean straight) {
        this.isStraight = straight;
    }

    public void addPoint(float x, float y) {
        points.add(new Point((int) x, (int) y));
    }

    public void setStartLength(int startLength) {
        this.startLength = startLength;
    }

    public float getStartLength() {
        return startLength;
    }
}
