package com.myandroid.apps.BasicTwitter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ComposeActivity extends Activity {
	EditText tweet;
	TwitterClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		tweet = (EditText) findViewById(R.id.etNewTweet);
		client = TwitterApplication.getRestClient();
	}
	
	public void onPostNewTweet(View v) {
		client.postTweet(tweet.getText().toString());
		finish();
	}
}
