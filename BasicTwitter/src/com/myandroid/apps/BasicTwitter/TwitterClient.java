package com.myandroid.apps.BasicTwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myandroid.apps.BasicTwitter.models.Tweet;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "KEThQon7NLOBrrBiRMCaz7ECE";       // Change this
    public static final String REST_CONSUMER_SECRET = "QYYULdqt4iHBlxOFdGNmas2dnCWFV95S0QchUW79EH4qKDBske"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpbasictweet"; // Change this (here and in manifest)
    public static final String GET_HOME_TIMELINE = "statuses/home_timeline.json";
    public static final String POST_TWEET = "statuses/update.json";
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(long maxid, int count, long sinceid, AsyncHttpResponseHandler handler) {
    	String apiUrl = getApiUrl(GET_HOME_TIMELINE);
    	RequestParams params = new RequestParams();
    	if (maxid > 0)
    		params.put("max_id", maxid + "");
    	params.put("count", count + "");
    	if (sinceid > 0)
    		params.put("since_id", sinceid + "");
    	
    	Log.w("CLIENT", params.toString() + " ");
    	client.get(apiUrl, params, handler); //pass null for params if no params
    	
    }
    
    public void postTweet(String tweetText) {
    	String apiUrl = getApiUrl(POST_TWEET);
    	RequestParams params = new RequestParams();
    	params.put("status", tweetText);
    	client.post(apiUrl, params, new AsyncHttpResponseHandler());
    }
    
}