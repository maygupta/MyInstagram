package com.groupon.maygupta.myinstagram;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by maygupta on 10/22/15.
 */
public class Comment {
    public String username;
    public String text;
    public String userUrl;
    public long time;

    public Comment(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public Comment(String username, String text, String userUrl, long time) {
        this.username = username;
        this.text = text;
        this.userUrl = userUrl;
        this.time = time;
    }

    public static Spanned commentToString(String username, String text) {
        return Html.fromHtml(username + " " + "<font color=black>" + text + "</font>");
    }

    public String Time() {
        String timePresenter = "";

        long currentTime = System.currentTimeMillis();
        long postedAt = currentTime - time * 1000;
        int hours = (int) ((postedAt / (1000*60*60)) % 24);
        int minutes = (int) ((postedAt / (1000*60)) % 60);
        int days = (int) (hours / 24);
        int weeks = (int) (days / 7);

        if (weeks > 0) {
            timePresenter = getSingularOrPlural(weeks, "week");
        } else if ( days > 0 ){
            timePresenter = getSingularOrPlural(days, "day");
        } else if (hours > 0) {
            timePresenter = getSingularOrPlural(hours, "hour");
        } else if ( minutes > 0) {
            timePresenter = getSingularOrPlural(minutes, "min");
        } else  {
            timePresenter = "Just now";
        }
        return timePresenter;
    }

    private String getSingularOrPlural(int count, String key) {
        if (count > 1) {
            return count + " " + key + "s" + " ago";
        }
        return count + " " + key + " ago";
    }
}
