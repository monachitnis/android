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

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Tweet")
public class Tweet extends Model implements Serializable {
	
	public enum Category {
		HOME_TWEETS, MENTIONS_TWEETS, USER_TWEETS
	}

	private static final long serialVersionUID = 8880600876124354628L;
	@Column(name = "Body")
	private String body;
	@Column(name = "Tweet_Id", unique=true, onUniqueConflict = Column.ConflictAction.IGNORE)
	private long tweetId;
	@Column(name = "Timestamp")
	private String createdAt;
	@Column(name = "T_user", onUpdate = ForeignKeyAction.CASCADE)
	private User user;
	@Column(name = "Category")
	private Category category;
	String LARGE_TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
	
	public Tweet() {
		super();
	}

	public static Tweet fromJson(JSONObject json, Category category) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = json.getString("text");
			tweet.tweetId = json.getLong("id");
			tweet.createdAt = json.getString("created_at");
			tweet.user = User.fromJson(json.getJSONObject("user"));
			tweet.category = category;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		tweet.save();
		return tweet;
	}

	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}

	public long getTweetId() {
		return tweetId;
	}

	public String getCreatedAt(boolean isDetail) {
		if (isDetail)
			return createdAt;
		return formatDate();
	}
	
	public void setCreatedAt(String time) {
		this.createdAt = time;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User u) {
		this.user = u;
	}

	public static List<Tweet> fromJsonArray(JSONArray json, Category category) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		for (int i = 0; i < json.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			Tweet tweet = Tweet.fromJson(tweetJson, category);
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
		int seconds, minutes = 0, hours = 0, days = 0, mnths = 0, yrs = 0;
		seconds = (int) (diff / 1000);
		if (seconds > 60) {
			minutes = seconds/60;
		}
		if (minutes > 60) {
			hours = minutes/60;
		}
		if (hours > 24) {
			days = hours/24;
		}
		if (days > 30) {
			mnths = days/30;
		}
		if (mnths > 12) {
			yrs = mnths/12;
		}
		String since = seconds + " s";
		if (seconds < 0)
			since = "just now"; //account for system time mismatch by +/- 1-3 seconds
		since = (minutes > 0 ? minutes + " m": since);
		since = (hours > 0 ? hours + " h": since);
		since = (days > 0 ? days + " d": since);
		since = (mnths > 0 ? mnths + " mth": since);
		since = (yrs > 0 ? yrs + " yr": since);
		return since;
	}
	
	public static List<Tweet> getAll(Category category) {
		List<Tweet> res = new Select()
	        .from(Tweet.class)
	        .where("Category = ?", category)
	        .orderBy("Timestamp DESC")
	        .execute();
		return res;
	}
}
