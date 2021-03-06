package com.groupon.maygupta.myinstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maygupta on 10/23/15.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    // View lookup cache
    private static class ViewHolder {
        TextView commentText;
        RoundedImageView commentUser;
        TextView commentTime;
    }

    public CommentAdapter(Context context, List<Comment> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder; // view lookup cache stored in tag
        Comment comment = getItem(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);

            viewHolder.commentText = (TextView) convertView.findViewById(R.id.tvCommentText);
            viewHolder.commentTime = (TextView) convertView.findViewById(R.id.tvCommentTime);
            viewHolder.commentUser = (RoundedImageView) convertView.findViewById(R.id.ivUserImageForComment);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.commentText.setText(Comment.commentToString(comment.username, comment.text));
        viewHolder.commentTime.setText(comment.Time());
        viewHolder.commentUser.setImageResource(0);
        Picasso.with(getContext()).load(comment.userUrl).into(viewHolder.commentUser);

        return convertView;
    }

}
