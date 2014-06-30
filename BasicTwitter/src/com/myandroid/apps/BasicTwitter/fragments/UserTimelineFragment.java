package com.myandroid.apps.BasicTwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.myandroid.apps.BasicTwitter.models.Tweet;
import com.myandroid.apps.BasicTwitter.models.User;

public class UserTimelineFragment extends TweetsListFragment {
	User user;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		populateUserTimeline();
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void populateUserTimeline() {
		if (user == null) {
			client.getUserTimeline(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray arr) {
					adapter.addAll(Tweet.fromJsonArray(arr, Tweet.Category.USER_TWEETS));
				}

				@Override
				public void onFailure(Throwable t) {
					Log.d("Twitter", "[UserTimeline] Json handler Failure");
				}
			});
		}
		else {
			client.getUserTimeline(user.getUserId(), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray arr) {
					adapter.addAll(Tweet.fromJsonArray(arr, Tweet.Category.USER_TWEETS));
				}

				@Override
				public void onFailure(Throwable t) {

				}
			});
		}
	}
}
