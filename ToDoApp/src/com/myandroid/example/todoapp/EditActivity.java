package com.myandroid.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends Activity {
	
	EditText changed;
	String orig;
	int pos;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		orig = getIntent().getStringExtra("toedit");
		pos = getIntent().getIntExtra("pos", 0);
		changed = (EditText) findViewById(R.id.editItem);
		changed.setText(orig); //default setting
	}
	
	public void onEdit(View v) {
		changed = (EditText) findViewById(R.id.editItem);
		// Prepare data intent
		Intent data = new Intent();
		// Pass relevant data back as a result
		data.putExtra("edited", changed.getText().toString());
		data.putExtra("pos", pos);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data); // set result code and bundle data for response
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
