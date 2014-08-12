package com.myandroid.apps.BasicTwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.myandroid.apps.BasicTwitter.fragments.UserTimelineFragment;
import com.myandroid.apps.BasicTwitter.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		User u = (User) getIntent().getSerializableExtra("user");
		loadProfileInfo(u);
		UserTimelineFragment utf = (UserTimelineFragment) 
	            getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
		utf.setUser(u);
	}
	
	public void loadProfileInfo(final User user) {
		if (user == null)
		TwitterApplication.getRestClient().getProfileInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User user = User.fromJson(json);
				getActionBar().setTitle(user.getHandle());
				populateProfileHeader(user);
			}
			
			@Override
			public void onFailure(Throwable t) {
				
			}
		});
		else
			TwitterApplication.getRestClient().getProfileInfo(user.getUserId(),
					new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					getActionBar().setTitle(user.getHandle());
					populateProfileHeader(user);
				}
				
				@Override
				public void onFailure(Throwable t) {
					
				}
			});
	}

	private void populateProfileHeader(User user) {
		ImageView ivUserProfile = (ImageView) findViewById(R.id.ivUserProfile);
		TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
		TextView tvNumFollowers = (TextView) findViewById(R.id.tvNumFollowers);
		TextView tvNumFollowing = (TextView) findViewById(R.id.tvNumFollowing);
		
		tvUserName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		ImageLoader.getInstance().displayImage(user.getProfileImgUrl(), ivUserProfile);
		tvNumFollowers.setText(user.getNumFollowers() + " Followers");
		tvNumFollowing.setText(user.getNumFollowing() + " Following");
		
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
