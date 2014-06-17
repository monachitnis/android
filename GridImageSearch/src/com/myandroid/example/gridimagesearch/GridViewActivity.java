package com.myandroid.example.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.myandroid.example.gridimagesearch.FilterSettingsDialog.FilterSettingsDialogListener;

public class GridViewActivity extends FragmentActivity implements FilterSettingsDialogListener {

	GridView gvImageResults;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdapter imageAdapter;
	Filters f;
	AsyncHttpClient client;
	String actionBarQuery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view);
		
		gvImageResults = (GridView) findViewById(R.id.gvImageResults);
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
	
	public void onImageSearch(View v) {
		getImagesViaApi(0);
	}
	
	private void getImagesViaApi(final int page) {
		
		// check internet connectivity
		ConnectivityManager cm =
		        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		Log.d("DEBUG", "Network state=" + activeNetwork.getState());
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();
		if (!isConnected) {
			Log.d("DEBUG", "Cannot connect to internet");
			Toast.makeText(this,
					"Bad bad internet. No donut for you :( Please try again",
					Toast.LENGTH_LONG).show();
			return;
		}
		
		String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
				+ "start=" + page + "&v=1.0&q=" + Uri.encode(actionBarQuery);
		
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
			filters += "&imgtype=" + f.getTypeFilter() + "&imgcolor="
					+ f.getColorFilter();
			if (f.getSiteFilter() != null && !f.getSiteFilter().isEmpty())
				filters += "&as_sitesearch=" + f.getSiteFilter();
		}
		//Toast.makeText(this, "Fetching images for : " + url + filters, Toast.LENGTH_SHORT).show();

		client.get(url + filters, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					if (page == 0)
						imageResults.clear(); //new query
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t, JSONObject response) {
				Toast.makeText(GridViewActivity.this,
						"Failure in receiving API data. Please try again",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        
        MenuItem searchItem = menu.findItem(R.id.mi_action_search);
        SearchView sv = (SearchView) searchItem.getActionView();
		sv.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				actionBarQuery = query;
				getImagesViaApi(0);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
       return true;
    }
	
	public void onSettings(MenuItem mi) {
		// advanced user story - overlay instead of activity for settings
		showSettingsOverlay();
	}
	
	private void showSettingsOverlay() {
		FragmentManager fm = getFragmentManager();
		FilterSettingsDialog overlay = FilterSettingsDialog.newInstance("Image Filters");
		overlay.show(fm, "activity_settings");
	}

	@Override
	public void passSettings(Filters filters) {
		f = filters;
	}
}
