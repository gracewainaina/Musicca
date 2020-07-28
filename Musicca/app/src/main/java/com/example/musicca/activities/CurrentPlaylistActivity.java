package com.example.musicca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
=======
=======
>>>>>>> Populate newly created playlist
=======
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"
<<<<<<< HEAD
=======
>>>>>>> Revert "Revert "Merge pull request #20 from gracewainaina/modify-playlist""
import android.widget.Toast;
=======
>>>>>>> Attempt 2: Edit Profile Activity
<<<<<<< HEAD
>>>>>>> Attempt 2: Edit Profile Activity
=======
=======
import android.widget.Toast;
>>>>>>> Populate newly created playlist
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> Populate newly created playlist
=======
=======
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"
=======
>>>>>>> Revert "Revert "Merge pull request #20 from gracewainaina/modify-playlist""

import com.example.musicca.R;
import com.example.musicca.adapters.CurrentPlaylistAdapter;
import com.example.musicca.models.Playlist;
import com.parse.GetCallback;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import com.parse.ParseQuery;
=======
>>>>>>> Revert "Revert "Merge pull request #20 from gracewainaina/modify-playlist""
=======
<<<<<<< HEAD
=======
=======
>>>>>>> Revert "Merge pull request #20 from gracewainaina/modify-playlist"
<<<<<<< HEAD
=======
>>>>>>> Populate newly created playlist
>>>>>>> Populate newly created playlist
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONException;
<<<<<<< HEAD
=======
import com.parse.ParseQuery;

>>>>>>> Attempt 2: Edit Profile Activity
=======
>>>>>>> Play song, login error handling, contant string extras for intents
import org.w3c.dom.Text;
>>>>>>> Attempt 2: Edit Profile Activity

import java.util.List;

public class CurrentPlaylistActivity extends AppCompatActivity {

    private static final String TAG = "CurrentPlaylistActivity";
<<<<<<< HEAD
<<<<<<< HEAD
    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
=======
>>>>>>> Attempt 2: Edit Profile Activity
=======
    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
>>>>>>> Play song, login error handling, contant string extras for intents

    private TextView tvPlaylistTitle;
    private RecyclerView rvPlaylistSongs;
    private Button btnAddMoreSongs;
    private String playlistObjectId;
    private CurrentPlaylistAdapter currentPlaylistAdapter;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    private List<String> currentPlaylistSongs;
=======
    protected List<Song> songsInPlaylist;
    private Playlist currentPlaylist;


>>>>>>> Attempt 2: Edit Profile Activity
=======
    ArrayList<Song> currentPlaylistSongs = new ArrayList<>();
>>>>>>> Populate newly created playlist
=======
    private List<String> currentPlaylistSongs;
>>>>>>> Play song, login error handling, contant string extras for intents

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_playlist);

        tvPlaylistTitle = findViewById(R.id.tvPlaylistTitle);
        rvPlaylistSongs = findViewById(R.id.rvPlaylistSongs);
        btnAddMoreSongs = findViewById(R.id.btnAddMoreSongs);
<<<<<<< HEAD
<<<<<<< HEAD
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        Log.d("PLAYLIST CURRENT objid ", playlistObjectId != null ? playlistObjectId : null);

        getCurrentPlaylistSongs(playlistObjectId);
=======
        playlistObjectId = getIntent().getStringExtra("playlistobjectid");
=======
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
>>>>>>> Play song, login error handling, contant string extras for intents
        Log.d("PLAYLIST CURRENT objid ", playlistObjectId != null ? playlistObjectId : null);

<<<<<<< HEAD
>>>>>>> Attempt 2: Edit Profile Activity
=======
        getCurrentPlaylistSongs(playlistObjectId);
>>>>>>> Populate newly created playlist
        btnAddMoreSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoQueueActivity();
            }
        });
<<<<<<< HEAD
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
=======
>>>>>>> Populate newly created playlist
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

    private void getCurrentPlaylistSongs(String playlistobjectid) {
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // First try to find from the cache and only then go to network
<<<<<<< HEAD
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
>>>>>>> Attempt 2: Edit Profile Activity
=======
        // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
>>>>>>> Play song, login error handling, contant string extras for intents
        // Execute the query to find the object with ID
        query.getInBackground(playlistobjectid, new GetCallback<Playlist>() {
            @Override
            public void done(Playlist playlist, com.parse.ParseException e) {
<<<<<<< HEAD
<<<<<<< HEAD
                if (e == null) {
                    Log.d(TAG, "playlist found " + playlist.getName());
=======
                if (e == null) {
<<<<<<< HEAD
                    Log.d(TAG, "playlist found" + playlist.getName());
>>>>>>> Populate newly created playlist
=======
                    Log.d(TAG, "playlist found " + playlist.getName());
>>>>>>> Play song, login error handling, contant string extras for intents
                    tvPlaylistTitle.setText(playlist.getName());
                    if (playlist.getSongList() != null) {
                        currentPlaylistSongs = playlist.getSongList();
                        Log.d("playlist CURRENT size1", "SIZE OF" + currentPlaylistSongs.size());
                    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Play song, login error handling, contant string extras for intents
                    currentPlaylistAdapter = new CurrentPlaylistAdapter(CurrentPlaylistActivity.this, currentPlaylistSongs, playlistObjectId);
                    rvPlaylistSongs.setAdapter(currentPlaylistAdapter);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CurrentPlaylistActivity.this);
                    rvPlaylistSongs.setLayoutManager(linearLayoutManager);
<<<<<<< HEAD
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
=======
=======
>>>>>>> Play song, login error handling, contant string extras for intents
                } else {
                    Log.d(TAG, "playlist not found!");
                }
            }
        });

<<<<<<< HEAD
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPlaylistSongs.setLayoutManager(linearLayoutManager);
>>>>>>> Populate newly created playlist
=======
>>>>>>> Play song, login error handling, contant string extras for intents
    }
}