package com.example.musicca.activities;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.musicca.R;
import com.example.musicca.fragments.BottomPlayerFragment;
import com.example.musicca.fragments.QueueFragment;
import com.example.musicca.fragments.SearchFragment;
import com.example.musicca.models.Playlist;
import com.google.android.material.appbar.AppBarLayout;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import butterknife.ButterKnife;

public class PartyActivity extends AppCompatActivity {

    private static final String KEY_QUEUE_FRAGMENT = "queue";
    private static final String KEY_SEARCH_FRAGMENT = "search";
    private static final String KEY_ACTIVE = "active";
    private static final String TAG = "MainActivity";

    public Playlist playlistParty;

    FrameLayout flPlaceholder;
    AppBarLayout ablMain;
    Toolbar tbMain;
    FrameLayout flBottomPlayer;
    SearchView miSearchView;
    ImageView ivSearchBackground;
    ImageButton ibBack;
    ImageButton ibOverflow;
    ConstraintLayout clSearchbar;
    FrameLayout flOverlay;

    private FragmentManager mFragmentManager;
    private Fragment mActiveFragment;
    private QueueFragment queueFragment;
    private SearchFragment searchFragment;
    private BottomPlayerFragment bottomPlayerFragment;

    private boolean mIsHiding = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        ButterKnife.bind(this);

        String objectId = getIntent().getStringExtra("playlistObjectId");
        ParseQuery<Playlist> query = ParseQuery.getQuery(Playlist.class);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(objectId, new GetCallback<Playlist>() {
            @Override
            public void done(Playlist object, ParseException e) {
                if (e == null) {
                    playlistParty = object;
                }
            }
        });

        initializeFragments(savedInstanceState);
        bottomPlayerFragment = BottomPlayerFragment.newInstance(playlistParty);

        mFragmentManager.beginTransaction().replace(R.id.flBottomPlayer, bottomPlayerFragment).commit();

        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void initializeFragments(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            queueFragment = (QueueFragment) mFragmentManager.getFragment(savedInstanceState, KEY_QUEUE_FRAGMENT);
            searchFragment = (SearchFragment) mFragmentManager.getFragment(savedInstanceState, KEY_SEARCH_FRAGMENT);
            mActiveFragment = savedInstanceState.getString(KEY_ACTIVE).equals(KEY_QUEUE_FRAGMENT)
                    ? queueFragment : searchFragment;
        }

        if (queueFragment == null) {
            queueFragment = QueueFragment.newInstance(playlistParty);
            mFragmentManager.beginTransaction().add(R.id.flPlaceholder, queueFragment, KEY_QUEUE_FRAGMENT).hide(queueFragment).commit();
        }
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance(playlistParty);
            mFragmentManager.beginTransaction().add(R.id.flPlaceholder, searchFragment, KEY_SEARCH_FRAGMENT).hide(searchFragment).commit();
        }

        if (mActiveFragment != null) {
            display(mActiveFragment);
        } else {
            display(queueFragment);
        }
    }

    // Initializes the searchbar onclicks
    private void initializeSearch() {
        // Set up back button onClick
        ibBack.setOnClickListener(view -> {
            onBackPressed();
            if (miSearchView.hasFocus()) {
                miSearchView.clearFocus();
                hideKeyboard(miSearchView);
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        miSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        miSearchView.setIconifiedByDefault(false);

        // Set SearchView callbacks
        miSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query, miSearchView);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                liveSearch(query);
                return true;
            }
        });

        // Set up fragment transition on focus change
        miSearchView.setOnQueryTextFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                display(searchFragment);
                showKeyboard(view);
                bottomPlayerFragment.setExpanded();
            }
        });
    }

    // Saves currently active fragment
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, KEY_ACTIVE, mActiveFragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switch (mActiveFragment.getTag()) {
            case KEY_QUEUE_FRAGMENT:
                break;
            case KEY_SEARCH_FRAGMENT:
                searchFragment.clear();
                miSearchView.setQuery("", false);
                display(queueFragment);
                break;
        }
    }


    // Displays a new fragment and hides previously active fragment
    private void display(Fragment fragment) {
        if (fragment == null || fragment.equals(mActiveFragment)) return;
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (mActiveFragment != null)
            ft.hide(mActiveFragment);
        ft.show(fragment);
        if (fragment.equals(searchFragment)) {
            searchFragment.clear();
            ft.addToBackStack(fragment.getTag());
        }
        ft.commit();

        mActiveFragment = fragment;
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void search(String query, SearchView v) {
        display(searchFragment);
        searchFragment.executeSearch(query);
        hideKeyboard(v);
        miSearchView.clearFocus();
    }

    private void liveSearch(String query) {
        display(searchFragment);
        searchFragment.updateLiveSearch(query);
    }
}
