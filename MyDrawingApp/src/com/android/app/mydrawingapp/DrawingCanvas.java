package com.android.app.mydrawingapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

public class DrawingCanvas extends View {
	Paint paint;
	List<Pair<Path, Pair<Integer, Integer>>> paths;
	Path currentPath;
	//defaults
	int color = Color.BLACK;
	int brushSize = BRUSH_SIZE.SMALL.get();

	public DrawingCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		currentPath = new Path();
		paths = new ArrayList<Pair<Path, Pair<Integer, Integer>>>();
		paths.add(Pair.create(currentPath, Pair.create(brushSize, color)));
		setFocusable(true); //needs this explicitly
		setFocusableInTouchMode(true);
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
	}
	
	public List<Pair<Path, Pair<Integer, Integer>>> getPaths() {
		return paths;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (Pair<Path, Pair<Integer, Integer>> path : paths) {
			Pair<Integer, Integer> values = path.second;
			paint.setStrokeWidth(values.first);
			paint.setColor(values.second);
			canvas.drawPath(path.first, paint);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// Defines the extra padding for the shape name text
	    int contentWidth = 500 + getSuggestedMinimumWidth();
	    // Try for a width based on our minimum
	    int minw = contentWidth + getPaddingLeft() + getPaddingRight();
	    int w = resolveSizeAndState(minw, widthMeasureSpec, 0);
	    // Ask for a height that would let the view get as big as it can
	    int minh = 1300 + getPaddingBottom() + getPaddingTop();
	    int h = resolveSizeAndState(minh, heightMeasureSpec, 0);
	    // Calling this method determines the measured width and height
	    setMeasuredDimension(w, h);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean change = false;
		if (color != paint.getColor()) {
			paint.setColor(color);
			change = true;
		}
		if (brushSize != paint.getStrokeWidth()) {			
			paint.setStrokeWidth(brushSize);
			change = true;
		}
		if (change) {
			Path p = new Path();
			currentPath = p;
			paths.add(Pair.create(currentPath, Pair.create(brushSize, color)));
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			currentPath.moveTo(event.getX(), event.getY());
			postInvalidate();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			currentPath.lineTo(event.getX(), event.getY());
			postInvalidate();
			return true;
		}
		return false;
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		return super.onSaveInstanceState();
	}
	
	public void clearCanvas() {
		currentPath = new Path();
		paths = new ArrayList<Pair<Path, Pair<Integer, Integer>>>();
		paths.add(Pair.create(currentPath, Pair.create(brushSize, color)));
		invalidate();
	}
	
	public void loadSavedDrawing(List<Pair<Path, Pair<Integer, Integer>>> paths) {
		this.paths = paths;
		invalidate();
	}
	
}
