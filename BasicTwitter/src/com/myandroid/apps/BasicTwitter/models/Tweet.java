package com.myandroid.apps.BasicTwitter.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable {

	private static final long serialVersionUID = 8880600876124354628L;
	private String body;
	private long id;
	private String createdAt;
	private User user;
	String LARGE_TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";

	public static Tweet fromJson(JSONObject json) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = json.getString("text");
			tweet.id = json.getLong("id");
			tweet.createdAt = json.getString("created_at");
			tweet.user = User.fromJson(json.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return tweet;
	}

	public String getBody() {
		return body;
	}

	public long getId() {
		return id;
	}

	public String getCreatedAt(boolean isDetail) {
		if (isDetail)
			return createdAt;
		return formatDate();
	}

	public User getUser() {
		return user;
	}

	/*@Override
	public String toString() {
		return getUser() + getBody();
	}*/

	public static List<Tweet> fromJsonArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		for (int i = 0; i < json.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null)
				tweets.add(tweet);
		}
		return tweets;
	}
	
	private String formatDate() {
		Date date = new Date();
		Date tweetDate;
		//Tue Aug 28 21:16:23 +0000 2012
		try {
			tweetDate = new SimpleDateFormat(LARGE_TWITTER_DATE_FORMAT, Locale.ENGLISH)
			       .parse(createdAt);
		} catch (ParseException e) {
			e.printStackTrace();
			return createdAt;
		}
		long diff = date.getTime() - tweetDate.getTime();
		//convert milliseconds to num hours/days/months
		int seconds, minutes = 0, hours = 0, days = 0;
		seconds = (int) (diff / 1000);
		if (seconds > 60) {
			minutes = seconds/60;
			//seconds = seconds % 60;
		}
		if (minutes > 60) {
			hours = minutes/60;
			//minutes = minutes % 60;
		}
		if (hours > 24) {
			days = hours/24;
			//hours = hours % 24;
		}
		String since = seconds + " s";
		if (seconds < 0)
			since = "just now"; //account for system time mismatch by +/- 1-3 seconds
		since = (minutes > 0 ? minutes + " m": since);
		since = (hours > 0 ? hours + " h": since);
		since = (days > 0 ? days + " d": since);
		return since;
	}
}
