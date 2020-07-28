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
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> resolved conflict in modify-playlist branch
=======
>>>>>>> 4302c40a9d6fb615fc2dacc4f1b801f1fdb34b4d

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
>>>>>>> resolved conflict in modify-playlist branch
=======
>>>>>>> 4302c40a9d6fb615fc2dacc4f1b801f1fdb34b4d

import com.example.musicca.R;
import com.example.musicca.adapters.CurrentPlaylistAdapter;
import com.example.musicca.models.Playlist;
import com.parse.GetCallback;
<<<<<<< HEAD
<<<<<<< HEAD
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

=======
import com.parse.ParseQuery;

>>>>>>> resolved conflict in modify-playlist branch
=======
import com.parse.ParseQuery;

>>>>>>> 4302c40a9d6fb615fc2dacc4f1b801f1fdb34b4d
import java.util.List;

public class CurrentPlaylistActivity extends AppCompatActivity {

    private static final String TAG = "CurrentPlaylistActivity";
    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";

    private TextView tvPlaylistTitle;
    private RecyclerView rvPlaylistSongs;
    private Button btnAddMoreSongs;
    private String playlistObjectId;
    private CurrentPlaylistAdapter currentPlaylistAdapter;
    private List<String> currentPlaylistSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_playlist);

        tvPlaylistTitle = findViewById(R.id.tvPlaylistTitle);
        rvPlaylistSongs = findViewById(R.id.rvPlaylistSongs);
        btnAddMoreSongs = findViewById(R.id.btnAddMoreSongs);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        Log.d("PLAYLIST CURRENT objid ", playlistObjectId != null ? playlistObjectId : null);

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
        startActivity(i);
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

    }
}