package com.myandroid.apps.BasicTwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myandroid.apps.BasicTwitter.models.Tweet;
import com.myandroid.apps.BasicTwitter.models.User;

public class ComposeActivity extends Activity {
	EditText tweet;
	TwitterClient client;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		tweet = (EditText) findViewById(R.id.etNewTweet);
		client = TwitterApplication.getRestClient();
		user = (User) getIntent().getSerializableExtra("user");
		/*TextView tvName = (TextView) findViewById(R.id.tvNewTweet);
		tvName.setText(user.getName());
		TextView tvHandle = (TextView) findViewById(R.id.tvComposeHandle);
		tvHandle.setText(user.getHandle());*/
	}
	
	public void onPostNewTweet(View v) {
		client.postTweet(tweet.getText().toString());
		/*Tweet t = new Tweet();
		t.setBody(tweet.getText().toString());
		t.setCreatedAt("just now");
		t.setUser(user);
		Intent i = new Intent(this, TimelineActivity.class);
		i.putExtra("tweet", t);
		setResult(RESULT_OK, i);*/
		finish();
	}
}
