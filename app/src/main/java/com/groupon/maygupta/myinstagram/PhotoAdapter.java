package com.groupon.maygupta.myinstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);

        // Set the caption
        tvCaption.setText(photo.caption);

        // Clear out the image view
        // Insert the image using picasso
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);

        return convertView;
    }
}
