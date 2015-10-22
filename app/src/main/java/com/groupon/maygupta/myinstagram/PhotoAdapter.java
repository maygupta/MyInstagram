package com.groupon.maygupta.myinstagram;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maygupta on 10/20/15.
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {
    public PhotoAdapter(Context context, List<Photo> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Photo photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        RoundedImageView ivUserProfileImage = (RoundedImageView) convertView.findViewById(R.id.ivUserProfileImage);

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        TextView tvLastComment = (TextView) convertView.findViewById(R.id.tvLastComment);
        TextView tvSecondLastComment = (TextView) convertView.findViewById(R.id.tvSecondLastComment);

        // Set the text fields
        tvCaption.setText(commentToString(photo.username,photo.caption));
        tvLikes.setText(photo.getLikes());
        tvUsername.setText(photo.username);
        tvTime.setText(photo.getCreatedTime());

        if (photo.getLastComment() != null) {
            Comment comment = photo.getLastComment();
            tvLastComment.setText(commentToString(comment.username,comment.text));
            tvLastComment.setVisibility(View.VISIBLE);
        }
        if (photo.getSecondLastComment() != null) {
            Comment comment = photo.getSecondLastComment();
            tvSecondLastComment.setText(commentToString(comment.username,comment.text));
            tvSecondLastComment.setVisibility(View.VISIBLE);
        }

        // Clear out the image view
        // Insert the image using picasso
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.placeholder).into(ivPhoto);

        ivUserProfileImage.setImageResource(0);
        Picasso.with(getContext()).load(photo.userProfileImageUrl).into(ivUserProfileImage);

        return convertView;
    }

    public Spanned commentToString(String username, String text) {
        return Html.fromHtml(username + " " + "<font color=black>" + text + "</font>");
    }
}
