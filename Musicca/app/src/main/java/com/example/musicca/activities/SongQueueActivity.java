package com.example.musicca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SongQueueActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";

    private static final String TAG = "Queue";
    private List<String> currentPlaylistSongs = new ArrayList<>();

    private ImageView ivSongAlbum;
    private TextView tvTitle;
    private TextView tvArtist;
    private Button btnAddSong;
    private Button btnBack;
    private Button btnGoToPlaylist;
    private String albumUrl;
    private String playlistObjectId;
    private String songObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_queue);
        Log.d(TAG, "song activity set up");

        ivSongAlbum = findViewById(R.id.ivSongAlbum);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);
        btnAddSong = findViewById(R.id.btnAddSong);
        btnBack = findViewById(R.id.btnBack);
        btnGoToPlaylist = findViewById(R.id.btnGoToPlaylist);

        songObjectId = getIntent().getStringExtra(EXTRA_SONGOBJECTID);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        Log.d("PLAYLIST SONGQUEUE", playlistObjectId != null ? playlistObjectId : null);

        albumUrl = getIntent().getStringExtra(EXTRA_ALBUMICONURL);
        Glide.with(this).load(albumUrl).into(ivSongAlbum);
        tvTitle.setText(getIntent().getStringExtra(EXTRA_SONGTITLE));
        tvArtist.setText(getIntent().getStringExtra(EXTRA_SONGARTIST));

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentPlaylistSongs(playlistObjectId);
            }
        });
        btnGoToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPlaylist();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToQueue();
            }
        });
    }

    private void gotoPlaylist() {
        Intent i = new Intent(this, CurrentPlaylistActivity.class);
        i.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
        startActivity(i);
    }

    private void backToQueue() {
        Intent i = new Intent(this, QueueActivity.class);
        i.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
        startActivity(i);
    }

    private void getCurrentPlaylistSongs(String playlistObjectId) {
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        query.include("songs");
        // Execute the query to find the object with ID
        query.getInBackground(playlistObjectId, new GetCallback<Playlist>() {
            @Override
            public void done(Playlist playlist, com.parse.ParseException e) {
                if (e == null) {
                    Log.d(TAG, "playlist found " + playlist.getName());
                    Log.d(TAG, "playlist found123 " + playlist.getSongList());
                    if (playlist.getSongList() != null) {
                        currentPlaylistSongs = playlist.getSongList();
                    }
                    currentPlaylistSongs.add(songObjectId);
                    playlist.setSongList(currentPlaylistSongs);
                    Log.d(TAG, "playlist found2" + currentPlaylistSongs.size());
                    playlist.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.e(TAG, "Error occurred when adding song", e);
                                Toast.makeText(SongQueueActivity.this, "Error occurred when adding song!", Toast.LENGTH_SHORT).show();
                            }
                            Log.i(TAG, "Post saved successfully!");
                            Toast.makeText(SongQueueActivity.this, "Song has been added to " + playlist.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Log.d(TAG, "playlist not found!");
                }
            }
        });
    }
}