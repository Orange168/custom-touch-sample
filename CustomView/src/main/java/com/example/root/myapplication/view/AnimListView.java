package com.example.root.myapplication.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ListView;

public class AnimListView extends ListView {
	private Camera mCamera;
	private Matrix mMatrix;
	private float[] values;

	public AnimListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mCamera = new Camera();
		mMatrix = new Matrix();
		values = new float[9];
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mCamera.save();
		mCamera.rotate(30, 0, 0);
		mCamera.getMatrix(mMatrix);
		mMatrix.getValues(values);
		mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
		mMatrix.getValues(values);
		mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
		mMatrix.getValues(values);
		canvas.concat(mMatrix);
		super.onDraw(canvas);
		mCamera.restore();
	}
}