package com.groupon.maygupta.myinstagram;

import android.content.Context;
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
        super(context, android.R.layout.simple_list_item_1, photos);
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

        // Set the text fields
        tvCaption.setText(photo.caption);
        tvLikes.setText(photo.getLikes());
        tvUsername.setText(photo.username);
        tvTime.setText(photo.getCreatedTime());

        // Clear out the image view
        // Insert the image using picasso
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);

        ivUserProfileImage.setImageResource(0);
        Picasso.with(getContext()).load(photo.userProfileImageUrl).into(ivUserProfileImage);

        return convertView;
    }
}
