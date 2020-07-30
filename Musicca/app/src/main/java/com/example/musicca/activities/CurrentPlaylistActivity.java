package com.example.musicca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.musicca.R;
import com.example.musicca.adapters.CurrentPlaylistAdapter;
import com.example.musicca.models.Playlist;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class CurrentPlaylistActivity extends AppCompatActivity {

    private static final String TAG = "CurrentPlaylistActivity";
    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";

    private TextView tvPlaylistTitle;
    private RecyclerView rvPlaylistSongs;
    private Button btnAddMoreSongs;
    private String playlistObjectId;
    private CurrentPlaylistAdapter currentPlaylistAdapter;
    private List<String> currentPlaylistSongs;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_playlist);

        Transition transitionEnter = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
        getWindow().setEnterTransition(transitionEnter);

        Transition transitionExit = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
        getWindow().setExitTransition(transitionExit);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    currentPlaylistAdapter.updateSongs();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                swipeContainer.setRefreshing(false);
                currentPlaylistAdapter.notifyDataSetChanged();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        tvPlaylistTitle = findViewById(R.id.tvPlaylistTitle);
        rvPlaylistSongs = findViewById(R.id.rvPlaylistSongs);
        btnAddMoreSongs = findViewById(R.id.btnAddMoreSongs);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);

        getCurrentPlaylistSongs(playlistObjectId);
        btnAddMoreSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoQueueActivity();
            }
        });
    }

    private void gotoQueueActivity() {
        Intent i = new Intent(this, QueueActivity.class);
        i.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);

        // options need to be passed when starting the activity
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CurrentPlaylistActivity.this);
        startActivity(i, options.toBundle());

//        startActivity(i);
    }

    private void getCurrentPlaylistSongs(String playlistobjectid) {
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // First try to find from the cache and only then go to network
        // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(playlistobjectid, new GetCallback<Playlist>() {
            @Override
            public void done(Playlist playlist, com.parse.ParseException e) {
                if (e == null) {
                    tvPlaylistTitle.setText(playlist.getName());
                    if (playlist.getSongList() != null) {
                        currentPlaylistSongs = playlist.getSongList();
                    }
                    currentPlaylistAdapter = new CurrentPlaylistAdapter(CurrentPlaylistActivity.this, currentPlaylistSongs, playlistObjectId);
                    rvPlaylistSongs.setAdapter(currentPlaylistAdapter);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CurrentPlaylistActivity.this);
                    rvPlaylistSongs.setLayoutManager(linearLayoutManager);
                    Toast.makeText(CurrentPlaylistActivity.this, "Double tap song like or unlike it!", Toast.LENGTH_SHORT).show();
                    rvPlaylistSongs.setItemAnimator(new SlideInUpAnimator());

                } else {
                    Log.d(TAG, "playlist not found!");
                }
            }
        });

    }
}