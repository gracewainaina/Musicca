package com.example.musicca.activities;

import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.musicca.R;
import com.example.musicca.adapters.QueueAdapter;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class QueueActivity extends AppCompatActivity {

    private static final String TAG = "QueueAdapter";

    private String playlistObjectId;
    private RecyclerView rvLatestSongs;
    private QueueAdapter queueAdapter;
    protected List<Song> allSongs;
    private TextView tvSection;
    private Button btngotoPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        playlistObjectId = getIntent().getStringExtra("playlistobjectid1");
        Log.d("PLAYLIST OBJ ID", "object id" + playlistObjectId);
        tvSection = findViewById(R.id.tvSection);
        rvLatestSongs = findViewById(R.id.rvLatestSongs);
        btngotoPlaylist = findViewById(R.id.btngotoPlaylist);

        btngotoPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPlaylist();
            }
        });

        allSongs = new ArrayList<>();
        Log.d(TAG, "length of songsAll1 " + allSongs.size());

        queryAllSongs();

        Log.d(TAG, "length of songsAll2 " + allSongs.size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "on query text submit");
                queueAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "on query text change");
                queueAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void queryAllSongs() {
        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);
//        query.setLimit(5);
//        query.setSkip(5 * page);

        query.findInBackground((songs, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue retrieving songs", e);
                return;
            }
            for (Song song : songs) {
                Log.i(TAG, "Song: " + song.getTitle() + ", spotifyId: " + song.getSpotifyId());
            }
            allSongs.addAll(songs);
            Log.d(TAG, "length of songsAll3 " + allSongs.size());

            queueAdapter = new QueueAdapter(this, allSongs, playlistObjectId);
            rvLatestSongs.setAdapter(queueAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvLatestSongs.setLayoutManager(linearLayoutManager);
            queueAdapter.notifyDataSetChanged();
        });

//        try {
//            allSongs.addAll(query.find());
//            Log.d(TAG, "length of songsAll3 " + allSongs.size());
//            queueAdapter.notifyDataSetChanged();
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }

    private void gotoPlaylist() {
        Intent i = new Intent(this, CurrentPlaylistActivity.class);
        i.putExtra("playlistobjectid", playlistObjectId);
        startActivity(i);
    }
}
