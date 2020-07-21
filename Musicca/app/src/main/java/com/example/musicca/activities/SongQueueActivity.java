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

import java.util.ArrayList;
import java.util.List;

public class SongQueueActivity extends AppCompatActivity {

    private static final String TAG = "Queue";
    Playlist currentPlaylist = new Playlist();
    ArrayList<Song> currentPlaylistSongs = new ArrayList<>();
    Song currentSong;

    private ImageView ivSongAlbum;
    private TextView tvTitle;
    private TextView tvArtist;
    private Button btnAddSong;
    private Button btnBack;
    private Button btngotoPlaylist;
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
        btngotoPlaylist = findViewById(R.id.btngotoPlaylist);

        songObjectId = getIntent().getStringExtra("songObjectid");
        playlistObjectId = getIntent().getStringExtra("playlistobjectid2");
        Log.d("PLAYLIST SONGQUEUE", playlistObjectId != null ? playlistObjectId : null);

        albumUrl = getIntent().getStringExtra("albumiconurl");
        Glide.with(this).load(albumUrl).into(ivSongAlbum);
        tvTitle.setText(getIntent().getStringExtra("songtitle"));
        tvArtist.setText(getIntent().getStringExtra("songartist"));
        //currentSong = getCurrentSong(songObjectId);
//        Log.d("CURRENT PLAYLIST", currentPlaylist.getName());

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentPlaylistSongs(playlistObjectId);
            }
        });
        btngotoPlaylist.setOnClickListener(new View.OnClickListener() {
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
        i.putExtra("playlistobjectid", playlistObjectId);
        startActivity(i);
    }

    private void backToQueue() {
        Intent i = new Intent(this, QueueActivity.class);
        startActivity(i);
    }

    private void getCurrentPlaylistSongs(String playlistobjectid) {
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(playlistobjectid, new GetCallback<Playlist>() {
            @Override
            public void done(Playlist playlist, com.parse.ParseException e) {
                if (e == null) {
//                    Log.d(TAG, "playlist found" + playlist.getName());
                    if (playlist.getSongList() != null){
                        currentPlaylistSongs = playlist.getSongList();
                    }
//                    Log.d(TAG, "playlist found!" + currentPlaylistSongs.size());
//                    playlist.setSong(getCurrentSong(songObjectId));
                    currentPlaylistSongs.add(getCurrentSong(songObjectId));
                    playlist.setSongList(currentPlaylistSongs);
                    Log.d(TAG, "playlist found!" + currentPlaylistSongs.size());
//                    Toast.makeText(SongQueueActivity.this, "Song has been added", Toast.LENGTH_SHORT).show();
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
}