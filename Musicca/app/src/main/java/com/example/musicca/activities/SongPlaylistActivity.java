package com.example.musicca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musicca.R;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class SongPlaylistActivity extends AppCompatActivity {

    Playlist currentPlaylist;
    Song currentSong;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playlist);

        ivSongAlbum = findViewById(R.id.ivSongAlbum);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);
        btnReturnPlaylist = findViewById(R.id.btngotoPlaylist);

        songObjectId = getIntent().getStringExtra("songObjectid");
        playlistObjectId = getIntent().getStringExtra("playlistobjectid");

        albumUrl = getIntent().getStringExtra("albumiconurl");
        Glide.with(this).load(albumUrl).into(ivSongAlbum);

        tvTitle.setText(getIntent().getStringExtra("songtitle"));
        tvArtist.setText(getIntent().getStringExtra("songartist"));

        currentPlaylist = getCurrentPlaylist(playlistObjectId);
        currentSong = getCurrentSong(songObjectId);

        btnReturnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPlaylist();
            }
        });
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
    private void gotoPlaylist() {
        Intent i = new Intent(this, CurrentPlaylistActivity.class);
        i.putExtra("playlistobjectid", playlistObjectId);
        startActivity(i);
    }
}