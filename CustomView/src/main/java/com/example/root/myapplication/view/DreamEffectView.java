package com.example.root.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.root.myapplication.R;
import com.example.root.myapplication.utils.MeasureUtils;

public class DreamEffectView extends View {
	private Paint mBitmapPaint;// 位图画笔
	private Bitmap mBitmap;// 位图
	private PorterDuffXfermode mXfermode;// 图形混合模式
	private int x, y;// 位图起点坐标
	private int screenW, screenH;// 屏幕宽高
	private Paint mShaderPaint;
	private Bitmap darkCornerBitmap;

	public DreamEffectView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 初始化资源
		initRes(context);
		// 初始化画笔
		initPaint();
	}

	/**
	 * 初始化资源
	 * 
	 * @param context
	 *            丢你螺母
	 */
	private void initRes(Context context) {
		// 获取位图
		mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.girl);



		screenW = MeasureUtils.getScreenSize(context).x;
		screenH = MeasureUtils.getScreenSize(context).y;

		x = screenW / 2 - mBitmap.getWidth() / 2;
		y = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getContext().getResources().getDisplayMetrics());
	}

	/**
	 * 初始化画笔
	 */
	private void initPaint() {
		// 实例化画笔
		mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		// 实例化混合模式
		mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SCREEN);
		// 去饱和、提亮、色相矫正
		mBitmapPaint.setColorFilter(new ColorMatrixColorFilter(
				new float[] {
						0.8587F, 0.2940F, -0.0927F, 0, 6.79F,
						0.0821F, 0.9145F, 0.0634F, 0, 6.79F,
						0.2019F, 0.1097F, 0.7483F, 0, 6.79F,
						0, 0, 0, 1, 0
				}));


		// 根据我们源图的大小生成暗角Bitmap
		darkCornerBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		// 将该暗角Bitmap注入Canvas
		Canvas canvas = new Canvas(darkCornerBitmap);
		// 计算径向渐变半径
		float radiu = canvas.getHeight() * (2F / 3F);
		// 实例化Shader图形的画笔
		mShaderPaint = new Paint();
		// 设置径向渐变，渐变中心当然是图片的中心也是屏幕中心，渐变半径我们直接拿图片的高度但是要稍微小一点
		// 中心颜色为透明而边缘颜色为黑色
		RadialGradient radialGradient = new RadialGradient(screenW / 2 , screenH / 2 ,
				mBitmap.getHeight() * 7 / 8, Color.TRANSPARENT, Color.BLACK, Shader.TileMode.CLAMP);
		// 实例化一个矩阵
		Matrix matrix = new Matrix();
		// 设置矩阵的缩放
		matrix.setScale(canvas.getWidth() / (radiu * 2F), 1.0F);
		// 设置矩阵的预平移
		matrix.preTranslate(((radiu * 2F) - canvas.getWidth()) / 2F, 0);
		// 将该矩阵注入径向渐变
		radialGradient.setLocalMatrix(matrix);
		mShaderPaint.setShader(radialGradient);
		// 绘制矩形
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mShaderPaint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		// 新建图层
		int sc = canvas.saveLayer(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);
		// 绘制混合颜色
		canvas.drawColor(0xcc1c093e);
		// 设置混合模式
		mBitmapPaint.setXfermode(mXfermode);
		// 绘制位图
		canvas.drawBitmap(mBitmap, x, y, mBitmapPaint);
		// 还原混合模式
		mBitmapPaint.setXfermode(null);
		// 还原画布
		canvas.restoreToCount(sc);
//		 绘制一个跟图片大小一样的矩形
		canvas.drawRect(x, y, x + mBitmap.getWidth(), y + mBitmap.getHeight(), mShaderPaint);
//		 绘制我们画好的径向渐变图
		canvas.drawBitmap(darkCornerBitmap, x, y, null);
	}
}