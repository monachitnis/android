package com.myandroid.apps.BasicTwitter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.myandroid.apps.BasicTwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	TwitterClient client;
	TweetArrayAdapter adapter;
	PullToRefreshListView lvTimeline;
	long max_id_pointer = 0;
	long since_id_pointer = 0;
	int count = 30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		//get the client from singleton class TwitterApplication
		client = TwitterApplication.getRestClient();
		
		// initial load
		populateTimeline(max_id_pointer, since_id_pointer);
		
		List<Tweet> tweets = new ArrayList<Tweet>();
		adapter = new TweetArrayAdapter(this, tweets);
		
		lvTimeline = (PullToRefreshListView) findViewById(R.id.lvTimeline);
		lvTimeline.setAdapter(adapter);
		
		//pagination
		lvTimeline.setOnScrollListener(new EndlessScrollListener(count) {

			@Override
			public void onLoadMore() {
				populateTimeline(max_id_pointer, 1);
			}
			
		});
		
		lvTimeline.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long rowid) {
				Intent i = new Intent(TimelineActivity.this, DetailActivity.class);
				i.putExtra("tweet", adapter.getItem(position));
				startActivity(i);
			}
			
		});
		
		//optional story
		lvTimeline.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				populateTimeline(0, since_id_pointer);
				lvTimeline.onRefreshComplete();
			}
		});

	}

	public void populateTimeline(final long maxid, final long sinceid) {
		Log.d("Twitter", "fetching tweets from maxid="+maxid + ",sinceid="+sinceid);
		client.getHomeTimeline(maxid, count, sinceid, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
			    Toast.makeText(TimelineActivity.this, "GET Success!", Toast.LENGTH_SHORT).show();
				List<Tweet> tweets = Tweet.fromJsonArray(json);
				if (tweets.size() > 0) {
					max_id_pointer = tweets.get(tweets.size()-1).getId();
					since_id_pointer = tweets.get(0).getId();
				}
				if (maxid == 0 && sinceid > 0) {
					int index = 0;
					for (Tweet t : tweets) {
						adapter.insert(t, index++);
					}
				} else {
					adapter.addAll(tweets);
				}
			}
			@Override
			public void onFailure(Throwable t, JSONObject responseBody) {
				Toast.makeText(TimelineActivity.this, "Client GET failed due to Twitter rate limiting",
					Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
	}
	
	public void onCompose(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, 50);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK)
			if (requestCode == 50) {
				//refresh timeline to see posted tweet
				Toast.makeText(TimelineActivity.this, "after compose sinceid="+since_id_pointer, Toast.LENGTH_SHORT).show();
				populateTimeline(0, since_id_pointer);
		}
	}
	
	public void onRefresh(MenuItem mi) {
		//refresh timeline to see posted tweet
		Toast.makeText(TimelineActivity.this, "on refresh sinceid="+since_id_pointer, Toast.LENGTH_SHORT).show();
		populateTimeline(0, since_id_pointer);
	}
	
}
