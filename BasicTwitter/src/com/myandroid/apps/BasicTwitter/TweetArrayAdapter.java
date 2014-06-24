package com.myandroid.apps.BasicTwitter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myandroid.apps.BasicTwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	ImageView ivProfileImg;
	TextView tvHandle;
	TextView tvName;
	TextView tvBody;
	TextView tvTimestamp;

	public TweetArrayAdapter(Context context, List<Tweet> streamItems) {
		super(context, R.layout.item_timeline, streamItems);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet item = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_timeline, parent, false);
		}

		setupViews(convertView);

		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(item.getUser().getProfileImgUrl(), ivProfileImg);
	    tvHandle.setText(item.getUser().getHandle());
	    tvName.setText(item.getUser().getName());
		tvBody.setText(item.getBody());
		tvTimestamp.setText(item.getCreatedAt(false));

		return convertView;
	}
	
	public void setupViews(View convertView) {
		ivProfileImg = (ImageView) convertView.findViewById(R.id.ivThmb);
		ivProfileImg.setImageResource(android.R.color.transparent);
		tvHandle = (TextView) convertView.findViewById(R.id.tvHandle);
		tvName = (TextView) convertView.findViewById(R.id.tvName);
		tvBody = (TextView) convertView.findViewById(R.id.tvBody);
		tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
	}

}
