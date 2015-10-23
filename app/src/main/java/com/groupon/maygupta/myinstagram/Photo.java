package com.groupon.maygupta.myinstagram;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by maygupta on 10/20/15.
 */
public class Photo {

    public String username;
    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;
    public long createdTime;
    public String userProfileImageUrl;
    public ArrayList<Comment> comments;
    public String mediaId;

    public String getLikes() {
        return "  " + NumberFormat.getNumberInstance(Locale.US).format(likesCount) + " likes";
    }

    public Photo(){
        comments = new ArrayList<>();
    }

    public String getCreatedTime() {
        String timePresenter = "";

        long time = System.currentTimeMillis();
        long postedAt = time - createdTime*1000;
        int hours = (int) ((postedAt / (1000*60*60)) % 24);
        int minutes = (int) ((postedAt / (1000*60)) % 60);
        int seconds = (int) ((postedAt / (1000)) % 60);
        int days = (int) (hours / 24);
        int weeks = (int) (days / 7);

        if (weeks > 0) {
          timePresenter = weeks + "w";
        } else if ( days > 0 ){
            timePresenter = days + "d";
        } else if (hours > 0) {
            timePresenter = hours + "h";
        } else if ( minutes > 0) {
            timePresenter = minutes + "m";
        } else  {
            timePresenter = "Just now";
        }
        return " " + timePresenter;
    }

    public Comment getLastComment(){
        if(comments.size() > 0) {
            return comments.get(0);
        } else {
            return null;
        }
    }

    public Comment getSecondLastComment(){
        if (comments.size() > 1) {
            return comments.get(1);
        }else {
            return null;
        }
    }

}
