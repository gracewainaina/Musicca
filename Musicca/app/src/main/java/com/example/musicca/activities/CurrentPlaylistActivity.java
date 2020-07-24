package com.example.musicca.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.musicca.R;
import com.example.musicca.adapters.CurrentPlaylistAdapter;
import com.example.musicca.adapters.QueueAdapter;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.GetCallback;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CurrentPlaylistActivity extends AppCompatActivity {

    private static final String TAG = "CurrentPlaylistActivity";

    private TextView tvPlaylistTitle;
    private RecyclerView rvPlaylistSongs;
    private Button btnAddMoreSongs;
    private String playlistObjectId;
    private CurrentPlaylistAdapter currentPlaylistAdapter;
    protected List<Song> songsInPlaylist;
    private Playlist currentPlaylist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_playlist);

        tvPlaylistTitle = findViewById(R.id.tvPlaylistTitle);
        rvPlaylistSongs = findViewById(R.id.rvPlaylistSongs);
        btnAddMoreSongs = findViewById(R.id.btnAddMoreSongs);
        playlistObjectId = getIntent().getStringExtra("playlistobjectid");

        btnAddMoreSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoQueueActivity();
            }
        });

        currentPlaylist = getCurrentPlaylist(playlistObjectId);
        songsInPlaylist = currentPlaylist.getSongs();
        if (songsInPlaylist != null){
            currentPlaylistAdapter = new CurrentPlaylistAdapter(this, songsInPlaylist, playlistObjectId);
            rvPlaylistSongs.setAdapter(currentPlaylistAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvPlaylistSongs.setLayoutManager(linearLayoutManager);
        }
    }

    private void gotoQueueActivity() {
        Intent i = new Intent(this, QueueActivity.class);
        i.putExtra("playlistobjectid", playlistObjectId);
        startActivity(i);
    }

    private Playlist getCurrentPlaylist(String playlistobjectid) {
        final Playlist[] currentplaylist = new Playlist[1];
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(playlistobjectid, new GetCallback<Playlist>() {
            @Override
            public void done(Playlist playlist, com.parse.ParseException e) {
                currentplaylist[0] = playlist;
            }
        });
        return currentplaylist[0];
    }
}