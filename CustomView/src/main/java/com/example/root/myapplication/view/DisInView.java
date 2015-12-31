package com.example.root.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.root.myapplication.R;
import com.example.root.myapplication.utils.MeasureUtils;

public class DisInView extends View {
	private Paint mPaint;// 画笔
	private Bitmap bitmapDis;// 位图

	private int x, y;// 位图绘制时左上角的起点坐标
	private int screenW, screenH;// 屏幕尺寸
	private Bitmap bitmapSrc;

	public DisInView(Context context) {
		this(context, null);
	}

	public DisInView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 初始化画笔
		initPaint();

		// 初始化资源
		initRes(context);
	}

	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		// 实例化画笔
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	/**
	 * 初始化资源
	 */
	private void initRes(Context context) {
		// 获取位图
		bitmapDis = BitmapFactory.decodeResource(context.getResources(), R.drawable.a3);
		bitmapSrc = BitmapFactory.decodeResource(context.getResources(), R.drawable.a3_mask);

		// 获取包含屏幕尺寸的数组
		Point screenSize = MeasureUtils.getScreenSize(context);

		// 获取屏幕尺寸
		screenW = screenSize.x;
		screenH = screenSize.y;

		/*
		 * 计算位图绘制时左上角的坐标使其位于屏幕中心
		 * 屏幕坐标x轴向左偏移位图一半的宽度
		 * 屏幕坐标y轴向上偏移位图一半的高度
		 */
		x = screenW / 2 - bitmapDis.getWidth() / 2;
		y = screenH / 2 - bitmapDis.getHeight() / 2;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		int sc = canvas.saveLayer(0, 0, screenW, screenH, null, Canvas.ALL_SAVE_FLAG);
		canvas.drawColor(0xFF8f66DA);
		canvas.drawColor(0xFFFFD975);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		// 绘制美女图
		canvas.scale(0.5f, 0.5f);
		canvas.drawBitmap(bitmapSrc, 0, 0, mPaint);
		mPaint.setXfermode(null);
		canvas.restoreToCount(sc);
	}
}