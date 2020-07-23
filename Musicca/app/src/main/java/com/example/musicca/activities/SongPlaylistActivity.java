package com.example.musicca.activities;

<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Play song, login error handling, contant string extras for intents
import android.content.Context;
>>>>>>> Attempt 2: Edit Profile Activity
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
<<<<<<< HEAD
=======
import android.content.Intent;
import android.os.Bundle;
>>>>>>> Attempt 2: Edit Profile Activity
=======
>>>>>>> Play song, login error handling, contant string extras for intents
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
<<<<<<< HEAD
<<<<<<< HEAD

import androidx.appcompat.app.AppCompatActivity;
=======
=======
>>>>>>> Play song, login error handling, contant string extras for intents
<<<<<<< HEAD
import android.widget.Toast;
=======
>>>>>>> Attempt 2: Edit Profile Activity
<<<<<<< HEAD
>>>>>>> Attempt 2: Edit Profile Activity
=======
=======
import android.widget.Toast;
>>>>>>> Play song, login error handling, contant string extras for intents
>>>>>>> Play song, login error handling, contant string extras for intents

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.models.Song;
import com.parse.GetCallback;
import com.parse.ParseQuery;
<<<<<<< HEAD
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> Play song, login error handling, contant string extras for intents
>>>>>>> Play song, login error handling, contant string extras for intents
import com.parse.SaveCallback;
>>>>>>> Attempt 2: Edit Profile Activity
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
<<<<<<< HEAD

public class SongPlaylistActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";


    private static final String TAG = "Play Song";
=======

public class SongPlaylistActivity extends AppCompatActivity {

    Playlist currentPlaylist;
    Song currentSong;
>>>>>>> Attempt 2: Edit Profile Activity
=======

public class SongPlaylistActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String EXTRA_SONGOBJECTID = "songObjectid";
    private static final String EXTRA_ALBUMICONURL = "albumiconurl";
    private static final String EXTRA_SONGTITLE = "songtitle";
    private static final String EXTRA_SONGARTIST = "songartist";



    private static final String TAG = "Play Song";
>>>>>>> Play song, login error handling, contant string extras for intents

    private ImageView ivSongAlbum;
    private TextView tvTitle;
    private TextView tvArtist;
    private ImageView ivPrevious;
    private ImageView ivPlayPause;
    private ImageView ivNext;
    private Button btnReturnPlaylist;

    private String albumUrl;
    private String playlistObjectId;
    private String songObjectId;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Play song, login error handling, contant string extras for intents
    private static final String CLIENT_ID = "22793b7728c54470b8d117506f9574c5";
    private static final String REDIRECT_URI = "com.musicca://callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    private static final int REQUEST_CODE = 1337;

<<<<<<< HEAD
=======
>>>>>>> Attempt 2: Edit Profile Activity
=======
>>>>>>> Play song, login error handling, contant string extras for intents
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playlist);

        ivSongAlbum = findViewById(R.id.ivSongAlbum);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> Play song, login error handling, contant string extras for intents
        btnReturnPlaylist = findViewById(R.id.btnReturnPlaylist);

        ivPrevious = (ImageView) findViewById(R.id.ivPrevious);
        ivPlayPause = (ImageView) findViewById(R.id.ivPlayPause);
        ivNext = (ImageView) findViewById(R.id.ivNext);
<<<<<<< HEAD

        songObjectId = getIntent().getStringExtra(EXTRA_SONGOBJECTID);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        Log.d("PLAYLIST SONGQUEUE", "playlistObjectId " + playlistObjectId);

        albumUrl = getIntent().getStringExtra(EXTRA_ALBUMICONURL);
        Glide.with(this).load(albumUrl).into(ivSongAlbum);

        tvTitle.setText(getIntent().getStringExtra(EXTRA_SONGTITLE));
        tvArtist.setText(getIntent().getStringExtra(EXTRA_SONGARTIST));

        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong(songObjectId);
                ivPlayPause.setImageResource(R.drawable.pauseicon);
            }
        });
=======
        btnReturnPlaylist = findViewById(R.id.btngotoPlaylist);
=======
>>>>>>> Play song, login error handling, contant string extras for intents

        songObjectId = getIntent().getStringExtra(EXTRA_SONGOBJECTID);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        Log.d("PLAYLIST SONGQUEUE", "playlistObjectId "+ playlistObjectId);

        albumUrl = getIntent().getStringExtra(EXTRA_ALBUMICONURL);
        Glide.with(this).load(albumUrl).into(ivSongAlbum);

        tvTitle.setText(getIntent().getStringExtra(EXTRA_SONGTITLE));
        tvArtist.setText(getIntent().getStringExtra(EXTRA_SONGARTIST));

<<<<<<< HEAD
        currentPlaylist = getCurrentPlaylist(playlistObjectId);
        currentSong = getCurrentSong(songObjectId);
>>>>>>> Attempt 2: Edit Profile Activity
=======
        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong(songObjectId);
                ivPlayPause.setImageResource(R.drawable.pauseicon);
            }
        });
>>>>>>> Play song, login error handling, contant string extras for intents

        btnReturnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPlaylist();
            }
        });
    }

<<<<<<< HEAD
<<<<<<< HEAD
    private void gotoPlaylist() {
        Intent i = new Intent(this, CurrentPlaylistActivity.class);
        i.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
        startActivity(i);
    }

    private void playSong(String songObjectId) {
        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);
        // Execute the query to find the object with ID
        query.getInBackground(songObjectId, new GetCallback<Song>() {
            @Override
            public void done(Song song, com.parse.ParseException e) {
                if (e == null) {
                    Log.d(TAG, "play song found" + song.getTitle());
                    String spotifyID = song.getSpotifyId();
                    Log.d(TAG, "play songspotifyid" + "spotifyid " + spotifyID);

                    ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID).setRedirectUri(REDIRECT_URI).showAuthView(true).build();
                    SpotifyAppRemote.connect(SongPlaylistActivity.this, connectionParams,
                            new Connector.ConnectionListener() {

                                @Override
                                public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                                    mSpotifyAppRemote = spotifyAppRemote;
                                    Log.d(TAG, "Connected! Yay!");

                                    // Now you can start interacting with App Remote
                                    mSpotifyAppRemote.getPlayerApi().play("spotify:track:" + spotifyID);
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    Log.e(TAG, throwable.getMessage(), throwable);

                                    // Something went wrong when attempting to connect! Handle errors here
                                }
                            });
                } else {
                    Log.d(TAG, "play song notfound!");
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
=======
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
=======
    private void gotoPlaylist() {
        Intent i = new Intent(this, CurrentPlaylistActivity.class);
        i.putExtra("playlistobjectid", playlistObjectId);
        startActivity(i);
>>>>>>> Play song, login error handling, contant string extras for intents
    }

    private void playSong(String songObjectId) {
        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);
        // Execute the query to find the object with ID
        query.getInBackground(songObjectId, new GetCallback<Song>() {
            @Override
            public void done(Song song, com.parse.ParseException e) {
                if (e == null) {
                    Log.d(TAG, "play song found" + song.getTitle());
                    String spotifyID = song.getSpotifyId();
                    Log.d(TAG, "play songspotifyid" + "spotifyid " + spotifyID);

                    ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID).setRedirectUri(REDIRECT_URI).showAuthView(true).build();
                    SpotifyAppRemote.connect(SongPlaylistActivity.this, connectionParams,
                            new Connector.ConnectionListener() {

                                @Override
                                public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                                    mSpotifyAppRemote = spotifyAppRemote;
                                    Log.d(TAG, "Connected! Yay!");

                                    // Now you can start interacting with App Remote
                                    mSpotifyAppRemote.getPlayerApi().play("spotify:track:" + spotifyID);
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    Log.e(TAG, throwable.getMessage(), throwable);

                                    // Something went wrong when attempting to connect! Handle errors here
                                }
                            });
                }
                else{
                    Log.d(TAG, "play song notfound!");
                }
            }
        });
    }
<<<<<<< HEAD
    private void gotoPlaylist() {
        Intent i = new Intent(this, CurrentPlaylistActivity.class);
        i.putExtra("playlistobjectid", playlistObjectId);
        startActivity(i);
>>>>>>> Attempt 2: Edit Profile Activity
=======
    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
>>>>>>> Play song, login error handling, contant string extras for intents
    }
}