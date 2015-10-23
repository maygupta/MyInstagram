package com.groupon.maygupta.myinstagram;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by maygupta on 10/22/15.
 */
public class Comment {
    public String username;
    public String text;

    public Comment(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public static Spanned commentToString(String username, String text) {
        return Html.fromHtml(username + " " + "<font color=black>" + text + "</font>");
    }
}
