package com.android.app.mydrawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class OnTouchCircle extends View {
	Paint paint;
	Path path;

	public OnTouchCircle(Context context) {
		super(context);
	}

	public OnTouchCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(Color.MAGENTA);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(10);
		//persist the STATE in which the graphics are drawn
		path = new Path();
		setFocusable(true); //needs this explicitly
		setFocusableInTouchMode(true);
	}
	
	// dont store Canvas object locally, gets redrawn
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(path, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			path.moveTo(event.getX(), event.getY());
			//path.addCircle(event.getX(), event.getY(), 5, Direction.CW);
			postInvalidate();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			path.lineTo(event.getX(), event.getY());
			//path.addCircle(event.getX(), event.getY(), 5, Direction.CW);
			postInvalidate();
			return true;
		}
		return false;
	}


}
