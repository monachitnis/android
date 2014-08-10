package com.android.app.mydrawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyUselessSquare extends View {
	Paint paint;

	public MyUselessSquare(Context context) {
		super(context);
	}

	public MyUselessSquare(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setStrokeWidth(4);
	}

	public MyUselessSquare(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	//Draw
	//called often, dont initialize objects here
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(20, 20, 200, 200, paint);
		paint.setColor(Color.GREEN);
		paint.setStyle(Style.FILL);
		canvas.drawRect(25, 25, 190, 190, paint);
	}
	
	//Interaction
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
	//Measurement
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(200, 200);
	}
	
	//Attributes
	
	//Persist the state
	@Override
	protected Parcelable onSaveInstanceState() {
		return super.onSaveInstanceState();
	}

}
