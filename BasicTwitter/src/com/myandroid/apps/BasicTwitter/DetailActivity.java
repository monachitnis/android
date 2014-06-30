package com.myandroid.apps.BasicTwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myandroid.apps.BasicTwitter.models.Tweet;
import com.myandroid.apps.BasicTwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailActivity extends Activity {

	ImageView ivProfileImg;
	TextView tvHandle;
	TextView tvName;
	TextView tvBody;
	TextView tvTimestamp;
	User u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		setupViews();
		Intent i = getIntent();
		Tweet t = (Tweet) i.getSerializableExtra("tweet");
		ImageLoader loader = ImageLoader.getInstance();
		u = t.getUser();
		loader.displayImage(u.getProfileImgUrl(), ivProfileImg);
	    tvHandle.setText(u.getHandle());
	    tvName.setText(u.getName());
		tvBody.setText(t.getBody());
		tvTimestamp.setText(t.getCreatedAt(true));
		
	}
	
	public void setupViews() {
		ivProfileImg = (ImageView) findViewById(R.id.ivThmb);
		ivProfileImg.setImageResource(android.R.color.transparent);
		tvHandle = (TextView) findViewById(R.id.tvHandle);
		tvName = (TextView) findViewById(R.id.tvName);
		tvBody = (TextView) findViewById(R.id.tvBody);
		tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);
	}
	
	public void onUser(View v) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("user", u);
		startActivity(i);
	}
}
