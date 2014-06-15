package com.myandroid.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GridViewActivity extends Activity {

	EditText etSearchQuery;
	GridView gvImageResults;
	Button btSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdapter imageAdapter;
	Filters f;
	AsyncHttpClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view);
		setupViews();
		// show thumbnail view of images from list
		imageAdapter = new ImageResultsArrayAdapter(this, imageResults);
		gvImageResults.setAdapter(imageAdapter);
		
		// set new activity for on click of thumbnail
		gvImageResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position, long l) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
		
		client = new AsyncHttpClient();
		
		gvImageResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				getImagesViaApi(page);
			}
			
		});

	}

	private void setupViews() {
		etSearchQuery = (EditText) findViewById(R.id.etSearchQuery);
		gvImageResults = (GridView) findViewById(R.id.gvImageResults);
		btSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	public void onImageSearch(View v) {
		getImagesViaApi(0);
	}
	
	private void getImagesViaApi(int page) {
		String query = etSearchQuery.getText().toString();		
		String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
				+ "start=" + page + "&v=1.0&q=" + Uri.encode(query);
		
		String filters = new String();
		if (f != null) {
			filters = "&imgsz=";
			switch(f.getSizeFilter()) {
			case "small":
				filters = filters + "icon";
				break;
			case "medium":
				filters = filters + "small";
				break;
			case "large":
				filters = filters + "xxlarge";
				break;
			case "x-large":
				filters = filters + "huge";
				break;
			default:
				filters = filters + "small";
			}
			filters = filters + "&imgtype=" + f.getTypeFilter() + "&imgcolor="
					+ f.getColorFilter() + "&as_sitesearch="
					+ f.getSiteFilter();
		}
		Toast.makeText(this, "Fetching images for : " + url + filters, Toast.LENGTH_LONG).show();

		client.get(url + filters, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						try {
							imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
							//imageResults.clear();
							imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
							Log.d("DEBUG", imageResults.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(Throwable t, JSONObject response) {
						Log.d("DEBUG", "Json On Failure");
					}
				});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
	
	public void onSettings(MenuItem mi) {
		Toast.makeText(this, "Settings clicked!", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, SettingsActivity.class);
		if (f != null) {
			//settings should reflect what user set previously
			intent.putExtra("previous", f);
		}
		startActivityForResult(intent, 123);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 123) {
				f = (Filters) data.getSerializableExtra("filters");
				Toast.makeText(this, f.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}
}
