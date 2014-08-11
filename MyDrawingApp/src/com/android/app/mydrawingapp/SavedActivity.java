package com.android.app.mydrawingapp;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class SavedActivity extends Activity {
	GridView gvDrawings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved);
		final ArrayList<String> titles = getIntent().getStringArrayListExtra("titles");
		gvDrawings = (GridView) findViewById(R.id.gvSavedDrawings);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, titles);
		gvDrawings.setAdapter(adapter);
		gvDrawings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(), DrawingActivity.class);
				i.putExtra("result", titles.get(position));
				setResult(RESULT_OK, i);
				finish();
			}
		});
	}
}
