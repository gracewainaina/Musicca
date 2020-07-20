package com.example.musicca.models;

import android.util.Log;

import androidx.annotation.Nullable;
<<<<<<< HEAD

import com.parse.ParseClassName;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ParseClassName("Song")
public class Song extends ParseObject {

    public static final String KEY_SPOTIFY_ID = "spotifyId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_ALBUM = "album";
    public static final String KEY_URL = "artUrl";

    public String getSpotifyId() {
        return getString(KEY_SPOTIFY_ID);
    }

    public void setSpotifyId(String spotifyid) {
        put(KEY_SPOTIFY_ID, spotifyid);
    }

    public String getTitle() {
        return getString(KEY_TITLE);
    }

    public void setTitle(String title) {
        put(KEY_TITLE, title);
    }

    public String getArtist() {
        return getString(KEY_ARTIST);
    }

    public void setArtist(String artist) {
        put(KEY_ARTIST, artist);
    }

    public String getAlbum() {
        return getString(KEY_ALBUM);
    }

    public void setAlbum(String album) {
        put(KEY_ALBUM, album);
    }

    public String getURL() {
        return getString(KEY_URL);
    }

    public void setURL(String url) {
        put(KEY_URL, url);
    }

    // This class represents a executeSearch query that can be sent to the Parse server which will then
    // call the Spotify API and return a list of song results
    public static class SearchQuery {
        private static int DEFAULT_NUM_RESULTS = 20;

        private List<Song> mResults;
        private String mQuery;
        private int mNumResults;
        private boolean mIsLiveSearch;

        public SearchQuery() {
            mNumResults = DEFAULT_NUM_RESULTS;
            mQuery = "";
            mIsLiveSearch = false;
            mResults = new ArrayList<>();
        }

        // Set the number of results the executeSearch should return.  Defaults to 20 if nothing is set.
        public SearchQuery setLimit(int numResults) {
            mNumResults = numResults;
            return this;
        }

        // Sets the search query
        public SearchQuery setQuery(String query) {
            mQuery = query;
            return this;
        }

        // get query
        public String getQuery() {
            return mQuery;
        }

        // Sets if the search is a live search or not.  If the search is live, it will use the cache
        // on the server to speed up results.
        public SearchQuery setIsLive(boolean isLiveSearch) {
            mIsLiveSearch = isLiveSearch;
            return this;
        }

        // Gets the executeSearch results. Called in the callback of the find function so as to populate

        public List<Song> getResults() {
            return mResults;
        }

        // executeSearch query
        public void find(@Nullable SaveCallback callback) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("query", mQuery);
            params.put("limit", mNumResults);
            params.put("useCache", mIsLiveSearch);

            // Default to an empty result list if the query is empty
            if (mQuery.isEmpty()) {
                // Run the callback if it exists
                if (callback != null) {
                    callback.done(null);
                }
                return;
            }

            ParseCloud.callFunctionInBackground("search", params, (List<Song> results, ParseException e) -> {
                if (e == null) {
                    // Save the executeSearch results to this object so they can be accessed through getResults()
                    mResults = results;
                } else {
                    // Log the error if we get one
                    Log.e("Song.SearchQuery", "SearchQuery failed: ", e);
                }

                // Run the callback if it exists
                if (callback != null) {
                    callback.done(e);
                }
            });
        }

    }

=======

import com.parse.ParseClassName;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ParseClassName("Song")
public class Song extends ParseObject {

    public static final String KEY_SPOTIFY_ID = "spotifyID";
    public static final String KEY_TITLE = "title";
    public static final String KEY_ARTIST = "artist";
    public static final String KEY_ALBUM = "album";
    public static final String KEY_URL = "artUrl";

    public String getSpotifyId(){
        return KEY_SPOTIFY_ID;
    }
    public void setSpotifyId(String spotifyid){
        put(KEY_SPOTIFY_ID, spotifyid);
    }
    public String getTitle(){
        return KEY_SPOTIFY_ID;
    }
    public void setTitle(String title){
        put(KEY_TITLE, title);
    }
    public String getArtist(){
        return KEY_ARTIST;
    }
    public void setArtist(String artist){
        put(KEY_ARTIST, artist);
    }
    public String getAlbum(){
        return KEY_ALBUM;
    }
    public void setAlbum(String album){
        put(KEY_ALBUM, album);
    }
    public String getURL(){
        return KEY_URL;
    }
    public void setURL(String url){
        put(KEY_URL, url);
    }

    // This class represents a executeSearch query that can be sent to the Parse server which will then
    // call the Spotify API and return a list of song results
    public static class SearchQuery {
        private static int DEFAULT_NUM_RESULTS = 20;

        private List<Song> mResults;
        private String mQuery;
        private int mNumResults;
        private boolean mIsLiveSearch;

        public SearchQuery() {
            mNumResults = DEFAULT_NUM_RESULTS;
            mQuery = "";
            mIsLiveSearch = false;
            mResults = new ArrayList<>();
        }

        // Set the number of results the executeSearch should return.  Defaults to 20 if nothing is set.
        public SearchQuery setLimit(int numResults) {
            mNumResults = numResults;
            return this;
        }

        // Sets the search query
        public SearchQuery setQuery(String query) {
            mQuery = query;
            return this;
        }
        // get query
        public String getQuery() {
            return mQuery;
        }

        // Sets if the search is a live search or not.  If the search is live, it will use the cache
        // on the server to speed up results.
        public SearchQuery setIsLive(boolean isLiveSearch) {
            mIsLiveSearch = isLiveSearch;
            return this;
        }

        // Gets the executeSearch results. Called in the callback of the find function so as to populate

        public List<Song> getResults() {
            return mResults;
        }

        // executeSearch query
        public void find(@Nullable SaveCallback callback) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("query", mQuery);
            params.put("limit", mNumResults);
            params.put("useCache", mIsLiveSearch);

            // Default to an empty result list if the query is empty
            if(mQuery.isEmpty()) {
                // Run the callback if it exists
                if(callback != null) {
                    callback.done(null);
                }
                return;
            }

            ParseCloud.callFunctionInBackground("search", params, (List<Song> results, ParseException e) -> {
                if (e == null) {
                    // Save the executeSearch results to this object so they can be accessed through getResults()
                    mResults = results;
                } else {
                    // Log the error if we get one
                    Log.e("Song.SearchQuery", "SearchQuery failed: ", e);
                }

                // Run the callback if it exists
                if(callback != null) {
                    callback.done(e);
                }
            });
        }

    }

>>>>>>> Attempt 2 [lost files] Searchbar onquery listener text
}