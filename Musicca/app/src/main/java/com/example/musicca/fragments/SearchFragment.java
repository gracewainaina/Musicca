package com.example.musicca.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.musicca.R;
import com.example.musicca.adapters.SearchAdapter;
import com.example.musicca.models.Playlist;
import com.example.musicca.models.Song;
import com.example.musicca.utilities.LiveSearchManager;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    RecyclerView rvSearch;
    ProgressBar progressBar;
    LinearLayout textContainer;
    TextView tvError;
    TextView tvDescription;

    SearchAdapter searchAdapter;
    LiveSearchManager mLiveSearchManager;

    private Playlist playlist;

    public SearchFragment(Playlist mPlaylist) {
        playlist = mPlaylist;
    }

    /***
     * Create a new instance of the SearchFragment
     * @return The new instance created
     */
    public static SearchFragment newInstance(Playlist mPlaylist) {
//		Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment(mPlaylist);
//		fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    /***
     * Execute a search for the given string
     * @param query the string to search for
     */
    public void executeSearch(String query) {
        if(!mLiveSearchManager.isSearchComplete()) {
            mLiveSearchManager.cancelPendingSearches();
            progressBar.setVisibility(View.VISIBLE);
            searchAdapter.clear();
            hideText();
            loadData(query);
        }
    }

    public void updateLiveSearch(String query) {
        hideText();
        mLiveSearchManager.updateQuery(query);
    }

    /***
     * Inflate the proper layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }

    /***
     * Set up the mAdapter and recycler view when the fragment view is created.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSearch = view.findViewById(R.id.rvSearch);
        progressBar = view.findViewById(R.id.progressBar);
        LinearLayout textContainer = view.findViewById(R.id.textContainer);
        tvError = view.findViewById(R.id.tvError);
        tvDescription = view.findViewById(R.id.tvDescription);

        // Create the mAdapter, along with onClick listener for the "add" button
        searchAdapter = new SearchAdapter(getContext());

        // Attach Swipe listeners
        ItemTouchHelperCallbacks callbacks = new ItemTouchHelperCallbacks(searchAdapter, getContext());
        new ItemTouchHelper(callbacks.addCallback).attachToRecyclerView(rvSearch);

        // Set mAdapter, layout manger, and item decorations
        rvSearch.setAdapter(searchAdapter);
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));

        mLiveSearchManager = new LiveSearchManager(this::handleSearchDisplay);
    }

    private void handleSearchDisplay(String query, List<Song> results) {
        searchAdapter.clear();
        if(query.isEmpty()) {
            showSearchDescription(null);
            showText("Search for a song");
        } else if(results.isEmpty()) {
            showSearchDescription(null);
            showText("No search results found");
        } else {
            showSearchDescription(query);
            hideText();
            searchAdapter.addAll(results);
        }
    }

    public void clear() {
        searchAdapter.clear();
        handleSearchDisplay("", null);
    }

    /***
     * Load the search results
     * @param searchText String to search for
     */
    public void loadData(String searchText) {
        Log.d(TAG, String.format("Searched for : %s", searchText));
        final Song.SearchQuery search = new Song.SearchQuery();
        search.setQuery(searchText).setLimit(15).find(e -> {
            progressBar.setVisibility(View.GONE);
            if(e == null) {
                if(search.getResults().isEmpty()) {
                    searchAdapter.clear();
                    showSearchDescription(null);
                    showText("No search results found");
                } else {
                    showSearchDescription(searchText);
                    hideText();
                    searchAdapter.clear();
                    searchAdapter.addAll(search.getResults());
                }
            } else {
                if(e.getMessage().startsWith("400")) {
                    showText("No search results found");
                } else {
                    showText("Something went wrong, try again");
                }
            }
        });
    }

    private void showText(String text) {
        textContainer.setVisibility(View.VISIBLE);
        tvError.setText(text);
    }

    private void showSearchDescription(@Nullable String query) {
        if(query == null) {
            tvDescription.setVisibility(View.GONE);
        } else {
            String text = String.format("Showing results for \"%s\"", query);
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            builder.setSpan(new StyleSpan(Typeface.BOLD),
                    21, text.length() - 1,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            tvDescription.setText(builder);
            tvDescription.setVisibility(View.VISIBLE);
        }
    }

    private void hideText() {
        textContainer.setVisibility(View.GONE);
    }
}
