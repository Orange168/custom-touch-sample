package com.custome.brokenview;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Edward Lin
 * on 1/19/16 9:28 PM.
 */
public class BrokenAnimator extends ValueAnimator {
    public final static int STAGE_OVER = 5 ;
    public final static int STAGE_BREAKING = 0 ;
    public static final int STAGE_FALLING = 1;
    public static final int STAGE_EARLYEND = 2;
    private static int SEGMENT  ;
    private int stage = 0 ;
    private final BrokenView mBrokenView;
    private final View view;
    private final Bitmap mBitmap;
    private final Point mTouchPoint;
    private final BrokenConfig mConfig;
    private boolean canReverse = false;
    private boolean isPressed = true;
    private ArrayList<Path> mPathArray;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private LinePath[] lineRifts;
    private Path[] circleRifts;
    private int[] circleWidth;
    private boolean hasCircleRift = true;
    private final int threshold;
    private Piece[] pieces;
    private int offsetX;
    private int offsetY;
    private Paint mPaint;

    public BrokenAnimator(BrokenView brokenView, View v, Bitmap bitmap, Point mTouchPoint, BrokenConfig config) {
        mBrokenView = brokenView;
        view = v;
        mBitmap = bitmap;
        this.mTouchPoint = mTouchPoint;
        mConfig = config;
        mPathArray = new ArrayList<>();
        mPath = new Path();
        mPathMeasure = new PathMeasure();
        lineRifts = new LinePath[mConfig.complexity];
        circleRifts = new Path[mConfig.complexity];
        circleWidth = new int[mConfig.complexity];
        SEGMENT = mConfig.circleRiftsRadius;
        if (SEGMENT == 0) {
            hasCircleRift = false;
            SEGMENT = 66;
        }
        threshold = SEGMENT + SEGMENT / 2;
        setFloatValues(0F,1F);
        setInterpolator(new AccelerateInterpolator(2.0F));
        setDuration(mConfig.breakDuration);
        create() ;
    }

    private void create() {
        Rect r = new Rect();
        view.getGlobalVisibleRect(r);
        offsetX = mTouchPoint.x - r.left;
        offsetY = mTouchPoint.y - r.top;
        r.offset(-mTouchPoint.x, -mTouchPoint.y);

        Rect brokenViewR = new Rect();
        mBrokenView.getGlobalVisibleRect(brokenViewR);
        mTouchPoint.x -= brokenViewR.left;
        mTouchPoint.y -= brokenViewR.top;

        buildBrokenLines(r);
        buildBrokenAreas(r);
        buildPieces();
        buildPaintShader();
    }

    private void buildPaintShader() {
        if (mConfig.paint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();

            matrix.setTranslate(-offsetX - 10, -offsetY - 7);
            shader.setLocalMatrix(matrix);
            ColorMatrix mColorMatrix = new ColorMatrix();
            mColorMatrix.set(new float[]{
                    2.5f, 0, 0, 0, 100,
                    0, 2.5f, 0, 0, 100,
                    0, 0, 2.5f, 100,
                    0, 0, 0, 1, 0
            });
            mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
            mPaint.setShader(shader);
            mPaint.setStyle(Paint.Style.FILL);
        }else
            mPaint = mConfig.paint;
    }

    private void buildPieces() {
        pieces = new Piece[mPathArray.size()];
        Paint paint = new Paint();
        Matrix matrix = new Matrix();
        Canvas canvas = new Canvas();
        for (int i = 0; i < pieces.length; i++) {
            int shadow = Utils.nextInt(Utils.dp2px(2), Utils.dp2px(9));
            Path path = mPathArray.get(i);
            RectF r = new RectF();
            path.computeBounds(r,true);

            Bitmap pBitmap = Utils.createBitmapSafely((int) r.width() + shadow * 2,
                    (int) r.height() + shadow * 2, Bitmap.Config.ARGB_4444,1);
            if (pBitmap == null) {
                pieces[i] = new Piece(-1, -1, null, shadow);
                continue;
            }

            pieces[i] = new Piece((int) r.left + mTouchPoint.x - shadow,
                    (int) r.top + mTouchPoint.y - shadow, pBitmap, shadow);
            canvas.setBitmap(pieces[i].bitmap);
            BitmapShader mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
            matrix.reset();
            matrix.setTranslate(-r.left - offsetX + shadow, -r.top - offsetY + shadow);
            mBitmapShader.setLocalMatrix(matrix);

            paint.reset();
            Path offsetPath = new Path();
            offsetPath.addPath(path, -r.left + shadow, -r.top + shadow);

            //shadow
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(shadow,0,0,0xff333333);
            canvas.drawPath(offsetPath,paint);
            paint.setShadowLayer(0, 0, 0, 0);

            //In case the view has alpha channel
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
            canvas.drawPath(offsetPath,paint);
            paint.setXfermode(null);

            paint.setShader(mBitmapShader);
            paint.setAlpha(0xcc);
            canvas.drawPath(offsetPath,paint);
        }
        Arrays.sort(pieces);
    }

    private void buildBrokenAreas(Rect r) {
        final int SEGMENT_LESS = SEGMENT * 7 / 9;
        final int START_LENGTH = (int) (SEGMENT * 1.1);

        float linkLen = 0 ;
        int repeat = 0 ;

        PathMeasure pmCur = new PathMeasure();
        PathMeasure pmPre = new PathMeasure();

        for (int i = 0; i < mConfig.complexity; i++) {
            LinePath path = lineRifts[i];

            path.setStartLength(Utils.dp2px(START_LENGTH));
            if (repeat > 0){
                repeat--;
            }else {
                linkLen = Utils.nextInt(Utils.dp2px(SEGMENT_LESS), Utils.dp2px(SEGMENT));
                repeat = Utils.nextInt(3);
            }

            int pre = (i - 1) < 0 ? mConfig.complexity - 1 : i - 1;
            pmCur.setPath(path, false);
            pmPre.setPath(lineRifts[pre],false);
            if (hasCircleRift && pmCur.getLength() > linkLen && pmPre.getLength() > linkLen) {
                float[] pointCur = new float[2];
                float[] pointPre = new float[2];
                circleWidth[i] = Utils.nextInt(Utils.dp2px(1)) + 1;
                circleRifts[i] = new Path();
                pmCur.getPosTan(linkLen, pointCur, null);
                circleRifts[i].moveTo(pointCur[0],pointCur[1]);
                pmPre.getPosTan(linkLen, pointPre, null);
                circleRifts[i].lineTo(pointPre[0],pointPre[1]);

                // The area outside Circle-Rifts
                Path pathArea = new Path();
                pmPre.getSegment(linkLen, pmPre.getLength(), pathArea, true);
                pathArea.rLineTo(0,0); // KITKAT(API 19) and earlier need it
                drawBorder(pathArea, lineRifts[pre].endPoint,
                        path.points.get(path.points.size() - 1), r);

                for (int j=path.points.size() -2; j>=0; j--) {
                    pathArea.lineTo(path.points.get(j).x,path.points.get(j).y);
                }
                pathArea.lineTo(pointCur[0],pointCur[1]);
                pathArea.lineTo(pointPre[0],pointPre[1]);
                pathArea.close();
                mPathArray.add(pathArea);

                // The area inside Circle-Rifts, it's a isosceles triangles
                pathArea = new Path();
                pathArea.moveTo(0,0);
                pathArea.lineTo(pointPre[0],pointPre[1]);
                pathArea.lineTo(pointCur[0],pointCur[1]);
                pathArea.close();
                mPathArray.add(pathArea);
            }else {
                // Too short, there is no Circle-Rifts
                Path pathArea = new Path(lineRifts[pre]);
                drawBorder(pathArea,lineRifts[pre].endPoint,path.points.get(path.points.size() -1),r);
                for (int j=path.points.size() -2; j>=0; j--) {
                    pathArea.lineTo(path.points.get(j).x,path.points.get(j).y);
                }
                pathArea.close();
                mPathArray.add(pathArea);
            }
        }
    }

    private void drawBorder(Path path, Point startPoint, Point endPoint, Rect r) {

        if(startPoint.x == r.right) {
            if(endPoint.x == r.right)
                path.lineTo(endPoint.x, endPoint.y);
            else if(endPoint.y == r.top) {
                path.lineTo(r.right, r.top);
                path.lineTo(endPoint.x, endPoint.y);
            }
            else if(endPoint.x == r.left){
                path.lineTo(r.right, r.top);
                path.lineTo(r.left, r.top);
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.y == r.bottom){
                path.lineTo(r.right, r.top);
                path.lineTo(r.left, r.top);
                path.lineTo(r.left, r.bottom);
                path.lineTo(endPoint.x, endPoint.y);
            }
        }
        else if(startPoint.y == r.top) {
            if(endPoint.y == r.top){
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.x == r.left){
                path.lineTo(r.left,r.top);
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.y == r.bottom){
                path.lineTo(r.left,r.top);
                path.lineTo(r.left,r.bottom);
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.x == r.right){
                path.lineTo(r.left,r.top);
                path.lineTo(r.left,r.bottom);
                path.lineTo(r.right,r.bottom);
                path.lineTo(endPoint.x, endPoint.y);
            }
        }
        else if(startPoint.x == r.left) {
            if(endPoint.x == r.left){
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.y == r.bottom){
                path.lineTo(r.left,r.bottom);
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.x == r.right){
                path.lineTo(r.left,r.bottom);
                path.lineTo(r.right,r.bottom);
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.y == r.top){
                path.lineTo(r.left,r.bottom);
                path.lineTo(r.right,r.bottom);
                path.lineTo(r.right,r.top);
                path.lineTo(endPoint.x, endPoint.y);
            }
        }
        else if(startPoint.y == r.bottom) {
            if(endPoint.y == r.bottom) {
                path.lineTo(endPoint.x, endPoint.y);
            }
            else if(endPoint.x == r.right){
                path.lineTo(r.right,r.bottom);
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.y == r.top){
                path.lineTo(r.right,r.bottom);
                path.lineTo(r.right,r.top);
                path.lineTo(endPoint.x, endPoint.y);
            }else if(endPoint.x == r.left){
                path.lineTo(r.right,r.bottom);
                path.lineTo(r.right,r.top);
                path.lineTo(r.left,r.top);
                path.lineTo(endPoint.x, endPoint.y);
            }
        }
    }

    private void buildBrokenLines(Rect r) {
        LinePath[] baseLines = new LinePath[mConfig.complexity];
        buildBaseLines(baseLines,r);

        PathMeasure pmTmp = new PathMeasure();
        for (int i = 0; i < mConfig.complexity; i++) {
            lineRifts[i] = new LinePath();
            LinePath itemLinePath = lineRifts[i];
            itemLinePath.moveTo(0,0);
            itemLinePath.endPoint = baseLines[i].endPoint;

            pmTmp.setPath(baseLines[i],false);
            float length = pmTmp.getLength();

            if (length > Utils.dp2px(threshold)) {
                itemLinePath.setStraight(false);
                float[] pos = new float[2];
                pmTmp.getPosTan(Utils.dp2px(SEGMENT), pos, null);
                itemLinePath.lineTo(pos[0],pos[1]);
                itemLinePath.addPoint(pos[0], pos[1]);

                int xRandom, yRandom;
                int step = Utils.dp2px(threshold);
                do {
                    pmTmp.getPosTan(step, pos, null);
                    xRandom = (int) (pos[0] + Utils.nextInt(-Utils.dp2px(3), Utils.dp2px(2)));
                    yRandom = (int) (pos[1] + Utils.nextInt(-Utils.dp2px(2), Utils.dp2px(3)));
                    itemLinePath.lineTo(xRandom, yRandom);
                    itemLinePath.addPoint(xRandom, yRandom);
                    step += Utils.dp2px(SEGMENT);
                } while (step < length);
                itemLinePath.line2End();
            }else {
                itemLinePath = baseLines[i];
                itemLinePath.setStraight(true);
            }
            itemLinePath.points.add(itemLinePath.endPoint);
        }
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
        angleBase[0] = (int) Math.toDegrees(Math.atan(-(float) r.top / r.left));
        angleBase[1] = (int) Math.toDegrees(Math.atan(-(float) r.top / -r.right));
        angleBase[2] = (int) Math.toDegrees(Math.atan((float) r.bottom /- r.left));
        angleBase[3] = (int) Math.toDegrees(Math.atan((float) r.bottom / r.right));

        if (baseLines[0].endPoint.x < 0) // 2-quadrant,3-quadrant
            angle += 180;
        else if (baseLines[0].endPoint.x < 0 && baseLines[0].endPoint.y > 0) { // 4-quadrant
            angle += 360 ;// TODO: 1/19/16 ???
        }

        //random angle range
        int range = 360 / mConfig.complexity / 3 ;
        int angleRandom ;
        for (int i = 0; i < mConfig.complexity; i++) {
            angle = angle + 360 / mConfig.complexity;
            if (angle >= 360) angle -= 360;
            angleRandom = angle + Utils.nextInt(-range, range);
            angleRandom = angleRandom > 360 ? angleRandom - 360 :
                    angleRandom < 0 ? angleRandom + 360 : angleRandom;
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
                path.endPoint.set(r.left,r.top + Utils.nextInt( r.height()));
                break;
            case 1:
                path.endPoint.set(Utils.nextInt(r.width() ) + r.left,r.top);
                break;
            case 2:
                path.endPoint.set(r.right,r.top + Utils.nextInt(r.height()));
                break;
            case 3:
                path.endPoint.set(Utils.nextInt(r.width())  + r.left,r.bottom);
                break;
        }
        path.line2End();
    }

    public int getStage() {
        return stage;
    }

    @Override
    public void start() {
        super.start();
        canReverse = true;
        mBrokenView.invalidate();
    }

    public boolean doReverse() {
        if (canReverse) {
            isPressed = !isPressed;
            reverse();
        }
        return canReverse;
    }

    public boolean draw(Canvas canvas) {
        if (!isStarted()) return false;
        float fraction = getAnimatedFraction();

        if (stage == STAGE_BREAKING) {
            canvas.save();
            canvas.translate(mTouchPoint.x,mTouchPoint.y);
            for (int i = 0; i < mConfig.complexity; i++) {
                mPaint.setStyle(Paint.Style.FILL);
                mPath.reset();
                mPathMeasure.setPath(lineRifts[i],false);
                float pathLength = mPathMeasure.getLength();
                float startLength = lineRifts[i].getStartLength();
                float drawLength = startLength + fraction * (pathLength - startLength);
                if (drawLength > pathLength) drawLength = pathLength;
                mPathMeasure.getSegment(0, drawLength, mPath, false);
                mPath.rLineTo(0, 0); // KITKAT(API 19) and earlier need it
                canvas.drawPath(mPath,mPaint);

                if (hasCircleRift) {
                    if (circleRifts[i] != null && fraction > 0) {
                        mPaint.setStyle(Paint.Style.STROKE);
                        float t = (fraction - 0.1f) * 2;
                        if (t > 1) t = 1;
                        mPaint.setStrokeWidth(circleWidth[i] * t);
                        canvas.drawPath(circleRifts[i],mPaint);
                    }
                }
            }
            if (fraction > 0.8 && isPressed) {
                canReverse = false;
                setRepeatCount(1);
            }
            canvas.restore();
        } else if (getStage() == STAGE_FALLING) {
            int piecesNum = pieces.length;
            for (Piece p : pieces) {
                if (p.bitmap != null && p.advance(fraction)) {
                    canvas.drawBitmap(p.bitmap, p.matrix, null);
                } else {
                    piecesNum--;
                }
            }
            if (piecesNum == 0) {
                stage = STAGE_EARLYEND;
                mBrokenView.onBrokenFallingEnd(view);
            }
        }

        mBrokenView.invalidate();
        return true;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setFallingDuration() {
        setDuration(mConfig.fallDuration);
    }
}
