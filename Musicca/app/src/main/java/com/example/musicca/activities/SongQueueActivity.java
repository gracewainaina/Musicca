package com.example.musicca.activities;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Populate newly created playlist
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
<<<<<<< HEAD
=======
import android.widget.ProgressBar;
<<<<<<< HEAD
=======
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
>>>>>>> Attempt 2: Edit Profile Activity
<<<<<<< HEAD
>>>>>>> Attempt 2: Edit Profile Activity
=======
=======
>>>>>>> Populate newly created playlist
>>>>>>> Populate newly created playlist
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
<<<<<<< HEAD
import com.parse.SaveCallback;

import java.util.ArrayList;
=======

>>>>>>> Attempt 2: Edit Profile Activity
=======
import com.parse.SaveCallback;

import org.json.JSONException;

import java.util.ArrayList;
>>>>>>> Populate newly created playlist
import java.util.List;

public class SongQueueActivity extends AppCompatActivity {

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Play song, login error handling, contant string extras for intents
    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";

<<<<<<< HEAD
    private static final String TAG = "Queue";
    private List<String> currentPlaylistSongs = new ArrayList<>();
=======
    Playlist currentPlaylist;
=======
    private static final String TAG = "Queue";
    Playlist currentPlaylist = new Playlist();
    ArrayList<Song> currentPlaylistSongs = new ArrayList<>();
>>>>>>> Populate newly created playlist
    Song currentSong;
>>>>>>> Attempt 2: Edit Profile Activity
=======

    private static final String TAG = "Queue";
    private List<String> currentPlaylistSongs = new ArrayList<>();
>>>>>>> Play song, login error handling, contant string extras for intents

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
<<<<<<< HEAD
        Log.d(TAG, "song activity set up");
=======
>>>>>>> Attempt 2: Edit Profile Activity
=======
        Log.d(TAG, "song activity set up");
>>>>>>> Populate newly created playlist

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

        songObjectId = getIntent().getStringExtra(EXTRA_SONGOBJECTID);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        Log.d("PLAYLIST SONGQUEUE", playlistObjectId != null ? playlistObjectId : null);

        albumUrl = getIntent().getStringExtra(EXTRA_ALBUMICONURL);
        Glide.with(this).load(albumUrl).into(ivSongAlbum);
<<<<<<< HEAD
        tvTitle.setText(getIntent().getStringExtra("songtitle"));
        tvArtist.setText(getIntent().getStringExtra("songartist"));
<<<<<<< HEAD

        currentPlaylist = getCurrentPlaylist(playlistObjectId);
        currentSong = getCurrentSong(songObjectId);
>>>>>>> Attempt 2: Edit Profile Activity
=======
=======
        tvTitle.setText(getIntent().getStringExtra(EXTRA_SONGTITLE));
        tvArtist.setText(getIntent().getStringExtra(EXTRA_SONGARTIST));
>>>>>>> Play song, login error handling, contant string extras for intents
        //currentSong = getCurrentSong(songObjectId);
//        Log.d("CURRENT PLAYLIST", currentPlaylist.getName());
>>>>>>> Populate newly created playlist

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
<<<<<<< HEAD
                getCurrentPlaylistSongs(playlistObjectId);
            }
        });
        btnGoToPlaylist.setOnClickListener(new View.OnClickListener() {
=======
                addSong(currentSong);
=======
                getCurrentPlaylistSongs(playlistObjectId);
>>>>>>> Populate newly created playlist
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
=======
        i.putExtra("playlistobjectid", playlistObjectId);
>>>>>>> Play song, login error handling, contant string extras for intents
        startActivity(i);
    }

    private void getCurrentPlaylistSongs(String playlistobjectid) {
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // First try to find from the cache and only then go to network
        // query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        query.include("songs");
        // Execute the query to find the object with ID
        query.getInBackground(playlistobjectid, new GetCallback<Playlist>() {
            @Override
            public void done(Playlist playlist, com.parse.ParseException e) {
                if (e == null) {
                    Log.d(TAG, "playlist found " + playlist.getName());
                    Log.d(TAG, "playlist found123 " + playlist.getSongList());
                    if (playlist.getSongList() != null){
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

                }
                else{
                    Log.d(TAG, "playlist not found!");
                }
            }
        });
    }
<<<<<<< HEAD
    private Song getCurrentSong(String songobjectid) {
        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);
        query.whereEqualTo("objectId", songobjectid);
        try {
            return query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

        // First try to find from the cache and only then go to network
//        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
//        // Execute the query to find the object with ID
//        query.getInBackground(songobjectid, new GetCallback<Song>() {
//            @Override
//            public void done(Song song, ParseException e) {
//                if (e == null) {
//                    currentSong.
//                    Log.d(TAG, "song found!");
//                }
//                else{
//                    Log.d(TAG, "song not found!");
//                }
//            }
//        });

<<<<<<< HEAD
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
=======
>>>>>>> Populate newly created playlist
    }
//    private void addSong(Song currentsong) {
//        List<Song> songList = currentPlaylist.getSongList();
//        songList.add(currentsong);
//        currentPlaylist.setSongList(songList);
//        currentPlaylist.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null) {
//                    Log.e(TAG, "Error while saving", e);
//                    Toast.makeText(SongQueueActivity.this, "Error while updating playlist!", Toast.LENGTH_SHORT).show();
//                }
//                Log.i(TAG, "Playlist updated successfully!");
//                Toast.makeText(SongQueueActivity.this, "Song has been added", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
=======
>>>>>>> Play song, login error handling, contant string extras for intents
}