package com.groupon.maygupta.myinstagram;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "b19ba3b6d52c49cfb8f9df07c46fa441";
    private ArrayList<Photo> photos;
    private PhotoAdapter adapter;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initEmptyPhotos();
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // Initialize array list of photos which will be populated in fetch call
        photos = new ArrayList<>();

        // Set adapter for List view
        adapter = new PhotoAdapter(this, photos);

        ListView listPhotos = (ListView) findViewById(R.id.lvPhotos);
        listPhotos.setAdapter(adapter);

        // Load photos from Instagram finally
        fetchPopularPhotos();
    }

    private void initEmptyPhotos() {
        photos.clear();
    }

    private void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray photosJSON = null;

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

                        // Attach comments to photo
                        JSONArray comments = photoJSON.getJSONObject("comments").getJSONArray("data");
                        if (comments.length() > 0) {
                            JSONObject comment = comments.getJSONObject(0);
                            photo.comments.add(String.format("%s : %s",comment.getJSONObject("from").getString("username"),comment.getString("text")));
                        }
                        if (comments.length() > 1) {
                            JSONObject comment = comments.getJSONObject(1);
                            photo.comments.add(String.format("%s : %s", comment.getJSONObject("from").getString("username"), comment.getString("text")));
                        }
                        // Add photo to the photos list
                        photos.add(photo);
                    }

                } catch (JSONException error) {
                    error.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // Do something when instagram api fetch fails
                Log.i("DEBUG", "Couldn't connect to Instagram");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
