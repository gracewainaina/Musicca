package com.example.musicca.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import java.util.List;

public class SongPlaylistActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    public static final String EXTRA_CURRENT_SONG_POSITION = "currentSongPosition";
    public static final String EXTRA_SONG_LIST = "songList";

    private static final String TAG = "Play Song";

    private List<String> sortedSongObjectIds;
    private int songPosition;
    private String songSpotifyId;

    private ImageView ivAlbum;
    private TextView tvTitle;
    private TextView tvArtist;
    private ImageView ivPrevious;
    private ImageView ivPlayPause;
    private ImageView ivNext;
    private Button btnReturnPlaylist;

    private String playlistObjectId;

    private static final String CLIENT_ID = "22793b7728c54470b8d117506f9574c5";
    private static final String REDIRECT_URI = "com.musicca://callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    // boolean to determine is song is being played for the first time, on resume or on pause
    private boolean isPlay = false;
    private boolean isPaused = false;

    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playlist);

        Transition transitionEnter = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
        getWindow().setEnterTransition(transitionEnter);

        Transition transitionExit = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
        getWindow().setExitTransition(transitionExit);

        ivAlbum = findViewById(R.id.ivAlbum);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);
        btnReturnPlaylist = findViewById(R.id.btnReturnPlaylist);

        ivPrevious = findViewById(R.id.ivPrevious);
        ivPlayPause = findViewById(R.id.ivPlayPause);
        ivNext = findViewById(R.id.ivNext);

<<<<<<< HEAD
<<<<<<< HEAD
        songPosition = getIntent().getIntExtra(EXTRA_CURRENT_SONG_POSITION, 0);
        sortedSongObjectIds = getIntent().getStringArrayListExtra(EXTRA_SONG_LIST);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);

        setSongView();
=======
        sortedSongObjectIds = getIntent().getStringArrayListExtra("songList");
        songPosition = getIntent().getIntExtra("currentSongPosition", 0);

        setSongView();

//        songObjectId = getIntent().getStringExtra(EXTRA_SONGOBJECTID);
//        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
//
//        albumUrl = getIntent().getStringExtra(EXTRA_ALBUMICONURL);
//        Glide.with(this).load(albumUrl).into(ivAlbum);
//
//        tvTitle.setText(getIntent().getStringExtra(EXTRA_SONGTITLE));
//        tvArtist.setText(getIntent().getStringExtra(EXTRA_SONGARTIST));
>>>>>>> set up play previous and play next

        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
=======
        songPosition = getIntent().getIntExtra(EXTRA_CURRENT_SONG_POSITION, 0);
        sortedSongObjectIds = getIntent().getStringArrayListExtra(EXTRA_SONG_LIST);
        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);

        setSongView();

        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
>>>>>>> user can now play/pause each song and resume playing, even after clicking next/ previous song

                // play for the first time
                if (!isPaused && !isPlay) {
                    playSong();
                    ivPlayPause.setImageResource(R.drawable.pauseicon);
                    isPlay = true;

                    // pause song
                } else if (!isPaused && isPlay) {
                    pauseSong();
                    ivPlayPause.setImageResource(R.drawable.playicon);
                    isPlay = false;
                    isPaused = true;

                    // resume song after playing
                } else if (isPaused && !isPlay) {
                    resumeSong();
                    ivPlayPause.setImageResource(R.drawable.pauseicon);
                    isPaused = false;
                    isPlay = true;
                }
<<<<<<< HEAD
            }
        });

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songPosition == 0) {
                    Toast.makeText(SongPlaylistActivity.this, "This is the first song!", Toast.LENGTH_SHORT).show();
                } else{
                    songPosition--;
                    setSongView();
                }
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SongPlaylistActivity.this, "Clicked!", Toast.LENGTH_SHORT);
                if (songPosition == sortedSongObjectIds.size()-1) {
                    Toast.makeText(SongPlaylistActivity.this, "This is the last song!", Toast.LENGTH_SHORT).show();
                } else{
                    songPosition++;
                    setSongView();
                }
=======
                playSong();
                ivPlayPause.setImageResource(R.drawable.pauseicon);
>>>>>>> set up play previous and play next
=======
>>>>>>> user can now play/pause each song and resume playing, even after clicking next/ previous song
            }
        });

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songPosition == 0) {
                    Toast.makeText(SongPlaylistActivity.this, "This is the first song!", Toast.LENGTH_SHORT).show();
                } else{
                    songPosition--;
                    setSongView();
                }
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SongPlaylistActivity.this, "Clicked!", Toast.LENGTH_SHORT);
                if (songPosition == sortedSongObjectIds.size()-1) {
                    Toast.makeText(SongPlaylistActivity.this, "This is the last song!", Toast.LENGTH_SHORT).show();
                } else{
                    songPosition++;
                    setSongView();
                }
            }
        });

        btnReturnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPlaylist();
            }
        });
    }

    private void setSongView() {
        if (sortedSongObjectIds != null && sortedSongObjectIds.size() > songPosition) {
            String songObjectId = sortedSongObjectIds.get(songPosition);

            ParseQuery<Song> querySong = ParseQuery.getQuery(Song.class);
            querySong.getInBackground(songObjectId, new GetCallback<Song>() {
                @Override
                public void done(Song song, com.parse.ParseException e) {
                    if (e == null) {
                        Glide.with(SongPlaylistActivity.this).load(song.getURL()).into(ivAlbum);
                        tvTitle.setText(song.getTitle());
                        tvArtist.setText(song.getArtist());
                        songSpotifyId = song.getSpotifyId();

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> user can now play/pause each song and resume playing, even after clicking next/ previous song
                        // grey out previous icon if it is the first item in the list
                        if (songPosition == 0) {
                            ivPrevious.setImageResource(R.drawable.previcon_grey);
                        } else {
                            ivPrevious.setImageResource(R.drawable.previcon);
                        }
                        // grey out next icon if it is the last item in the list
                        if (songPosition == sortedSongObjectIds.size()-1) {
                            ivNext.setImageResource(R.drawable.nexticon_grey);
                        } else {
                            ivNext.setImageResource(R.drawable.nexticon);
                        }

                        // if previous song was playing, start playing the next one too
                        if (!isPaused && isPlay) {
                            ivPlayPause.setImageResource(R.drawable.pauseicon);
                            playSong();

                            // previous song was not playing then do not auto start the next one
                        } else {
                            isPaused = false;
                            isPlay = false;
                            ivPlayPause.setImageResource(R.drawable.playicon);
                        }

<<<<<<< HEAD
=======
>>>>>>> set up play previous and play next
=======
>>>>>>> user can now play/pause each song and resume playing, even after clicking next/ previous song
                    } else {
                        Toast.makeText(SongPlaylistActivity.this, "Error showing song!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> set up play previous and play next
=======
>>>>>>> user can now play/pause each song and resume playing, even after clicking next/ previous song
    }

    private void gotoPlaylist() {
        Intent i = new Intent(this, CurrentPlaylistActivity.class);
        i.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
        startActivity(i);
    }

    private void playSong() {

        ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID).setRedirectUri(REDIRECT_URI).showAuthView(true).build();
        SpotifyAppRemote.connect(SongPlaylistActivity.this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d(TAG, "Connected!");
                        // Now you can start interacting with App Remote
                        mSpotifyAppRemote.getPlayerApi().play("spotify:track:" + songSpotifyId);
                    }
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> set up play previous and play next
=======
>>>>>>> user can now play/pause each song and resume playing, even after clicking next/ previous song
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage(), throwable);
                        Toast.makeText(SongPlaylistActivity.this, "Error playing song!", Toast.LENGTH_SHORT).show();
                    }
                });
<<<<<<< HEAD
<<<<<<< HEAD
    }

    private void resumeSong() {
        mSpotifyAppRemote.getPlayerApi().resume();
    }

    private void pauseSong() {
        mSpotifyAppRemote.getPlayerApi().pause();
=======
=======
    }
>>>>>>> user can now play/pause each song and resume playing, even after clicking next/ previous song

    private void resumeSong() {
        mSpotifyAppRemote.getPlayerApi().resume();
    }

<<<<<<< HEAD

//        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);
//        // Execute the query to find the object with ID
//        query.getInBackground(songObjectId, new GetCallback<Song>() {
//            @Override
//            public void done(Song song, com.parse.ParseException e) {
//                if (e == null) {
//                    String spotifyID = song.getSpotifyId();
//
//
//                } else {
//                    Log.d(TAG, "play song not found!");
//                    Toast.makeText(SongPlaylistActivity.this, "Error playing song!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
>>>>>>> set up play previous and play next
=======
    private void pauseSong() {
        mSpotifyAppRemote.getPlayerApi().pause();
>>>>>>> user can now play/pause each song and resume playing, even after clicking next/ previous song
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }
}