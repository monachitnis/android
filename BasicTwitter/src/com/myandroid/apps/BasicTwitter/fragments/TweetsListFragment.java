package com.myandroid.apps.BasicTwitter.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.myandroid.apps.BasicTwitter.DetailActivity;
import com.myandroid.apps.BasicTwitter.R;
import com.myandroid.apps.BasicTwitter.TimelineActivity;
import com.myandroid.apps.BasicTwitter.TweetArrayAdapter;
import com.myandroid.apps.BasicTwitter.TwitterApplication;
import com.myandroid.apps.BasicTwitter.TwitterClient;
import com.myandroid.apps.BasicTwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class TweetsListFragment extends Fragment {
	protected TwitterClient client;
	protected TweetArrayAdapter adapter;
	protected PullToRefreshListView lvTimeline;
	protected List<Tweet> tweets;
	//protected ProgressBar pb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		//assign our view references
		lvTimeline = (PullToRefreshListView) v.findViewById(R.id.lvTimeline);
		lvTimeline.setAdapter(adapter);
		lvTimeline.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long rowid) {
				Intent i = new Intent(getActivity(), DetailActivity.class);
				i.putExtra("tweet", adapter.getItem(position));
				startActivity(i);
			}

		});
		
		//return layout view
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//non-view initializations
		tweets = new ArrayList<Tweet>();
		adapter = new TweetArrayAdapter(getActivity(), tweets);
		//get the client from singleton class TwitterApplication
		client = TwitterApplication.getRestClient();
	}
	
	//Delegate adding to internal adapter
	public void addAll(List<Tweet> tweets) {
		adapter.addAll(tweets);
	}
	
	public void insert(Tweet tweet, int pos) {
		adapter.insert(tweet, pos);
	}
	
	protected Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
	
	public TweetArrayAdapter getAdapter() {
		return adapter;
	}
	
	/*protected ProgressBar getProgressBar() {
		return pb;
	}*/

}
