package com.myandroid.apps.BasicTwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "User")
public class User extends Model implements Serializable {

	private static final long serialVersionUID = 5863104542021558627L;
	@Column(name = "Name")
	private String name;
	@Column(name = "User_Id", unique=true, onUniqueConflict = Column.ConflictAction.IGNORE)
	private long userId;
	@Column(name = "Handle")
	private String handle;
	@Column(name = "Profile_img_url")
	private String profileImgUrl;
	@Column(name = "Followers")
	private long followers;
	@Column(name = "Following")
	private long following = 0;
	@Column(name = "Tagline")
	private String tagline;
	
	public User() {
		super();
	}

	public static User fromJson(JSONObject json) {
		User user = new User();
		try {
			user.name = json.getString("name");
			user.userId = json.getLong("id");
			user.handle = "@" + json.getString("screen_name");
			user.profileImgUrl = json.getString("profile_image_url");
			user.followers = json.getLong("followers_count");
			user.following = json.getLong("friends_count");
			user.tagline = json.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		user.save();
		return user;
	}

	public String getName() {
		return name;
	}

	public long getUserId() {
		return userId;
	}

	public String getHandle() {
		return handle;
	}

	public String getProfileImgUrl() {
		return profileImgUrl;
	}

	public long getNumFollowers() {
		return followers;
	}

	public long getNumFollowing() {
		return following;
	}
	
	public String getTagline() {
		return tagline;
	}
	
	/*public User getUser(long userId) {
		
	}*/
	
}
