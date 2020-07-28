package com.example.musicca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class SongQueueActivity extends AppCompatActivity {

    Playlist currentPlaylist;
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

        ivSongAlbum = findViewById(R.id.ivSongAlbum);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);
        btnAddSong = findViewById(R.id.btnAddSong);
        btnBack = findViewById(R.id.btnBack);
        btngotoPlaylist = findViewById(R.id.btngotoPlaylist);

        songObjectId = getIntent().getStringExtra("songObjectid");
        playlistObjectId = getIntent().getStringExtra("playlistobjectid");

        albumUrl = getIntent().getStringExtra("albumiconurl");
        Glide.with(this).load(albumUrl).into(ivSongAlbum);
        tvTitle.setText(getIntent().getStringExtra("songtitle"));
        tvArtist.setText(getIntent().getStringExtra("songartist"));

        currentPlaylist = getCurrentPlaylist(playlistObjectId);
        currentSong = getCurrentSong(songObjectId);

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSong(currentSong);
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
    }
}