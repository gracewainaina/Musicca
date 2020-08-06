package com.example.musicca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicca.R;
import com.example.musicca.adapters.QueueAdapter;
import com.example.musicca.fragments.MultiFilterDialogFragment;
import com.example.musicca.fragments.MultiFilterDialogFragment.onFilterActionListener;
import com.example.musicca.models.Song;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class QueueActivity extends AppCompatActivity implements onFilterActionListener {

    private static final String EXTRA_PLAYLISTOBJECTID = "playlistobjectid";
    private static final String TAG = "QueueAdapter";

    private String playlistObjectId;
    private RecyclerView rvLatestSongs;
    private QueueAdapter queueAdapter;
    protected List<Song> allSongs;
    private TextView tvSection;
    private Button btnGoToPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);

        Transition transitionEnter = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
        getWindow().setEnterTransition(transitionEnter);

        Transition transitionExit = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
        getWindow().setExitTransition(transitionExit);

        playlistObjectId = getIntent().getStringExtra(EXTRA_PLAYLISTOBJECTID);
        tvSection = findViewById(R.id.tvSection);
        rvLatestSongs = findViewById(R.id.rvLatestSongs);
        btnGoToPlaylist = findViewById(R.id.btnGoToPlaylist);

        btnGoToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPlaylist();
            }
        });

        allSongs = new ArrayList<>();
        queryAllSongs();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queueAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queueAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            showFilterMenu();
        } else if (item.getItemId() == R.id.action_home) {
            goMainActivity();
        } else if (item.getItemId() == R.id.action_logout) {
            performLogOut();
        }
        return super.onOptionsItemSelected(item);
    }

    private void performLogOut() {
        ParseUser.logOut();
        if (ParseUser.getCurrentUser() == null) {
            goLoginActivity();
        }
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void showFilterMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MultiFilterDialogFragment multiFilterDialogFragment = MultiFilterDialogFragment.newInstance("Add Search Filters Here");
        multiFilterDialogFragment.show(fm, "fragment_multi_filter_dialog");
    }


    private void queryAllSongs() {
        ParseQuery<Song> query = ParseQuery.getQuery(Song.class);

        query.findInBackground((songs, e) -> {
            if (e != null) {
                Log.e(TAG, "Issue retrieving songs", e);
                Toast.makeText(QueueActivity.this, "Error retrieving songs!", Toast.LENGTH_SHORT).show();
                return;
            }

            allSongs.addAll(songs);

            queueAdapter = new QueueAdapter(this, allSongs, playlistObjectId);
            rvLatestSongs.setAdapter(queueAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            rvLatestSongs.setLayoutManager(linearLayoutManager);
            rvLatestSongs.setItemAnimator(new SlideInUpAnimator());

            queueAdapter.notifyDataSetChanged();
        });
    }

    private void gotoPlaylist() {
        Intent i = new Intent(this, CurrentPlaylistActivity.class);
        i.putExtra(EXTRA_PLAYLISTOBJECTID, playlistObjectId);
        startActivity(i);
    }

    @Override
    public void onFinishFilterDialog(String yearAfter, String yearBefore, String songTitle, String songArtist) {
        queueAdapter.performMultiFiltering(yearAfter, yearBefore, songTitle, songArtist);
    }
}
