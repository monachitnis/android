package com.myandroid.apps.BasicTwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.myandroid.apps.BasicTwitter.fragments.HomeTimelineFragment;
import com.myandroid.apps.BasicTwitter.fragments.MentionsTimelineFragment;
import com.myandroid.apps.BasicTwitter.fragments.TweetsListFragment;
import com.myandroid.apps.BasicTwitter.listeners.FragmentTabListener;
import com.myandroid.apps.BasicTwitter.models.Tweet;
import com.myandroid.apps.BasicTwitter.models.User;

public class TimelineActivity extends FragmentActivity {
	User u;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_timeline);
		
		setupTabs();
		
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
						HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
			    		MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        MenuItem actionViewItem = menu.findItem(R.id.miCompose);
        View v = actionViewItem.getActionView();
        Button composeActionView = (Button) v.findViewById(R.id.button1);
        composeActionView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TwitterApplication.getRestClient().getProfileInfo(new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						u = User.fromJson(json);
						Log.d("Twitter", "USER="+u);
					}
					@Override
					public void onFailure(Throwable arg0, JSONObject obj) {
						Log.d("Twitter", "User profile FAILURE");
					}
				});
				Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
				i.putExtra("user", u);
				startActivityForResult(i, 50);
				overridePendingTransition(R.anim.slide_down_entry, R.anim.slide_down_exit);
			}
        	
        });
        
        return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK)
			if (requestCode == 50) {
				//put new tweet on top
				TweetsListFragment frag = (TweetsListFragment) getSupportFragmentManager()
						.findFragmentById(R.id.flContainer);
				frag.getAdapter().insert(
						(Tweet) data.getSerializableExtra("tweet"), 0);
		}
	}
	
	public void onProfile(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
}
