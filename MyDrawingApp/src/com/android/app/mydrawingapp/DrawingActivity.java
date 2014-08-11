package com.android.app.mydrawingapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;

import com.android.app.mydrawingapp.BrushSizeDialog.BrushSizeDialogListener;
import com.android.app.mydrawingapp.SaveAsDialog.SaveAsDialogListener;

public class DrawingActivity extends Activity implements BrushSizeDialogListener, SaveAsDialogListener {
	
	DrawingCanvas drawingBoard;
	Map<String, List<Pair<Path, Pair<Integer, Integer>>>> savedDrawings;
	ShareActionProvider miShareAction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		drawingBoard = (DrawingCanvas) findViewById(R.id.drawingCanvas1);
		savedDrawings = new HashMap<String, List<Pair<Path, Pair<Integer, Integer>>>>();
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
	
	public void onWhite(View v) {
		drawingBoard.setColor(Color.WHITE);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_drawing, menu);
		//MenuItem miShare = menu.getItem(R.id.miShare);
		//miShare.getActionProvider();
		return true;
	}
	
	public void onSaved(MenuItem mi) {
		Intent i = new Intent(this, SavedActivity.class);
		ArrayList<String> titles = new ArrayList<String>();
		for (String t : savedDrawings.keySet()) {
			titles.add(t);
		}
		i.putStringArrayListExtra("titles", titles);
		startActivityForResult(i, 50);
	}
	
	public void onShare(MenuItem mi) {
		Bitmap myBitmap = Bitmap.createBitmap((int)100, (int)100, Config.RGB_565);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 50) {
				String retrieved = data.getStringExtra("result");
				if (savedDrawings.get(retrieved) != null) {
					drawingBoard.loadSavedDrawing(savedDrawings.get(retrieved));
				}
			}
		}
	}
	
	public void onSaveDrawing(View v) {
		FragmentManager fm = getFragmentManager();
		SaveAsDialog overlay = new SaveAsDialog();
		overlay.show(fm, "save_as");
	}
	
	public void onErase(View v) {
		drawingBoard.setColor(Color.WHITE);
	}

	@Override
	public void saveAs(String title) {
		savedDrawings.put(title, drawingBoard.getPaths());
	}
}
