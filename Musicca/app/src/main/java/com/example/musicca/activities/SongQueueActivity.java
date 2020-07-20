package com.example.musicca.activities;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
<<<<<<< HEAD
=======
import android.widget.ProgressBar;
=======
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
>>>>>>> Attempt 2: Edit Profile Activity
>>>>>>> Attempt 2: Edit Profile Activity
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.models.Playlist;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
<<<<<<< HEAD
import com.parse.SaveCallback;

import java.util.ArrayList;
=======

>>>>>>> Attempt 2: Edit Profile Activity
import java.util.List;

public class SongQueueActivity extends AppCompatActivity {

<<<<<<< HEAD
    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";

    private static final String TAG = "Queue";
    private List<String> currentPlaylistSongs = new ArrayList<>();
=======
    Playlist currentPlaylist;
    Song currentSong;
>>>>>>> Attempt 2: Edit Profile Activity

    private ImageView ivSongAlbum;
    private TextView tvTitle;
    private TextView tvArtist;
    private Button btnAddSong;
    private Button btnBack;
<<<<<<< HEAD
    private Button btnGoToPlaylist;
=======
    private Button btngotoPlaylist;
>>>>>>> Attempt 2: Edit Profile Activity
    private String albumUrl;
    private String playlistObjectId;
    private String songObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_queue);
<<<<<<< HEAD
        Log.d(TAG, "song activity set up");
=======
>>>>>>> Attempt 2: Edit Profile Activity

        ivSongAlbum = findViewById(R.id.ivSongAlbum);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);
        btnAddSong = findViewById(R.id.btnAddSong);
        btnBack = findViewById(R.id.btnBack);
<<<<<<< HEAD
        btnGoToPlaylist = findViewById(R.id.btnGoToPlaylist);

        songObjectId = getIntent().getStringExtra(EXTRA_SONGOBJECTID);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        Log.d("PLAYLIST SONGQUEUE", playlistObjectId != null ? playlistObjectId : null);

        albumUrl = getIntent().getStringExtra(EXTRA_ALBUMICONURL);
        Glide.with(this).load(albumUrl).into(ivSongAlbum);
        tvTitle.setText(getIntent().getStringExtra(EXTRA_SONGTITLE));
        tvArtist.setText(getIntent().getStringExtra(EXTRA_SONGARTIST));
=======
        btngotoPlaylist = findViewById(R.id.btngotoPlaylist);

        songObjectId = getIntent().getStringExtra("songObjectid");
        playlistObjectId = getIntent().getStringExtra("playlistobjectid");

        albumUrl = getIntent().getStringExtra("albumiconurl");
        Glide.with(this).load(albumUrl).into(ivSongAlbum);
        tvTitle.setText(getIntent().getStringExtra("songtitle"));
        tvArtist.setText(getIntent().getStringExtra("songartist"));

        currentPlaylist = getCurrentPlaylist(playlistObjectId);
        currentSong = getCurrentSong(songObjectId);
>>>>>>> Attempt 2: Edit Profile Activity

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                getCurrentPlaylistSongs(playlistObjectId);
            }
        });
        btnGoToPlaylist.setOnClickListener(new View.OnClickListener() {
=======
                addSong(currentSong);
            }
        });
        btngotoPlaylist.setOnClickListener(new View.OnClickListener() {
>>>>>>> Attempt 2: Edit Profile Activity
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
<<<<<<< HEAD
        i.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
=======
        i.putExtra("playlistobjectid", playlistObjectId);
>>>>>>> Attempt 2: Edit Profile Activity
        startActivity(i);
    }

    private void backToQueue() {
        Intent i = new Intent(this, QueueActivity.class);
<<<<<<< HEAD
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
=======
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
    private Song getCurrentSong(String songobjectid) {
        final Song[] currentsong = new Song[1];
        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(songobjectid, new GetCallback<Song>() {
            @Override
            public void done(Song song, ParseException e) {
                currentsong[0] = song;
            }
        });
        return currentsong[0];
    }

    private void addSong(Song currentsong) {
        List<Song> availableSongs = currentPlaylist.getSongs();
        for (Song song : availableSongs){
            if(songObjectId == song.getObjectId()){
                Toast.makeText(SongQueueActivity.this, "Song already exists in " + currentPlaylist.getName(), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        currentPlaylist.setSong(currentsong);
        Toast.makeText(SongQueueActivity.this, "Song has been added to " + currentPlaylist.getName(), Toast.LENGTH_SHORT).show();
>>>>>>> Attempt 2: Edit Profile Activity
    }
}