package com.example.musicca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
<<<<<<< HEAD

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
=======
<<<<<<< HEAD
import android.widget.Toast;
=======
>>>>>>> Attempt 2: Edit Profile Activity
>>>>>>> Attempt 2: Edit Profile Activity

import com.example.musicca.R;
import com.example.musicca.adapters.CurrentPlaylistAdapter;
import com.example.musicca.models.Playlist;
import com.parse.GetCallback;
<<<<<<< HEAD
import com.parse.ParseQuery;
=======
<<<<<<< HEAD
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONException;
=======
import com.parse.ParseQuery;

>>>>>>> Attempt 2: Edit Profile Activity
import org.w3c.dom.Text;
>>>>>>> Attempt 2: Edit Profile Activity

import java.util.List;

public class CurrentPlaylistActivity extends AppCompatActivity {

    private static final String TAG = "CurrentPlaylistActivity";
<<<<<<< HEAD
    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
=======
>>>>>>> Attempt 2: Edit Profile Activity

    private TextView tvPlaylistTitle;
    private RecyclerView rvPlaylistSongs;
    private Button btnAddMoreSongs;
    private String playlistObjectId;
    private CurrentPlaylistAdapter currentPlaylistAdapter;
<<<<<<< HEAD
    private List<String> currentPlaylistSongs;
=======
    protected List<Song> songsInPlaylist;
    private Playlist currentPlaylist;


>>>>>>> Attempt 2: Edit Profile Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_playlist);

        tvPlaylistTitle = findViewById(R.id.tvPlaylistTitle);
        rvPlaylistSongs = findViewById(R.id.rvPlaylistSongs);
        btnAddMoreSongs = findViewById(R.id.btnAddMoreSongs);
<<<<<<< HEAD
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        Log.d("PLAYLIST CURRENT objid ", playlistObjectId != null ? playlistObjectId : null);

        getCurrentPlaylistSongs(playlistObjectId);
=======
        playlistObjectId = getIntent().getStringExtra("playlistobjectid");

>>>>>>> Attempt 2: Edit Profile Activity
        btnAddMoreSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoQueueActivity();
            }
        });
<<<<<<< HEAD
=======

        currentPlaylist = getCurrentPlaylist(playlistObjectId);
        songsInPlaylist = currentPlaylist.getSongs();
        if (songsInPlaylist != null){
            currentPlaylistAdapter = new CurrentPlaylistAdapter(this, songsInPlaylist, playlistObjectId);
            rvPlaylistSongs.setAdapter(currentPlaylistAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvPlaylistSongs.setLayoutManager(linearLayoutManager);
        }
>>>>>>> Attempt 2: Edit Profile Activity
    }

    private void gotoQueueActivity() {
        Intent i = new Intent(this, QueueActivity.class);
<<<<<<< HEAD
        i.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
        startActivity(i);
    }

    private void getCurrentPlaylistSongs(String playlistobjectid) {
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // First try to find from the cache and only then go to network
        // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
=======
        i.putExtra("playlistobjectid", playlistObjectId);
        startActivity(i);
    }

    private Playlist getCurrentPlaylist(String playlistobjectid) {
        final Playlist[] currentplaylist = new Playlist[1];
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
>>>>>>> Attempt 2: Edit Profile Activity
        // Execute the query to find the object with ID
        query.getInBackground(playlistobjectid, new GetCallback<Playlist>() {
            @Override
            public void done(Playlist playlist, com.parse.ParseException e) {
<<<<<<< HEAD
                if (e == null) {
                    Log.d(TAG, "playlist found " + playlist.getName());
                    tvPlaylistTitle.setText(playlist.getName());
                    if (playlist.getSongList() != null) {
                        currentPlaylistSongs = playlist.getSongList();
                        Log.d("playlist CURRENT size1", "SIZE OF" + currentPlaylistSongs.size());
                    }
                    currentPlaylistAdapter = new CurrentPlaylistAdapter(CurrentPlaylistActivity.this, currentPlaylistSongs, playlistObjectId);
                    rvPlaylistSongs.setAdapter(currentPlaylistAdapter);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CurrentPlaylistActivity.this);
                    rvPlaylistSongs.setLayoutManager(linearLayoutManager);
                } else {
                    Log.d(TAG, "playlist not found!");
                }
            }
        });

=======
                currentplaylist[0] = playlist;
            }
        });
        return currentplaylist[0];
>>>>>>> Attempt 2: Edit Profile Activity
    }
}