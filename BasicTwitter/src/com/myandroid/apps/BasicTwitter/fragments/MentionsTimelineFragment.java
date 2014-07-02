package com.myandroid.apps.BasicTwitter.fragments;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.myandroid.apps.BasicTwitter.EndlessScrollListener;
import com.myandroid.apps.BasicTwitter.models.Tweet;

import eu.erikw.PullToRefreshListView.OnRefreshListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MentionsTimelineFragment extends TweetsListFragment {
	private long max_id_pointer = 0;
	private long since_id_pointer = 0;
	private int count = 30;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// initial load
		List<Tweet> cached = Tweet.getAll(Tweet.Category.MENTIONS_TWEETS);
		if (cached != null && !cached.isEmpty()) {
			addAll(cached);
			max_id_pointer = cached.get(cached.size()-1).getId()-1;
			since_id_pointer = cached.get(0).getId();
			Log.d("Twitter", "Load offline tweets");
		}
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
				populateTimeline(max_id_pointer, 0);
			}

		});

		lvTimeline.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				adapter.clear();
				populateTimeline(0, since_id_pointer);
				lvTimeline.onRefreshComplete();
			}
		});
		return v;
	}
	
	public void populateTimeline(final long maxid, final long sinceid) {
		Log.d("Twitter", "[Mentions]fetching tweets from maxid="+maxid + ",sinceid="+sinceid);
		client.getMentionsTimeline(maxid, count, sinceid, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJsonArray(json, Tweet.Category.MENTIONS_TWEETS);
				if (maxid == 0 && sinceid > 0) {
					int index = 0;
					for (Tweet t : tweets) {
						insert(t, index++);
					}
				} else {
					addAll(tweets);
				}
				if (tweets.size() > 0) {
					max_id_pointer = tweets.get(tweets.size()-1).getId()-1;
					since_id_pointer = 
						(tweets.get(0).getId() > since_id_pointer) ? 
							tweets.get(0).getId() : since_id_pointer;
				}
			}
			@Override
			public void onFailure(Throwable t, JSONObject responseBody) {
				Log.d("Twitter", "[MentionsTimeline] Json handler Failure");
			}
		});
		
	}
}
