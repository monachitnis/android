package com.myandroid.apps.BasicTwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {

	private static final long serialVersionUID = 5863104542021558627L;
	private String name;
	private long id;
	private String handle;
	private String profileImgUrl;

	public static User fromJson(JSONObject json) {
		User user = new User();
		try {
			user.name = json.getString("name");
			user.id = json.getLong("id");
			user.handle = "@" + json.getString("screen_name");
			user.profileImgUrl = json.getString("profile_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public String getHandle() {
		return handle;
	}

	public String getProfileImgUrl() {
		return profileImgUrl;
	}
	
	/*@Override
	public String toString() {
		return getName() + getProfileImgUrl();
	}*/

}
