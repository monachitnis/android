package com.myandroid.apps.BasicTwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.myandroid.apps.BasicTwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailActivity extends Activity {

	ImageView ivProfileImg;
	TextView tvHandle;
	TextView tvName;
	TextView tvBody;
	TextView tvTimestamp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		setupViews();
		Intent i = getIntent();
		Tweet t = (Tweet) i.getSerializableExtra("tweet");
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(t.getUser().getProfileImgUrl(), ivProfileImg);
	    tvHandle.setText(t.getUser().getHandle());
	    tvName.setText(t.getUser().getName());
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
}
