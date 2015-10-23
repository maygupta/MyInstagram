package com.groupon.maygupta.myinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by maygupta on 10/23/15.
 */
public class CommentActivity extends AppCompatActivity {

    ArrayList<Comment> comments;
    OnClientDataLoad dataListener;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);

        String mediaId = getIntent().getStringExtra("mediaId");
        comments = new ArrayList<>();

        adapter = new CommentAdapter(this, comments);

        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(adapter);

        dataListener = new OnClientDataLoad() {
            public void onCommentsLoad(ArrayList<Comment> commentsList){
                for(Comment comment:commentsList) {
                    comments.add(comment);
                }
                adapter.notifyDataSetChanged();
            }
        };

        InstagramClient.fetchAllComments(mediaId, dataListener);
    }
}
