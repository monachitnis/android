package com.android.app.mydrawingapp;

import com.android.app.mydrawingapp.BrushSizeDialog.BrushSizeDialogListener;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class DrawingActivity extends Activity implements BrushSizeDialogListener {
	
	DrawingCanvas drawingBoard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		drawingBoard = (DrawingCanvas) findViewById(R.id.drawingCanvas1);
	}
	
	public void onBlack(View v) {
		drawingBoard.setColor(Color.BLACK);
	}
	
	public void onRed(View v) {
		drawingBoard.setColor(Color.RED);
	}
	
	public void onGreen(View v) {
		drawingBoard.setColor(Color.GREEN);
	}
	
	public void onBlue(View v) {
		drawingBoard.setColor(Color.BLUE);
	}
	
	public void onYellow(View v) {
		drawingBoard.setColor(Color.YELLOW);
	}
	
	public void onBrush(View v) {
		FragmentManager fm = getFragmentManager();
		BrushSizeDialog overlay = new BrushSizeDialog();
		overlay.show(fm, "brush_size");
	}

	@Override
	public void passSize(BRUSH_SIZE size) {
		drawingBoard.setBrushSize(size.get());
	}
	
	public void onNew(View v) {
		drawingBoard.clearCanvas();
	}
}
