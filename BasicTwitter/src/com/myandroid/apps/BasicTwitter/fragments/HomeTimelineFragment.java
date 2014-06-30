package com.myandroid.apps.BasicTwitter.fragments;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.myandroid.apps.BasicTwitter.EndlessScrollListener;
import com.myandroid.apps.BasicTwitter.models.Tweet;

import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class HomeTimelineFragment extends TweetsListFragment {
	private long max_id_pointer = 0;
	private long since_id_pointer = 0;
	private int count = 30;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// initial load
		List<Tweet> cached = Tweet.getAll(Tweet.Category.HOME_TWEETS);
		if (cached != null && !cached.isEmpty())
			addAll(cached);
		else
			populateTimeline(max_id_pointer, since_id_pointer);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		// pagination
		lvTimeline.setOnScrollListener(new EndlessScrollListener(count) {

			@Override
			public void onLoadMore() {
				populateTimeline(max_id_pointer, 1);
			}

		});

		lvTimeline.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				populateTimeline(0, since_id_pointer);
				lvTimeline.onRefreshComplete();
			}
		});
		return v;
	}
	
	public void populateTimeline(final long maxid, final long sinceid) {
		client.getHomeTimeline(maxid, count, sinceid, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJsonArray(json, Tweet.Category.HOME_TWEETS);
				if (tweets.size() > 0) {
					max_id_pointer = tweets.get(tweets.size()-1).getId();
					since_id_pointer = tweets.get(0).getId();
				}
				if (maxid == 0 && sinceid > 0) {
					int index = 0;
					for (Tweet t : tweets) {
						insert(t, index++);
					}
				} else {
					addAll(tweets);
				}
			}
			@Override
			public void onFailure(Throwable t, JSONObject responseBody) {
				Log.d("Twitter", "[Home Timeline] Json handler Failure");
				if (!isNetworkAvailable()) {
					List<Tweet> cached = Tweet.getAll(Tweet.Category.HOME_TWEETS);
					if (cached != null && !cached.isEmpty())
						addAll(cached);
				}
			}
		});
		
	}
	
}
