package com.example.root.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.example.root.myapplication.R;

public class BitmapMeshView extends View {
	private static final int W = 19;// 横向分割成的网格数量
	private static final int H = 19;// 纵向分割成的网格数量
	private static final int COUNT = (W + 1) * (H + 1);// 横纵向网格交织产生的点数量

	private Bitmap mBitmap;// 位图资源

	private float[] verts;// 交点的坐标数组

	public BitmapMeshView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 获取位图资源
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);

		// 实例化数组
		verts = new float[COUNT * 2];
		translation();

	}

	public void translation() {

	/*
	 * 生成各个交点坐标
	 */
		int index = 0;
		float multiple = mBitmap.getWidth();
		for (int y = 0; y <= H; y++) {
			float fy = mBitmap.getHeight() * y / H;
			for (int x = 0; x <= W; x++) {
				float fx = mBitmap.getWidth() * x / W + ((H - y) * 1.0F / H * multiple);
				setXY(fx, fy, index);
				index += 1;
			}
		}
		invalidate();
	}

	public void magnify() {
		int index = 0 ;
		float multipleY = mBitmap.getHeight() / H;
		float multipleX = mBitmap.getWidth() / W;
		for (int y = 0; y <= H; y++) {
			float fy = multipleY * y;
			for (int x = 0; x <= W; x++) {
				float fx = multipleX * x;
				setXY(fx, fy, index);
				if (5 == y) {
					if (8 == x) {
						setXY(fx - multipleX, fy - multipleY, index);
					}
					if (9 == x) {
						setXY(fx + multipleX, fy - multipleY, index);
					}
				}
				if (6 == y) {
					if (8 == x) {
						setXY(fx - multipleX, fy + multipleY, index);
					}
					if (9 == x) {
						setXY(fx + multipleX, fy + multipleY, index);
					}
				}
				index += 1;
			}
		}
		invalidate();
	}

	/**
	 * 将计算后的交点坐标存入数组
	 * @param fx
	 * @param fy  y坐标
	 * @param index 标识值
	 */
	private void setXY(float fx, float fy, int index) {
		verts[index * 2 + 0] = fx;
		verts[index * 2 + 1] = fy;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制网格位图
		canvas.drawBitmapMesh(mBitmap, W, H, verts, 0, null, 0, null);
	}
}