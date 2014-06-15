package com.myandroid.example.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	Spinner spSize;
	Spinner spColor;
	Spinner spType;
	EditText etSite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupViews();
		// retain previous settings
		Intent i = getIntent();
		Filters f = (Filters) i.getSerializableExtra("previous");
		if (f != null) {
			ArrayAdapter spinnerAdapter = (ArrayAdapter) spSize.getAdapter();
			int position = spinnerAdapter.getPosition(f.getSizeFilter());
			spSize.setSelection(position);
			
			spinnerAdapter = (ArrayAdapter) spColor.getAdapter();
			position = spinnerAdapter.getPosition(f.getColorFilter());
			spColor.setSelection(position);
			
			spinnerAdapter = (ArrayAdapter) spType.getAdapter();
			position = spinnerAdapter.getPosition(f.getTypeFilter());
			spType.setSelection(position);
			
			etSite.setText(f.getSiteFilter());
		}
	}
	
	private void setupViews() {
		spSize = (Spinner) findViewById(R.id.spSize);
		spColor = (Spinner) findViewById(R.id.spColor);
		spType = (Spinner) findViewById(R.id.spType);
		etSite = (EditText) findViewById(R.id.etSite);
	}

	public void onSave(View v) {
		
		Filters f = new Filters(spSize.getSelectedItem().toString(), spColor
				.getSelectedItem().toString(), spType.getSelectedItem()
				.toString(), etSite.getText().toString());
		Intent i = new Intent(this, GridViewActivity.class);
		i.putExtra("filters", f);
		setResult(RESULT_OK, i);
		finish();
	}
}
