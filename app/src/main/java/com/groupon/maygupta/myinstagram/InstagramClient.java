package com.groupon.maygupta.myinstagram;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by maygupta on 10/22/15.
 */
public class InstagramClient {

    public static final String CLIENT_ID = "b19ba3b6d52c49cfb8f9df07c46fa441";

    public static void fetchAllComments(String mediaId, final OnClientDataLoad dataListener) {
        String url = String.format("https://api.instagram.com/v1/media/%s/comments?client_id=%s", mediaId, CLIENT_ID);
        AsyncHttpClient client = new AsyncHttpClient();



        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray commentsJSON = null;
                ArrayList<Comment> comments = new ArrayList<Comment>();

                try {
                    commentsJSON = response.getJSONArray("data");
                    Log.i("Debug", commentsJSON.toString());
                    for (int i = 0; i < commentsJSON.length(); i++) {
                        JSONObject commentJSON = commentsJSON.getJSONObject(i);
                        comments.add(new Comment(commentJSON.getJSONObject("from").getString("username") , commentJSON.getString("text")));
                    }
                } catch (JSONException error) {
                    error.printStackTrace();
                }
                dataListener.onCommentsLoad(comments);
            }
        });
    }

    public static void fetchPopularPhotos(final OnClientDataLoad dataListener) {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray photosJSON = null;
                ArrayList<Photo> photos = new ArrayList<Photo>();

                try {
                    photosJSON = response.getJSONArray("data");
                    Log.i("Debug", photosJSON.toString());
                    for ( int i = 0 ; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);

                        // Decode json to Photo model
                        Photo photo = new Photo();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        if ( !photoJSON.isNull("caption") ) {
                            photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        }
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photo.createdTime = photoJSON.getInt("created_time");
                        photo.userProfileImageUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.mediaId = photoJSON.getString("id");

                        // Attach comments to photo
                        JSONArray comments = photoJSON.getJSONObject("comments").getJSONArray("data");

                        // Adding the second last comment first
                        if (comments.length() > 1) {
                            JSONObject commentJSON = comments.getJSONObject(1);
                            Comment comment = new Comment(
                                    commentJSON.getJSONObject("from").getString("username"),
                                    commentJSON.getString("text"));
                            photo.comments.add(comment);
                        }

                        // Adding the last comment
                        if (comments.length() > 0) {
                            JSONObject commentJSON = comments.getJSONObject(0);
                            Comment comment = new Comment(
                                    commentJSON.getJSONObject("from").getString("username"),
                                    commentJSON.getString("text"));
                            photo.comments.add(comment);
                        }

                        // Add photo to the photos list
                        photos.add(photo);
                    }

                } catch (JSONException error) {
                    error.printStackTrace();
                }
                dataListener.onLoad(photos);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // Do something when instagram api fetch fails
                Log.i("DEBUG", "Couldn't connect to Instagram");
            }
        });

    }

}
