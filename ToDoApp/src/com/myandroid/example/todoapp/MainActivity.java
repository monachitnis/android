package com.myandroid.example.todoapp;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int REQUEST_CODE = 20;
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		items.add("Welcome to ToDo List");
		setupListViewListener();
	}
	
	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long rowId) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long rowId) {
				String editText = items.get(position);
				Intent intent = new Intent(MainActivity.this, EditActivity.class);
				intent.putExtra("toedit", editText);
				intent.putExtra("pos", position);
				startActivityForResult(intent, REQUEST_CODE); // brings up the second activity
				itemsAdapter.notifyDataSetChanged();
				saveItems();
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// REQUEST_CODE is defined above
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			// Extract name value from result extras
			String edited = data.getExtras().getString("edited");
			int pos = data.getExtras().getInt("pos");
			// Toast the name to display temporarily on screen
			Toast.makeText(this, "ToDo item changed to:" + edited, Toast.LENGTH_SHORT).show();
			// set value to listview
			items.remove(pos);
			items.add(pos, edited);
			itemsAdapter.notifyDataSetChanged();
			saveItems();
		}
	} 
	
	private void readItems() {
		File dir = getFilesDir();
		File file = new File(dir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(file));
		}
		catch (Exception e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveItems() {
		File dir = getFilesDir();
		File file = new File(dir, "todo.txt");
		try {
			FileUtils.writeLines(file, items);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onAdd(View v) {
		EditText et = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(et.getText().toString());
		et.setText("");
		saveItems();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
