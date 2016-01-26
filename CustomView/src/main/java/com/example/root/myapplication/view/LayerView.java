package com.example.root.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Edward Lin
 * on 1/6/16 7:37 AM.
 */
public class LayerView extends View {

	private Paint mPaint;
	private int mHeight;
	private int mWidth;
	private RectF mRectF100;
	private RectF mRectF200;
	private RectF mRectF400;
	private RectF mRectF300;
	private RectF mRectF200_2;

	public LayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
	}

	public LayerView(Context context) {
		this(context,null);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mHeight = h;
		mRectF100 = new RectF(mWidth / 2 - 100, mHeight / 2 - 100, mWidth / 2 + 100, mHeight / 2 + 100);
		mRectF200 = new RectF(mWidth / 2F - 200, mHeight / 2 - 200, mWidth / 2 + 200, mHeight / 2 + 200);
		mRectF200_2 = new RectF(mWidth / 2F, mHeight / 2, mWidth / 2 + 200, mHeight / 2 + 200);
		mRectF400 = new RectF(mWidth / 2F , mHeight / 2 , mWidth / 2 + 400, mHeight / 2 + 400);
		mRectF300 = new RectF(mWidth / 2F - 300, mHeight / 2F - 300, mWidth / 2F + 300, mHeight / 2F + 300);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int saveId_1 = canvas.save(Canvas.CLIP_SAVE_FLAG);
		canvas.clipRect(mRectF300);
		canvas.drawColor(Color.YELLOW);
		canvas.restore();
		int saveId_2 = canvas.save(Canvas.CLIP_SAVE_FLAG);
		canvas.clipRect(mRectF200);
		canvas.drawColor(Color.GREEN);
		canvas.restore();

		int saveId_3 = canvas.save(Canvas.MATRIX_SAVE_FLAG);
//		canvas.saveLayer(mRectF100, null, Canvas.ALL_SAVE_FLAG); //1.
//		canvas.saveLayerAlpha(mRectF100, 0x55, Canvas.ALL_SAVE_FLAG); //2.
		canvas.rotate(5);
		mPaint.setColor(Color.BLUE);
		canvas.drawRect(mRectF100,mPaint);
//		canvas.restoreToCount(saveId_2);
		canvas.restore();

		mPaint.setColor(Color.CYAN);
		canvas.drawRect(mRectF400,mPaint);

//	/*
//	 * 保存并裁剪画布填充绿色
//	 */
//		int saveID1 = canvas.save(Canvas.CLIP_SAVE_FLAG);
//		canvas.clipRect(mWidth / 2F - 300, mHeight / 2F - 300, mWidth / 2F + 300, mHeight / 2F + 300);
//		canvas.drawColor(Color.YELLOW);
//
//	/*
//	 * 保存并裁剪画布填充绿色
//	 */
//		int saveID2 = canvas.save(Canvas.CLIP_SAVE_FLAG);
//		canvas.clipRect(mWidth / 2F - 200, mHeight / 2F - 200, mWidth / 2F + 200, mHeight / 2F + 200);
//		canvas.drawColor(Color.GREEN);
//
//	/*
//	 * 保存画布并旋转后绘制一个蓝色的矩形
//	 */
//		int saveID3 = canvas.save(Canvas.MATRIX_SAVE_FLAG);
//
//		// 旋转画布
//		canvas.rotate(5);
//		mPaint.setColor(Color.BLUE);
//		canvas.drawRect(mWidth / 2F - 100, mHeight / 2F - 100, mWidth / 2F + 100, mHeight / 2F + 100, mPaint);
//
//		mPaint.setColor(Color.CYAN);
//		canvas.drawRect(mWidth / 2F, mHeight / 2F, mWidth / 2F + 200, mHeight / 2F + 200, mPaint);

	}
}
